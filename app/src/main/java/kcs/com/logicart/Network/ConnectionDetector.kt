package kcs.com.logicart.Network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by Semmy on 10/08/2018.
 */
class ConnectionDetector(private val mContext: Context) {


    val isConnectingToInternet: Boolean
        get() {
            val connectivity = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val info = connectivity.allNetworkInfo
                if (info != null)
                    for (i in info.indices)
                        if (info[i].state == NetworkInfo.State.CONNECTED) {
                            return true
                        }

            }
            return false
        }
}