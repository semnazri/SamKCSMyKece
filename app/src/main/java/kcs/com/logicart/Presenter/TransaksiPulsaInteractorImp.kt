package kcs.com.logicart.Presenter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kcs.com.logicart.Helper.APIConstant
import kcs.com.logicart.Interface.AuthInterface
import kcs.com.logicart.JSON.Transaction
import kcs.com.logicart.POJO.TransaksiPulsaResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class TransaksiPulsaInteractorImp : TransaksiPulsaInteractor {

    override fun DoTransaksiPulsa(listener: TransaksiPulsaInteractor.OnSuccessTransaksiListener, transaction: Transaction) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(APIConstant.main)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create(AuthInterface::class.java)
        val call = service.BeliPulsa(transaction)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    try {
                        val transaksiPulsaResponse = gson.fromJson(response.body()!!.string(), TransaksiPulsaResponse::class.java)
                        listener.onSuccess(response.message(), transaksiPulsaResponse)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                } else {
                    when (response.code()) {
                        401 -> listener.onelseError("Something went wrong! Response Code 401")
                        404 -> listener.onelseError("Something went wrong! Response Code 404")
                        500 -> listener.onelseError("Something went wrong! Response Code 500")
                        else -> listener.onelseError("Something went wrong! Response Code" + response.code())
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                android.util.Log.d("onFailure", t.toString())
            }
        })
    }
}
