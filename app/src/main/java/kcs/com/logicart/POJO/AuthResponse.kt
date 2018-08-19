package kcs.com.logicart.POJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthResponse {

    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("saldo")
    @Expose
    var saldo: Int? = null
    @SerializedName("status")
    @Expose
    var status: String? = null

}
