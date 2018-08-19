package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.POJO.AuthResponse

interface CekSaldoInteractor {

    fun getSaldp(listener: OnSuccessgetSaldoListener, userAuth: UserAuth)

    interface OnSuccessgetSaldoListener {

        fun onSuccess(response_message: String, authResponse: AuthResponse)

        fun onelseError(response_message: String)

    }
}
