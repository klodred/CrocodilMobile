package com.example.crocodil
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.crocodil.NetworkService.NetworkService
import com.example.crocodil.databinding.ActivityMainBinding
import com.example.crocodil.databinding.FragmentCreateLobbyBinding
import com.example.crocodil.startWindow.LobbyConnectDialogFragment
import java.net.Socket


class MainActivity : AppCompatActivity() {
    lateinit var bindingMain: ActivityMainBinding
    lateinit var bindingSettings: FragmentCreateLobbyBinding
    lateinit var socket: Socket
    var mService: NetworkService? = null
    val mTAG = MainActivity::class.java.simpleName

    // Boolean to check if our activity is bound to service or not
    var mIsBound: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        bindingSettings = FragmentCreateLobbyBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            Log.d(mTAG, "ServiceConnection: connected to service.")
            // We've bound to MyService, cast the IBinder and get MyBinder instance
            val binder = iBinder as NetworkService.MyBinder
            mService = binder.service
            mIsBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d(mTAG, "ServiceConnection: disconnected from service.")
            mIsBound = false
        }
    }

    private fun bindService() {
        Intent(this, NetworkService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun buttonCreateLobby(view: View) {
        bindService()
        //startAsServer()
        val fragmentManager = supportFragmentManager
        val newFragment = LobbyConnectDialogFragment()
        val bundle = Bundle()
        val player = Admin()
        bundle.putSerializable("player", player)
        //bundle.putSerializable("socket", socket)
        newFragment.arguments = bundle
        newFragment.show(fragmentManager, "dialog")
    }



    @RequiresApi(Build.VERSION_CODES.Q)
    fun startAsServer() {
        startService(Intent(this, NetworkService::class.java))
        //thread {
          //  socket = Socket("192.168.0.101", 8080)
        //}
        //val socket = Socket()
        //val p = InetAddress.getLocalHost()
        //val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        //val a = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        //val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        //val wInfo = wifiManager.connectionInfo

        // Extracting the information from the received connection info
        //val ipAddress = Formatter.formatIpAddress(wInfo.ipAddress)
        //Log.d("Network address", ipAddress)
        //val server = ServerSocket(9999)
        //server.bind(wInfo.ipAddress)
        //val socket = server.accept()

        /*
        if (a != null) {
            Log.d("network capabilities", "not null")
            val o = a.transportInfo as WifiInfo
            val id = o.networkId
            Log.d("Network address", id.toString())
        }
        else {
            Log.d("network capabilities", "is null")
        }
         */
    }

    fun connect_to_lobbi(view: View) {
        /*
        val bundle = Bundle()
        val fragmentManager = supportFragmentManager
        val newFragment = FireMissilesDialogFragment()
        bundle.putString("hello", "Hello")
        newFragment.setArguments(bundle)
        newFragment.show(fragmentManager, "dialog")

         */
        val socket = Socket("192.168.0.100", 9999)
    }

}


