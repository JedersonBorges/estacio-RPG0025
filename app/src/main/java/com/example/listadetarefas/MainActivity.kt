package com.example.listadetarefas

import android.content.Context
import android.content.Intent
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var audioHelper: AudioHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        audioHelper = AudioHelper(this)


        if (!audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)) {
            redirectToBluetoothSettings()
        } else {
            Toast.makeText(this, "Fone Bluetooth conectado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun redirectToBluetoothSettings() {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra("EXTRA_CONNECTION_ONLY", true)
            putExtra("EXTRA_CLOSE_ON_CONNECT", true)
            putExtra("android.bluetooth.devicepicker.extra.FILTER_TYPE", 1)
        }
        startActivity(intent)
    }
}
