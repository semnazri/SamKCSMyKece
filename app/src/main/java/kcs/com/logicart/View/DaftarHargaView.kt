package kcs.com.logicart.View

import kcs.com.logicart.POJO.DaftarHarga

interface DaftarHargaView {

    fun ResultHarga(response_message: String, daftarHarga: List<DaftarHarga>)

    fun ResultError(response_message: String)
}
