package kcs.com.logicart.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DaftarHarga {

    @SerializedName("DenomRibu")
    @Expose
    var denomRibu: Int? = null

    @SerializedName("Denomial")
    @Expose
    var denomial: Int? = null

    @SerializedName("FiturKode")
    @Expose
    var fiturKode: String? = null

    @SerializedName("FiturNama")
    @Expose
    var fiturNama: String? = null

    @SerializedName("Harga")
    @Expose
    var harga: Int? = null

}
