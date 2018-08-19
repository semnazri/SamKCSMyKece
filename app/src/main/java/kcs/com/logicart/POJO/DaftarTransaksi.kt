package kcs.com.logicart.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DaftarTransaksi {

    @SerializedName("Denomial")
    @Expose
    var denomial: Int? = null
    @SerializedName("ErrorKode")
    @Expose
    var errorKode: String? = null
    @SerializedName("Harga")
    @Expose
    var harga: Int? = null
    @SerializedName("NoHp")
    @Expose
    var noHp: String? = null
    @SerializedName("Pesan")
    @Expose
    var pesan: String? = null
    @SerializedName("TransDate")
    @Expose
    var transDate: String? = null

    @SerializedName("FiturNama")
    @Expose
    var fiturNama: String? = null
}
