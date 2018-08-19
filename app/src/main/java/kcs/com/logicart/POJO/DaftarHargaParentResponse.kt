package kcs.com.logicart.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DaftarHargaParentResponse {


    @SerializedName("DaftarHarga")
    @Expose
    var daftarHarga: List<DaftarHarga>? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null


}
