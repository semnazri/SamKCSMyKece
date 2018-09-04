package kcs.com.logicart

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.POJO.AuthResponse
import kcs.com.logicart.Presenter.CekSaldoPresenter
import kcs.com.logicart.Presenter.CekSaldoPresenterImp
import kcs.com.logicart.View.CekSaldoView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CekSaldoView {

    private var mDialog: MaterialDialog? = null
    private var cekSaldoPresenter: CekSaldoPresenter? = null
    val PREFS_PRIVATE = "PREFS_PRIVATE"
    private var prefsprivate: SharedPreferences? = null
    var username: String = ""
    var password: String = ""
    var kodeagen: String = ""
    fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cekSaldoPresenter = CekSaldoPresenterImp(this)
        prefsprivate = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE)

        val namas = prefsprivate!!.getString("USERNAME", "")
        val pwd = prefsprivate!!.getString("PASSWORD", "")
        val kodeagens = prefsprivate!!.getString("KODEAGENT", "")

        if (!kodeagens.equals("")) {
            to_menuActivity()
        }

        btn_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        btn_forgot!!.setOnClickListener {
            val intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent)
        }

        btn_login!!.setOnClickListener {
            username = edt_username?.text.toString()
            password = edt_password?.text.toString()
            kodeagen = edt_kode_agent?.text.toString()

            val userAuth = UserAuth(username, password, kodeagen)

            cekSaldoPresenter!!.getSaldo(userAuth)


        }
    }

    fun to_menuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun ResultCekSaldo(response_message: String, authResponse: AuthResponse) {


        if (authResponse!!.status.toString().equals("00")) {
            to_menuActivity()
            Toast.makeText(this, "Selamat Datang " + username, Toast.LENGTH_SHORT).show()
            val editor = prefsprivate!!.edit()
//            Log.d("aaa", username + " " + password + " " + kodeagen)

            if (editor != null) {
                editor.putString("USERNAME", username)
                editor.putString("PASSWORD", password)
                editor.putString("KODEAGENT", kodeagen)
                editor.apply()
            }
        }else if (authResponse!!.status.toString().equals("02")){
            getdialogerror(authResponse!!.message.toString())
        }
    }

    private fun getdialogerror(response_message: String) {
        mDialog = MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(response_message)
                .positiveText("Close")
                .cancelable(false)
                .onPositive { dialog, which -> mDialog!!.dismiss() }
                .show()
    }

    override fun ResultError(response_message: String) {

        getdialogerror(response_message)
//        toast(response_message)
    }
}
