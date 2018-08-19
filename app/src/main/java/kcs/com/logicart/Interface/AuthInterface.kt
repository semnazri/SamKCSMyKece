package kcs.com.logicart.Interface

import kcs.com.logicart.JSON.DailyTransaction
import kcs.com.logicart.JSON.Transaction
import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.POJO.DaftarHargaParentResponse
import kcs.com.logicart.POJO.TransaksiHarianResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {

    @POST("ACSaldo")
    fun Ceksaldo(@Body userAuth: UserAuth): Call<ResponseBody>


    //TODO : ini responsenya masih kosong
    @POST("ADaftarHarga")
    fun Daftar_harga(@Body userAuth: UserAuth): Call<DaftarHargaParentResponse>


    //TODO : ini responsenya masih kosong
    @POST("ABeliPulsa")
    fun BeliPulsa(@Body transaction: Transaction): Call<ResponseBody>

    //TODO : ini responsenya masih kosong
    @POST("ATransaksiHarian")
    fun DailyTransaction(@Body dailyTransaction: DailyTransaction): Call<TransaksiHarianResponse>
}
