package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.POJO.DaftarHarga
import kcs.com.logicart.View.DaftarHargaView

class DaftarHargaPresenterImp(private var daftarHargaView: DaftarHargaView?) : DaftarHargaPresenter, DaftarHargaInteractor.OnSuccessgetHargaListener {


    override fun onSuccess(response_message: String, daftarHarga: List<DaftarHarga>) {
        if (daftarHargaView != null) {
            daftarHargaView!!.ResultHarga(response_message, daftarHarga)
        }    }


    private val daftarHargaInteractor: DaftarHargaInteractor


    init {
        this.daftarHargaInteractor = DaftarHargaInteractorImp()
    }

    override fun getHarga(userAuth: UserAuth) {
        daftarHargaInteractor.getHarga(this, userAuth)
    }


    override fun onelseError(response_message: String) {
        if (daftarHargaView != null) {
            daftarHargaView!!.ResultError(response_message)
        }
    }


    override fun onDestroy() {
        daftarHargaView = null
    }
}
