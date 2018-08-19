package kcs.com.logicart.Presenter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kcs.com.logicart.Helper.APIConstant
import kcs.com.logicart.Interface.AuthInterface
import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.POJO.AuthResponse
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

class CekSaldoInteractorImp : CekSaldoInteractor {
    override fun getSaldp(listener: CekSaldoInteractor.OnSuccessgetSaldoListener, userAuth: UserAuth) {
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
        val call = service.Ceksaldo(userAuth)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val gson = Gson()
                    try {
                        val authResponse = gson.fromJson(response.body()!!.string(), AuthResponse::class.java)
                        listener.onSuccess(response.message(), authResponse)
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
