package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.Transaction
import kcs.com.logicart.POJO.TransaksiPulsaResponse
import kcs.com.logicart.View.TransaksiPulsaView

class TransaksiPulsaPresenterImp(private var transaksiPulsaView: TransaksiPulsaView?) : TransaksiPulsaPresenter, TransaksiPulsaInteractor.OnSuccessTransaksiListener {


    override fun doTransaksi(transaction: Transaction) {
        transaksiPulsaInteractor.DoTransaksiPulsa(this, transaction)
    }

    override fun onSuccess(response_message: String, transaksiPulsaResponse: TransaksiPulsaResponse) {
        if (transaksiPulsaView != null) {
            transaksiPulsaView!!.ResultTransaksiPulsa(response_message, transaksiPulsaResponse)
        }
    }

    private val transaksiPulsaInteractor: TransaksiPulsaInteractor

    init {
        this.transaksiPulsaInteractor = TransaksiPulsaInteractorImp()
    }

    override fun onelseError(response_message: String) {
        if (transaksiPulsaView != null) {
            transaksiPulsaView!!.ResultError(response_message)
        }
    }


    override fun onDestroy() {
        transaksiPulsaView = null
    }
}
