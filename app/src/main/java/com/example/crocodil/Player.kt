package com.example.crocodil

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*  Admin - создатель комнаты (может распустить лобби). Задает пароль и название комнаты

    Soldier - подключается к уже созданной комнате

 */

abstract class Player: Serializable {
    abstract val role: String

    @SerializedName("name")
    var name: String? = null
    //protected var gameSettings: Map<String, String>? = null

    /*
    fun setSettings(value: Map<String, String>) {
        gameSettings = value
    }

     */


    abstract fun start_game()
    //fun getSerializable() {}

    /*
    fun setLobbySettings(name: String, password: String) {
        gameData = mapOf("name" to name, "password" to password)
    }

     */
}

class Admin() : Player() {
    @SerializedName("role")
    override val role = "admin"

    //override lateinit var gameData: Map<String, String>

    override fun start_game() {
        //val dataForServer = startGameData("admin", )

    }
}

class Soldier : Player() {
    @SerializedName("role")
    override val role = "soldier"

    //lateinit var binding: ActivityWaitLobbiBinding

    override fun start_game() {
        //val client = Socket("127.0.0.1", 9999)

        //val output = PrintWriter(client.getOutputStream(), true)
        //val input = BufferedReader(InputStreamReader(client.inputStream))


        //output.println("Hello")
        //println("Client receiving [${input.readLine()}]")
        //client.close()
    }
}

