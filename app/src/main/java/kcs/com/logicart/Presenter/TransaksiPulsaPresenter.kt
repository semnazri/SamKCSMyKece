package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.Transaction

interface TransaksiPulsaPresenter {

    fun doTransaksi(transaction: Transaction)
    fun onDestroy()
}
