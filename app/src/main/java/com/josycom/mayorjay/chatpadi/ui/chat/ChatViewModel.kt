package com.josycom.mayorjay.chatpadi.ui.chat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.josycom.mayorjay.chatpadi.data.Message
import com.josycom.mayorjay.chatpadi.mqtt.MqttClientHelper
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val _msgMutableLiveData = MutableLiveData<Message>()
    val msgLiveData: LiveData<Message> = _msgMutableLiveData
    private var mqttClient: MqttClientHelper = MqttClientHelper((application))


    fun publish(topic: String, msg: String) {
        mqttClient.publish(topic, msg)
    }

    fun subscribeToMyTopic(topic: String) {
        mqttClient.subscribe(topic)
    }

    fun subscribeToTheirTopic(topic: String) {
        mqttClient.subscribe(topic)
    }

    fun setCallBack() {
        mqttClient.setCallback(object: MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Log.d(ChatFragment.TAG, "Connected to host" + serverURI!!)
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                if (message.toString().isNotEmpty()) {
                    val msg = Message(topic, message.toString())
                    _msgMutableLiveData.value = msg
                } else {
                    Log.d(ChatFragment.TAG, "No message yet")
                }
            }

            override fun connectionLost(cause: Throwable?) {
                Log.w(ChatFragment.TAG, "Connection to host lost, caused by " + cause?.message)
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.d(ChatFragment.TAG, "Message published to host")
            }
        })
    }

    fun disconnect() {
        mqttClient.disconnect()
    }

    fun isConnected() = mqttClient.isConnected()
}