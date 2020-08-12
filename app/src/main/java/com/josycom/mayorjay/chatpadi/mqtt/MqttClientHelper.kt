package com.josycom.mayorjay.chatpadi.mqtt

import android.content.Context
import android.util.Log
import com.josycom.mayorjay.chatpadi.utils.AppConstants.HIVE_CONNECTION_CLEAN_SESSION
import com.josycom.mayorjay.chatpadi.utils.AppConstants.HIVE_CONNECTION_KEEP_ALIVE_INTERVAL
import com.josycom.mayorjay.chatpadi.utils.AppConstants.HIVE_CONNECTION_RECONNECT
import com.josycom.mayorjay.chatpadi.utils.AppConstants.HIVE_CONNECTION_TIMEOUT
import com.josycom.mayorjay.chatpadi.utils.AppConstants.HIVE_MQTT_SERVER_URL
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

/**
 * Helper class for establishing connection to the MQTT server
 * Publishing and Subscribing to the server
 */
class MqttClientHelper(private val context: Context?) {

    companion object {
        const val TAG = "MqttClientHelper"
    }
    var myTopic = ""
    var theirTopic = ""
    private var mqttAndroidClient: MqttAndroidClient
    private val serverUrl = HIVE_MQTT_SERVER_URL
    private val clientId = MqttClient.generateClientId()

    init {
        mqttAndroidClient = MqttAndroidClient(context, serverUrl, clientId)
        mqttAndroidClient.setCallback(object: MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Log.w(TAG, serverURI!!)
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.w(TAG, message.toString())
            }

            override fun connectionLost(cause: Throwable?) {
                cause?.printStackTrace()
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
            }
        } )
        connect()
    }

    private fun connect() {
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.apply {
            connectionTimeout = HIVE_CONNECTION_TIMEOUT
            isAutomaticReconnect = HIVE_CONNECTION_RECONNECT
            isCleanSession = HIVE_CONNECTION_CLEAN_SESSION
            keepAliveInterval = HIVE_CONNECTION_KEEP_ALIVE_INTERVAL
        }
        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, object: IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.w(TAG, "Connection to: $serverUrl; successful")
                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.apply {
                        isBufferEnabled = true
                        bufferSize = 100
                        isPersistBuffer = false
                        isDeleteOldestMessages = false
                    }
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions)
                    subscribe(myTopic)
                    subscribe(theirTopic)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.w(TAG, "Failed to connect to: $serverUrl; $exception")
                }
            } )
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }
    }

    private fun subscribe(topic: String, qos: Int = 1) {
        try {
            mqttAndroidClient.subscribe(topic, qos)
        } catch (ex: MqttException){
            ex.printStackTrace()
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 1) {
        try {
            val encodedPayload = msg.toByteArray(charset("UTF-8"))
            mqttAndroidClient.publish(topic, encodedPayload, qos, false)
            Log.d(TAG, "Message published to topic `$topic`: $msg")
        } catch (ex: MqttException) {
            Log.d(TAG, "Error Publishing to $topic: " + ex.message)
            ex.printStackTrace()
        }
    }

    fun unSubscribe() {
        mqttAndroidClient.unsubscribe(myTopic)
        mqttAndroidClient.unsubscribe(theirTopic)
    }

    fun setCallback(callback: MqttCallbackExtended?) {
        mqttAndroidClient.setCallback(callback)
    }

    fun isConnected() = mqttAndroidClient.isConnected

    fun register() {
        mqttAndroidClient.registerResources(context)
    }

    fun unregister() {
        mqttAndroidClient.unregisterResources()
    }

    fun disconnect() {
        mqttAndroidClient.disconnect()
    }
}