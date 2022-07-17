package com.getdefault.smsapplication.smslist

import android.app.Application
import androidx.lifecycle.LiveData
import com.getdefault.smsapplication.data.db.SMSDao
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import com.getdefault.smsapplication.listener.SmsReceiverListener
import com.getdefault.smsapplication.receiver.SMSReceiver
import com.getdefault.smsapplication.utils.Constants.NUMBER_OF_THREADS
import java.util.concurrent.Executors
import javax.inject.Inject

class SMSDbRepository @Inject constructor (val application: Application){
    private lateinit var allMessagedLiveData: LiveData<List<SMSEntity>>
    @Inject
    lateinit var smsDao:SMSDao

    fun getAllMessages(): LiveData<List<SMSEntity>> {
        allMessagedLiveData =  smsDao.getAllMessages()
        return allMessagedLiveData
    }

    fun setReceiver(smsReceiver: SMSReceiver) {
        smsReceiver.setOnReceiverListener(object : SmsReceiverListener {
            override fun sendData(smsEntity: SMSEntity) {
                insert(smsEntity)
            }
        })
    }

    fun insert(smsEntity: SMSEntity) {
        val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
        databaseWriteExecutor.execute { smsDao.insertMessage(smsEntity) }
    }


}