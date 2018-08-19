package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.DailyTransaction
import kcs.com.logicart.POJO.DaftarTransaksi

interface TransaksiHarianInteractor {

    fun getDailyTrans(listener: OnSuccessgetDailyTransListener, dailytransaction: DailyTransaction)

    interface OnSuccessgetDailyTransListener {

        fun onSuccess(response_message: String, daftartransaksix: List<DaftarTransaksi>)

        fun onelseError(response_message: String)

    }
}
