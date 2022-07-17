package com.getdefault.smsapplication.smslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import com.getdefault.smsapplication.receiver.SMSReceiver
import javax.inject.Inject


class SMSDbViewModel @Inject constructor (private val smsDbRepository: SMSDbRepository): ViewModel() {
    fun getAllMessages(): LiveData<List<SMSEntity>> {
        return smsDbRepository.getAllMessages()
    }
    fun getReceiver(smsReceiver: SMSReceiver){
        smsDbRepository.setReceiver(smsReceiver)
    }

}