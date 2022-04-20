package com.example.crocodil.NetworkService

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.crocodil.Player
import com.example.crocodil.Test
import com.google.gson.Gson
import java.io.ObjectOutputStream
import java.net.Socket
import kotlin.concurrent.thread

class NetworkService: Service() {
    private lateinit var socket: Socket
    private val mBinder: IBinder = MyBinder()

    override fun onCreate() {
        super.onCreate()
        thread {
            socket = Socket("192.168.0.101", 8080)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    fun sendSettings(player: Player) {
        thread {
            //val selectorManager = ActorSelectorManager(Dispatchers.IO)
            //val socket = aSocket(selectorManager).tcp().connect("127.0.0.1", 9002)
            //val sendChannel = socket.openWriteChannel(autoFlush = true)
            val writer = ObjectOutputStream(socket.getOutputStream())

            val gson = Gson()
            //val gsonPretty = GsonBuilder().setPrettyPrinting().create()
            val tut = Test("Tut #1")
            val jsonTut: String = gson.toJson(tut)
            writer.writeObject(jsonTut)
            //writer.writeInt(25)
            writer.close()
        }
    }

    inner class MyBinder : Binder() {
        // Return this instance of MyService so clients can call public methods
        val service: NetworkService
            get() =// Return this instance of MyService so clients can call public methods
                this@NetworkService
    }

}