package kcs.com.logicart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import kcs.com.logicart.Adapter.DaftarHargaAdapter
import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.Network.ConnectionDetector
import kcs.com.logicart.POJO.DaftarHarga
import kcs.com.logicart.Presenter.DaftarHargaPresenter
import kcs.com.logicart.Presenter.DaftarHargaPresenterImp
import kcs.com.logicart.View.DaftarHargaView
import kotlinx.android.synthetic.main.activity_daftargarga.*

class DaftarHargaActivity : AppCompatActivity(), DaftarHargaView {

    private var isInternetPresent: Boolean? = false
    private var cd: ConnectionDetector? = null
    private var dialog_muter: MaterialDialog? = null
    private var mDialog: MaterialDialog? = null
    private var daftarHargaPresenter: DaftarHargaPresenter? = null
    private var prefsprivate: SharedPreferences? = null
    val PREFS_PRIVATE = "PREFS_PRIVATE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftargarga)

        prefsprivate = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE)

        cd = ConnectionDetector(this)
        daftarHargaPresenter = DaftarHargaPresenterImp(this)

        rv_harga.layoutManager = LinearLayoutManager(this)
        checkConnection()
    }


    private fun checkConnection() {

        getDialog_progress()
        isInternetPresent = cd!!.isConnectingToInternet

        if (isInternetPresent!!) {
            val namas = prefsprivate!!.getString("USERNAME", "")
            val pwd = prefsprivate!!.getString("PASSWORD", "")
            val kodeagens = prefsprivate!!.getString("KODEAGENT", "")
            val userAuth = UserAuth(namas, pwd, kodeagens)
            daftarHargaPresenter!!.getHarga(userAuth)

        } else {
            getdialogerror("Tidak Ada koneksi Internet")
        }

    }

    private fun getdialogerror(response_message: String) {
        dialog_muter!!.dismiss()
        mDialog = MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(response_message)
                .positiveText("Close")
                .onPositive { dialog, which -> mDialog!!.dismiss() }
                .show()
    }

    private fun getDialog_progress() {
        dialog_muter = MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show()
    }

    override fun ResultHarga(response_message: String, daftarHarga: List<DaftarHarga>) {
        dialog_muter!!.dismiss()
        rv_harga.adapter = DaftarHargaAdapter(daftarHarga, this)
    }

    override fun ResultError(response_message: String) {

        getdialogerror(response_message)
    }

}