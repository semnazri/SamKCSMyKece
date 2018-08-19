package kcs.com.logicart.View

import kcs.com.logicart.POJO.DaftarTransaksi

interface TransaksiHarianView {

    fun ResultTransaksiHarian(response_message: String, daftarTransaksi: List<DaftarTransaksi>)

    fun ResultError(response_message: String)
}