package kcs.com.logicart.View

import kcs.com.logicart.POJO.AuthResponse

interface CekSaldoView {

    fun ResultCekSaldo(response_message: String, authResponse: AuthResponse)

    fun ResultError(response_message: String)
}
