package com.example.crocodil.NetworkService

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.crocodil.Player
import com.google.gson.Gson
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket
import kotlin.concurrent.thread

class NetworkService: Service() {
    private lateinit var socket: Socket
    private val mBinder: IBinder = MyBinder()
    private lateinit var writer: ObjectOutputStream
    private lateinit var reader: ObjectInputStream

    override fun onCreate() {
        super.onCreate()
        thread {
            socket = Socket("192.168.0.101", 8080)
            writer = ObjectOutputStream(socket.getOutputStream())
            reader = ObjectInputStream(socket.getInputStream())
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    fun sendSettings(player: Player) {
            writer.writeObject(Gson().toJson(player))
    }

    fun sendSettings(gameSett: Map<String, String>) {
            writer.writeObject(gameSett)
    }

    fun getResponse(): String {
        val response = reader.readObject() as String
        Log.d("RESPONSE", response)

        if (response == "Error") {
            val errorMessage = reader.readObject() as Pair<*, *>
        }

        return response
    }

    inner class MyBinder : Binder() {
        // Return this instance of MyService so clients can call public methods
        val service: NetworkService
            get() =// Return this instance of MyService so clients can call public methods
                this@NetworkService
    }

}


