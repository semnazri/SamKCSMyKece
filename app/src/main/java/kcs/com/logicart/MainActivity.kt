package kcs.com.logicart

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.POJO.AuthResponse
import kcs.com.logicart.Presenter.CekSaldoPresenter
import kcs.com.logicart.Presenter.CekSaldoPresenterImp
import kcs.com.logicart.View.CekSaldoView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_panel_akun.*

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



        btn_menu.setOnClickListener(View.OnClickListener {
            val kodeagen = prefsprivate!!.getString("KODEAGENT", "")

            if (!kodeagen.equals("")) {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            } else {
                getdialogerror("Untuk masuk ke menu ini anda harus login pada panel akun")
            }

        })
        btn_register.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        })
        btn_forgot!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent)
        })
        btn_panelakun!!.setOnClickListener(View.OnClickListener {
            val builder = Dialog(this)
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
            builder.setContentView(R.layout.dialog_panel_akun)
            builder.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            builder.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            val namas = prefsprivate!!.getString("USERNAME", "")
            val pwd = prefsprivate!!.getString("PASSWORD", "")
            val kodeagens = prefsprivate!!.getString("KODEAGENT", "")



            if (!namas.equals("")) {
                builder.edt_username.setText(namas)
                builder.edt_password.setText(pwd)
                builder.edt_kode_agent.setText(kodeagens)
            }

            builder.btn_save.setOnClickListener(View.OnClickListener {

                val userAuth = UserAuth(builder.edt_username?.text.toString(), builder.edt_password?.text.toString(), builder.edt_kode_agent?.text.toString())
                cekSaldoPresenter!!.getSaldo(userAuth)
                username = builder.edt_username?.text.toString()
                password = builder.edt_password?.text.toString()
                kodeagen = builder.edt_kode_agent?.text.toString()


            })

            builder.show()
        })

        //TODO : ada btn panel akkun, di buat fungsi dialog field gitu, kedepannya bikin mini AI gitu,
        //TODO : dengan dialog "Selamat Datang, Bantu kami mengenal anda dengan mengisi data pada menu "Panel Akun" demi kemudahan anda bertransaksi


    }

    override fun ResultCekSaldo(response_message: String, authResponse: AuthResponse) {


        if (authResponse!!.message.toString().equals("Berhasil")) {

            Toast.makeText(this, "Selamat Datang " + username, Toast.LENGTH_SHORT).show()
            val editor = prefsprivate!!.edit()
//            Log.d("aaa", username + " " + password + " " + kodeagen)

            if (editor != null) {
                editor.putString("USERNAME", username)
                editor.putString("PASSWORD", password)
                editor.putString("KODEAGENT", kodeagen)
                editor.apply()
            }
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
