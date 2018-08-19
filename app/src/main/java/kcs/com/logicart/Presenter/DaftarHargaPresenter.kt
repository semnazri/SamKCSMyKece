package kcs.com.logicart.Presenter

import kcs.com.logicart.JSON.UserAuth

interface DaftarHargaPresenter {

    fun getHarga(userAuth: UserAuth)
    fun onDestroy()
}
