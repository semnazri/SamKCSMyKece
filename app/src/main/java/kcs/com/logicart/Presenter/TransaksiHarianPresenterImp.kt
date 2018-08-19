package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.DailyTransaction
import kcs.com.logicart.POJO.DaftarTransaksi
import kcs.com.logicart.View.TransaksiHarianView

class TransaksiHarianPresenterImp(private var transaksiHarianView: TransaksiHarianView?) : TransaksiHarianPresenter, TransaksiHarianInteractor.OnSuccessgetDailyTransListener {
    override fun getDailyTrans(dailyTransaction: DailyTransaction) {
        traksaksiharianInteractor.getDailyTrans(this, dailyTransaction)
    }

    private val traksaksiharianInteractor: TransaksiHarianInteractor

    init {
        this.traksaksiharianInteractor = TransaksiHarianInteractorImp()
    }


    override fun onSuccess(response_message: String, daftartransaksix: List<DaftarTransaksi>) {
        if (transaksiHarianView != null) {
            transaksiHarianView!!.ResultTransaksiHarian(response_message, daftartransaksix)
        }
    }


    override fun onelseError(response_message: String) {
        if (transaksiHarianView != null) {
            transaksiHarianView!!.ResultError(response_message)
        }
    }

    override fun onDestroy() {
        transaksiHarianView = null
    }
}
