package com.getdefault.smsapplication.listener

import com.getdefault.smsapplication.data.db.entity.SMSEntity


interface SmsReceiverListener {
    fun sendData(smsEntity: SMSEntity)

}