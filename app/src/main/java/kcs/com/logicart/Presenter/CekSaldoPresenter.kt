package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.UserAuth

interface CekSaldoPresenter {

    fun getSaldo(userAuth: UserAuth)
    fun onDestroy()
}
