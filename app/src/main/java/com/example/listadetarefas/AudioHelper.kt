package com.example.listadetarefas

import android.content.Context
import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.content.pm.PackageManager
import android.util.Log

class AudioHelper(private val context: Context) {

    private val audioManager: AudioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun audioOutputAvailable(type: Int): Boolean {
        if (!context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
            return false
        }
        return audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS).any { it.type == type }
    }


    fun registerAudioCallback() {
        audioManager.registerAudioDeviceCallback(object : AudioDeviceCallback() {
            override fun onAudioDevicesAdded(addedDevices: Array<out AudioDeviceInfo>?) {
                super.onAudioDevicesAdded(addedDevices)
                if (audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)) {
                    Log.d("AudioHelper", "Fone de ouvido Bluetooth conectado!")
                }
            }

            override fun onAudioDevicesRemoved(removedDevices: Array<out AudioDeviceInfo>?) {
                super.onAudioDevicesRemoved(removedDevices)
                if (!audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)) {
                    Log.d("AudioHelper", "Fone de ouvido Bluetooth desconectado!")
                }
            }
        }, null)
    }
}
