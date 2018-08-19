package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.POJO.AuthResponse
import kcs.com.logicart.View.CekSaldoView

class CekSaldoPresenterImp(private var cekSaldoView: CekSaldoView?) : CekSaldoPresenter, CekSaldoInteractor.OnSuccessgetSaldoListener {
    private val cekSaldoInteractor: CekSaldoInteractor

    init {
        this.cekSaldoInteractor = CekSaldoInteractorImp()
    }

    override fun onSuccess(response_message: String, authResponse: AuthResponse) {
            if (cekSaldoView != null) {
                cekSaldoView!!.ResultCekSaldo(response_message, authResponse)
            }
    }

    override fun onelseError(response_message: String) {
        if (cekSaldoView != null) {
            cekSaldoView!!.ResultError(response_message)
        }
    }

    override fun getSaldo(userAuth: UserAuth) {
        cekSaldoInteractor.getSaldp(this, userAuth)
    }

    override fun onDestroy() {
        cekSaldoView = null
    }
}
