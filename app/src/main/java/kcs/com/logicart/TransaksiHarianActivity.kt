package kcs.com.logicart

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import kcs.com.logicart.Adapter.DailyTransactionAdapter
import kcs.com.logicart.JSON.DailyTransaction
import kcs.com.logicart.Network.ConnectionDetector
import kcs.com.logicart.POJO.DaftarTransaksi
import kcs.com.logicart.Presenter.TransaksiHarianPresenter
import kcs.com.logicart.Presenter.TransaksiHarianPresenterImp
import kcs.com.logicart.View.TransaksiHarianView
import kotlinx.android.synthetic.main.activity_transaksi_harian.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TransaksiHarianActivity : AppCompatActivity(), TransaksiHarianView {


    private var isInternetPresent: Boolean? = false
    private var cd: ConnectionDetector? = null
    private var dialog_muter: MaterialDialog? = null
    private var mDialog: MaterialDialog? = null
    private var transaksiharianPresenter: TransaksiHarianPresenter? = null
    private var prefsprivate: SharedPreferences? = null
    val PREFS_PRIVATE = "PREFS_PRIVATE"
    private val myCalendar = Calendar.getInstance()
    private var month_final: String? = null
    private var tgl: String? = null
    private var bln: String? = null
    private var thn: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaksi_harian)

        prefsprivate = getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE)

        cd = ConnectionDetector(this)
        transaksiharianPresenter = TransaksiHarianPresenterImp(this)

        rv_transaksi.layoutManager = LinearLayoutManager(this)


        updateLabel()


        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }
        txt_date.setOnClickListener(View.OnClickListener {
            DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()

        })
    }

    private fun updateLabel() {

        val myFormat = "dd-MMM-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("yyyy/MM/dd")

        val outputDate = SimpleDateFormat("dd")
        val outputMonth = SimpleDateFormat("MMMM")
        val outputMonthstr = SimpleDateFormat("MM")
        val outputYear = SimpleDateFormat("yyyy")

        val inputDateStr = sdf.format(myCalendar.time)
        var date1 = myCalendar.time

        try {
            date1 = inputFormat.parse(inputDateStr)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val tanggal = outputDate.format(date1)
        val bulan = outputMonth.format(date1)
        val bulanstr = outputMonthstr.format(date1)
        val tahun = outputYear.format(date1)

        if (bulan.contains("January")) {
            month_final = bulan.replace("January", "Januari")
        } else if (bulan.contains("February")) {
            month_final = bulan.replace("February", "Februari")
        } else if (bulan.contains("March")) {
            month_final = bulan.replace("March", "Maret")
        } else if (bulan.contains("April")) {
            month_final = bulan.replace("April", "April")
        } else if (bulan.contains("May")) {
            month_final = bulan.replace("May", "Mei")
        } else if (bulan.contains("June")) {
            month_final = bulan.replace("June", "Juni")
        } else if (bulan.contains("July")) {
            month_final = bulan.replace("July", "Juli")
        } else if (bulan.contains("August")) {
            month_final = bulan.replace("August", "Agustus")
        } else if (bulan.contains("September")) {
            month_final = bulan.replace("September", "September")
        } else if (bulan.contains("October")) {
            month_final = bulan.replace("October", "Oktober")
        } else if (bulan.contains("November")) {
            month_final = bulan.replace("November", "November")
        } else if (bulan.contains("December")) {
            month_final = bulan.replace("December", "Desember")
        }
        else{
            month_final = bulan
        }

        //        txt_date.setText(sdf.format(myCalendar.getTime()));
        txt_date.text = "$tanggal - $month_final - $tahun"

        tgl = tanggal
        bln = bulanstr
        thn = tahun
        checkConnection()
    }


    private fun checkConnection() {
        getDialog_progress()
        isInternetPresent = cd!!.isConnectingToInternet

        if (isInternetPresent!!) {
            val namas = prefsprivate!!.getString("USERNAME", "")
            val pwd = prefsprivate!!.getString("PASSWORD", "")
            val kodeagens = prefsprivate!!.getString("KODEAGENT", "")

            val dailyTrasaction = DailyTransaction(namas, pwd, kodeagens, thn + "-" + bln + "-" + tgl)
            transaksiharianPresenter!!.getDailyTrans(dailyTrasaction)

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

    override fun ResultError(response_message: String) {
        getdialogerror(response_message)
    }

    override fun ResultTransaksiHarian(response_message: String, daftarTransaksi: List<DaftarTransaksi>) {
        dialog_muter!!.dismiss()

        if (daftarTransaksi.size != 0){
            rv_transaksi.visibility = View.VISIBLE
            txt_nodata.visibility = View.GONE
            rv_transaksi.adapter = DailyTransactionAdapter(daftarTransaksi, this)

        }else{
            rv_transaksi.visibility = View.GONE
            txt_nodata.visibility = View.VISIBLE
        }
    }
}