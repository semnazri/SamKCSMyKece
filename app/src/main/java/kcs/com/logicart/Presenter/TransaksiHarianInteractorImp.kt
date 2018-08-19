package kcs.com.logicart.Presenter

import com.google.gson.GsonBuilder
import kcs.com.logicart.Helper.APIConstant
import kcs.com.logicart.Interface.AuthInterface
import kcs.com.logicart.JSON.DailyTransaction
import kcs.com.logicart.POJO.DaftarHarga
import kcs.com.logicart.POJO.DaftarTransaksi
import kcs.com.logicart.POJO.TransaksiHarianResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TransaksiHarianInteractorImp : TransaksiHarianInteractor {

    internal lateinit var daftarTransaksi: ArrayList<DaftarTransaksi>

    override fun getDailyTrans(listener: TransaksiHarianInteractor.OnSuccessgetDailyTransListener, dailytransaction: DailyTransaction) {
        daftarTransaksi = ArrayList()


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
        val call = service.DailyTransaction(dailytransaction)

        call.enqueue(object : Callback<TransaksiHarianResponse> {
            override fun onResponse(call: Call<TransaksiHarianResponse>, response: Response<TransaksiHarianResponse>) {
                if (response.isSuccessful) {

                    for (i in 0 until response.body()!!.daftarTransaksi!!.size) {
                        val data = DaftarTransaksi()

                        data.denomial = response.body()!!.daftarTransaksi!![i].denomial
                        data.errorKode = response.body()!!.daftarTransaksi!![i].errorKode
                        data.harga = response.body()!!.daftarTransaksi!![i].harga
                        data.noHp = response.body()!!.daftarTransaksi!![i].noHp
                        data.pesan = response.body()!!.daftarTransaksi!![i].pesan
                        data.transDate = response.body()!!.daftarTransaksi!![i].transDate
                        data.fiturNama = response.body()!!.daftarTransaksi!![i].fiturNama

                        daftarTransaksi.add(data)
                    }

                    listener.onSuccess(response.message(),daftarTransaksi)

                } else {
                    when (response.code()) {
                        401 -> listener.onelseError("Something went wrong! Response Code 401")
                        404 -> listener.onelseError("Something went wrong! Response Code 404")
                        500 -> listener.onelseError("Something went wrong! Response Code 500")
                        else -> listener.onelseError("Something went wrong! Response Code" + response.code())
                    }
                }
            }

            override fun onFailure(call: Call<TransaksiHarianResponse>, t: Throwable) {
                android.util.Log.d("onFailure", t.toString())
            }
        })
    }
}
