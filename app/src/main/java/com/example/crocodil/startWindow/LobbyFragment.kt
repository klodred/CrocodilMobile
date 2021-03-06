package com.example.crocodil.startWindow

import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.crocodil.NetworkService.NetworkService
import com.example.crocodil.Player
import com.example.crocodil.databinding.FragmentCreateLobbyBinding
import com.example.crocodil.lobby.WaitLobbyActivity
import kotlin.concurrent.thread


class LobbyConnectDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentCreateLobbyBinding
    val mTAG = LobbyConnectDialogFragment::class.java.simpleName
    // Variable for storing instance of our service class
    var mService: NetworkService? = null

    // Boolean to check if our activity is bound to service or not
    var mIsBound: Boolean? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        bindService()
        binding = FragmentCreateLobbyBinding.inflate(layoutInflater)
        val bundle = arguments
        val player = bundle!!.getSerializable("player") as Player

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .setNegativeButton("Закрыть",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.dismiss()
                    })

            if (player.role == "admin") {
                builder.setPositiveButton("Создать",
                    DialogInterface.OnClickListener { dialog, id ->
                        //if (processSettingsLobby()) {

                        val gameSettings = mapOf("name" to binding.name.text.toString(),
                            "password" to binding.password.text.toString())
                        player.name = binding.playerName.text.toString()
                        bindService()
                        thread {
                            mService?.sendSettings(player)
                            mService?.sendSettings(gameSettings)
                        }


                        //mService?.sendSettings(gameSettings)
                        val randomIntent = Intent(context, WaitLobbyActivity::class.java)
                        randomIntent.putExtra("player", "run")
                        startActivity(randomIntent)
                        //}
                    })

            } else {
                builder.setPositiveButton("Подключиться",
                    DialogInterface.OnClickListener { dialog, id ->
                        val gameSettings = mapOf("name" to binding.name.text.toString(),
                            "password" to binding.password.text.toString())
                        player.name = binding.playerName.text.toString()
                        bindService()
                        thread {
                            mService?.sendSettings(player)
                            mService?.sendSettings(gameSettings)
                            val message = mService!!.getResponse()
                            val myDialogFragment = WarningSettingsDialogFragment(message)
                            val manager = parentFragmentManager
                            myDialogFragment.show(manager, "myDialog")
                        }

                        val randomIntent = Intent(context, WaitLobbyActivity::class.java)
                        randomIntent.putExtra("player", "run")
                        startActivity(randomIntent)
                        //}
                    })
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
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
        Intent(context, NetworkService::class.java).also { intent ->
              requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }
}

class WarningSettingsDialogFragment(private val message: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage(message)
                .setNegativeButton("Закрыть",
                    DialogInterface.OnClickListener { dialog, id ->
                        parentFragmentManager.popBackStack()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
/*
    private fun processSettingsLobby(): Boolean {
        if (binding.name.length() < 4 || binding.name.length() < 4) {
            val dialog = WarningSettingsDialogFragment()
            dialog.show()
            return false

        }  else
            return true
    }
}


class WarningSettingsDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setNegativeButton("Закрыть",
                    DialogInterface.OnClickListener { dialog, id ->
                        getFragmentManager()?.popBackStack()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

 */




