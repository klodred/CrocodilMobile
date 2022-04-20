package com.example.crocodil.lobby
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.crocodil.databinding.ActivityWaitLobbiBinding


class WaitLobbyActivity : AppCompatActivity() {
    lateinit var binding: ActivityWaitLobbiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitLobbiBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun buttonSend(view: View) {
        val text = binding.test.text
    }
}