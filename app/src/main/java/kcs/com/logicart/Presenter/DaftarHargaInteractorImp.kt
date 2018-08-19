package kcs.com.logicart.Presenter

import com.google.gson.GsonBuilder
import kcs.com.logicart.Helper.APIConstant
import kcs.com.logicart.Interface.AuthInterface
import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.POJO.DaftarHarga
import kcs.com.logicart.POJO.DaftarHargaParentResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class DaftarHargaInteractorImp : DaftarHargaInteractor {

    internal lateinit var daftarharga: ArrayList<DaftarHarga>

    override fun getHarga(listener: DaftarHargaInteractor.OnSuccessgetHargaListener, userAuth: UserAuth) {
        daftarharga = ArrayList()

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
        val call = service.Daftar_harga(userAuth)

        call.enqueue(object : Callback<DaftarHargaParentResponse> {
            override fun onResponse(call: Call<DaftarHargaParentResponse>, response: Response<DaftarHargaParentResponse>) =
                    if (response.isSuccessful) {

                        for (i in 0 until response.body()!!.daftarHarga!!.size) {
                            val data = DaftarHarga()

                            data.fiturNama = response.body()!!.daftarHarga!![i].fiturNama
                            data.fiturKode = response.body()!!.daftarHarga!![i].fiturKode
                            data.harga = response.body()!!.daftarHarga!![i].harga
                            data.denomial = response.body()!!.daftarHarga!![i].denomial
                            data.denomRibu = response.body()!!.daftarHarga!![i].denomRibu

                            daftarharga.add(data)
                        }

//
//                        for (i in 0 until response.body()!!.daftarHarga!!.size. {
//                            val data = DaftarHargaParentResponse()
//
//                            data.daftarHarg = response.body()!![i].daftarHarga!![i].fiturNama
//                            data.daftarHarga!![i].fiturKode = response.body()!![i].daftarHarga!![i].fiturKode
//                            data.daftarHarga!![i].denomRibu = response.body()!![i].daftarHarga!![i].denomRibu
//                            data.daftarHarga!![i].denomial = response.body()!![i].daftarHarga!![i].denomial
//                            data.daftarHarga!![i].harga = response.body()!![i].daftarHarga!![i].harga
//                            daftarharga.add(data)
//                        }
                        listener.onSuccess(response.message(), daftarharga)
                    } else {
                        when (response.code()) {
                            401 -> listener.onelseError("Something went wrong! Response Code 401")
                            404 -> listener.onelseError("Something went wrong! Response Code 404")
                            500 -> listener.onelseError("Something went wrong! Response Code 500")
                            else -> listener.onelseError("Something went wrong! Response Code" + response.code())
                        }
                    }

            override fun onFailure(call: Call<DaftarHargaParentResponse>, t: Throwable) {
                android.util.Log.d("onFailure", t.toString())
            }
        })
    }
}
