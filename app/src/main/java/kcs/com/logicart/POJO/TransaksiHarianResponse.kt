package kcs.com.logicart.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TransaksiHarianResponse {

    @SerializedName("DaftarTransaksi")
    @Expose
    var daftarTransaksi: List<DaftarTransaksi>? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null

}
