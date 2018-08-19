package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.POJO.DaftarHarga

interface DaftarHargaInteractor {

    fun getHarga(listener: OnSuccessgetHargaListener, userAuth: UserAuth)

    interface OnSuccessgetHargaListener {

        fun onSuccess(response_message: String, daftarHarga: List<DaftarHarga>)

        fun onelseError(response_message: String)

    }
}
