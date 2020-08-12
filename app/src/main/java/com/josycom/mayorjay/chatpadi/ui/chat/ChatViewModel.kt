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

/**
 * Created by MayorJay
 * This ViewModel houses all the logic used in the ChatFragment
 * It is used to decouple main logic of the App making the Fragment only responsible for displaying data to the UI
 * It also has a LiveData object that emits the message to the ChatFragment
 */
class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val _msgMutableLiveData = MutableLiveData<Message>()
    val msgLiveData: LiveData<Message> = _msgMutableLiveData
    private var mqttClient: MqttClientHelper = MqttClientHelper((application))
    var myTopic = ""
    var theirTopic = ""

    fun setTopic() {
        mqttClient.myTopic = myTopic
        mqttClient.theirTopic = theirTopic
    }

    fun isConnected() = mqttClient.isConnected()

    fun publish(topic: String, msg: String) {
        mqttClient.publish(topic, msg)
    }

    fun register() {
        mqttClient.register()
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

    fun unSubscribe() {
        mqttClient.unSubscribe()
    }

    fun unregister() {
        mqttClient.unregister()
    }

    fun disconnect() {
        mqttClient.disconnect()
    }
}