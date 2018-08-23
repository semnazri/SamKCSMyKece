package kcs.com.logicart

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import kcs.com.logicart.Adapter.SpinnerDenomAdapter
import kcs.com.logicart.JSON.Denom
import kcs.com.logicart.JSON.Transaction
import kcs.com.logicart.JSON.UserAuth
import kcs.com.logicart.Network.ConnectionDetector
import kcs.com.logicart.POJO.AuthResponse
import kcs.com.logicart.POJO.TransaksiPulsaResponse
import kcs.com.logicart.Presenter.CekSaldoPresenter
import kcs.com.logicart.Presenter.CekSaldoPresenterImp
import kcs.com.logicart.Presenter.TransaksiPulsaPresenter
import kcs.com.logicart.Presenter.TransaksiPulsaPresenterImp
import kcs.com.logicart.View.CekSaldoView
import kcs.com.logicart.View.TransaksiPulsaView
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.dialog_transaksi_pulsa.*
import java.util.*


class MenuActivity : AppCompatActivity(), CekSaldoView, TransaksiPulsaView {


    private var isInternetPresent: Boolean? = false
    private var cd: ConnectionDetector? = null
    private var dialog_muter: MaterialDialog? = null
    private var mDialog: MaterialDialog? = null
    private var cekSaldoPresenter: CekSaldoPresenter? = null
    private var transaksiPulsaPresenter: TransaksiPulsaPresenter? = null
    private var prefsprivate: SharedPreferences? = null
    val PREFS_PRIVATE = "PREFS_PRIVATE"
    val denom: ArrayList<Denom> = ArrayList()
    var str_denom: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        prefsprivate = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE)

        cd = ConnectionDetector(this)
        cekSaldoPresenter = CekSaldoPresenterImp(this)
        transaksiPulsaPresenter = TransaksiPulsaPresenterImp(this)

        btn_cek_saldo.setOnClickListener {
            checkConnection("1", "0", "00")

        }

        btn_hbp.setOnClickListener {
            val intent = Intent(this, DaftarHargaActivity::class.java)
            startActivity(intent)
        }

        btn_transaksi_harian.setOnClickListener {
            val intent = Intent(this, TransaksiHarianActivity::class.java)
            startActivity(intent)
        }

        btn_logout.setOnClickListener {

            val editor = prefsprivate!!.edit()
            editor.clear()
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()


        }
        btn_beli_pulsa.setOnClickListener {
            val builder = Dialog(this)
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
            builder.setContentView(R.layout.dialog_transaksi_pulsa)
            builder.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            builder.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            builder.edt_no_tlp.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//
                    if (s.count() > 3) {


                        if (s.contains("0811") || s.contains("0812") || s.contains("0813") || s.contains("0852") || s.contains("0853") || s.contains("0821")) {
                            denom.clear()
                            telkomsel()
                        } else if (s.contains("0814") || s.contains("0815") || s.contains("0816") || s.contains("0855") || s.contains("0856") || s.contains("0857") || s.contains("0858")) {
                            denom.clear()
                            indosat()
                        } else if (s.contains("0898") || s.contains("0899") || s.contains("0896")) {
                            denom.clear()
                            three()
                        } else if (s.contains("0881") || s.contains("0887")) {
                            denom.clear()
                            smartfren()
                        } else {
                            denom.clear()
                            default()
                        }

                        builder.spinner_denom.adapter = SpinnerDenomAdapter(applicationContext, denom)

                    }

                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                    // TODO Auto-generated method stub
                }

                override fun afterTextChanged(s: Editable) {

                    // TODO Auto-generated method stub
                }
            })

            builder.spinner_denom.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val item_id = (view.findViewById(R.id.id_item) as TextView).text.toString()
                    str_denom = item_id
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            })
            builder.btn_beli.setOnClickListener(View.OnClickListener {
                checkConnection("2", str_denom, builder.edt_no_tlp.text.toString())

            })

            builder.show()
        }

    }

    fun telkomsel() {
        denom.add(Denom("PILIH DENOM", "0"))
        denom.add(Denom("10.000", "10"))
        denom.add(Denom("20.000", "20"))
        denom.add(Denom("25.000", "25"))
        denom.add(Denom("50.000", "50"))
        denom.add(Denom("100.000", "100"))
    }

    fun indosat() {
        denom.add(Denom("PILIH DENOM", "0"))
        denom.add(Denom("20.000", "20"))
        denom.add(Denom("30.000", "30"))
        denom.add(Denom("50.000", "50"))
        denom.add(Denom("100.000", "100"))
    }

    fun three() {
        denom.add(Denom("PILIH DENOM", "0"))
        denom.add(Denom("5.000", "5"))
        denom.add(Denom("10.000", "10"))
        denom.add(Denom("20.000", "20"))
        denom.add(Denom("25.000", "25"))
        denom.add(Denom("30.000", "30"))
        denom.add(Denom("50.000", "50"))
        denom.add(Denom("100.000", "100"))
        denom.add(Denom("150.000", "150"))
    }

    fun smartfren() {
        denom.add(Denom("PILIH DENOM", "0"))
        denom.add(Denom("25.000", "25"))
        denom.add(Denom("30.000", "30"))
        denom.add(Denom("50.000", "50"))
        denom.add(Denom("100.000", "100"))
        denom.add(Denom("150.000", "150"))
        denom.add(Denom("200.000", "200"))
        denom.add(Denom("300.000", "300"))
        denom.add(Denom("500.000", "500"))
    }

    fun default() {
        denom.add(Denom("PILIH DENOM", "0"))
        denom.add(Denom("5.000", "5"))
        denom.add(Denom("10.000", "10"))
        denom.add(Denom("20.000", "20"))
        denom.add(Denom("25.000", "25"))
        denom.add(Denom("30.000", "30"))
        denom.add(Denom("50.000", "50"))
        denom.add(Denom("100.000", "100"))
        denom.add(Denom("150.000", "150"))
        denom.add(Denom("200.000", "200"))
    }

    private fun checkConnection(type: String, denom: String, no_tlp: String) {

        getDialog_progress()
        isInternetPresent = cd!!.isConnectingToInternet

        if (isInternetPresent!!) {
            if (type.equals("1")) {
                val namas = prefsprivate!!.getString("USERNAME", "")
                val pwd = prefsprivate!!.getString("PASSWORD", "")
                val kodeagens = prefsprivate!!.getString("KODEAGENT", "")

                val userAuth = UserAuth(namas, pwd, kodeagens)
                cekSaldoPresenter!!.getSaldo(userAuth)
            } else if (type.equals("2")) {
                val namas = prefsprivate!!.getString("USERNAME", "")
                val pwd = prefsprivate!!.getString("PASSWORD", "")
                val kodeagens = prefsprivate!!.getString("KODEAGENT", "")

                if (str_denom.equals("") || str_denom.equals("0")) {
                    getdialogerror("Harap Pilih Denom!")
                } else {
                    val transaction = Transaction(namas, pwd, kodeagens, no_tlp, str_denom)
                    transaksiPulsaPresenter!!.doTransaksi(transaction)
                }

            }

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


    override fun ResultCekSaldo(response_message: String, authResponse: AuthResponse) {
        getdialogerror("Saldo Anda : Rp." + authResponse.saldo.toString())
    }

    override fun ResultTransaksiPulsa(response_message: String, transaksiPulsaResponse: TransaksiPulsaResponse) {
        getdialogerror(transaksiPulsaResponse.status!! + " " + transaksiPulsaResponse.message)
    }

    override fun ResultError(response_message: String) {
        getdialogerror(response_message)

    }
}