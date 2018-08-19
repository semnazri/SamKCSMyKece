package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.DailyTransaction

interface TransaksiHarianPresenter {

    fun getDailyTrans(dailyTransaction: DailyTransaction)
    fun onDestroy()
}
