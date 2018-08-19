package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.Transaction
import kcs.com.logicart.POJO.TransaksiPulsaResponse

interface TransaksiPulsaInteractor {

    fun DoTransaksiPulsa(listener: OnSuccessTransaksiListener, transaction: Transaction)

    interface OnSuccessTransaksiListener {

        fun onSuccess(response_message: String, transaksiPulsaResponse: TransaksiPulsaResponse)

        fun onelseError(response_message: String)

    }
}
