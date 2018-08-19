package kcs.com.logicart.View

import kcs.com.logicart.POJO.TransaksiPulsaResponse

interface TransaksiPulsaView {
    fun ResultTransaksiPulsa(response_message: String, transaksiPulsaResponse: TransaksiPulsaResponse)

    fun ResultError(response_message: String)
}