package kcs.com.logicart.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TransaksiPulsaResponse {

    @SerializedName("denom")
    @Expose
    var denom: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("nohp")
    @Expose
    var nohp: String? = null
    @SerializedName("sn")
    @Expose
    var sn: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null

}

