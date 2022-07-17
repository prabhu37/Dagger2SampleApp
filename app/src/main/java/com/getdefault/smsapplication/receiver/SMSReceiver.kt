package com.getdefault.smsapplication.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import com.getdefault.smsapplication.listener.SmsReceiverListener
import com.getdefault.smsapplication.utils.Constants.ACTION_SMS_RECEIVER
import com.getdefault.smsapplication.utils.Utils

class SMSReceiver : BroadcastReceiver() {
   private lateinit var smsReceiverListener: SmsReceiverListener
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_SMS_RECEIVER) {
            val bundle = intent.extras

            try {
                if (bundle != null) {
                    val pdusObj = bundle["pdus"] as Array<*>?
                    for (i in pdusObj!!.indices) {
                        val currentMessage = SmsMessage.createFromPdu(
                            pdusObj[i] as ByteArray
                        )
                        val phoneNumber = currentMessage.displayOriginatingAddress.toString()
                        val message = currentMessage.displayMessageBody
                         val time = Utils.getDateCurrentTimeZone(currentMessage.timestampMillis)
                        smsReceiverListener.sendData(SMSEntity(phoneNumber, message, time))
                        Log.e("MessageReceiver", message)

                    } // end for loop
                } // bundle is null
            } catch (e: Exception) {
                Log.e("SmsReceiver", "Exception smsReceiver$e")
            }
        }
    }

    fun setOnReceiverListener(smsReceiverListener: SmsReceiverListener) {
        this.smsReceiverListener = smsReceiverListener
    }


}
