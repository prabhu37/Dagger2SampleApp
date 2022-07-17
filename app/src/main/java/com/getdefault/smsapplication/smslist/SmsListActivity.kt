package com.getdefault.smsapplication.smslist

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.getdefault.smsapplication.R
import com.getdefault.smsapplication.base.BaseActivity
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import com.getdefault.smsapplication.databinding.ActivitySmsListBinding
import com.getdefault.smsapplication.receiver.SMSReceiver
import com.getdefault.smsapplication.utils.Constants.ACTION_SMS_RECEIVER
import com.google.gson.Gson
import javax.inject.Inject


class SmsListActivity : BaseActivity() {
    lateinit var binding: ActivitySmsListBinding
    lateinit var smsDbViewModel: SMSDbViewModel
    lateinit var smsReceiver: SMSReceiver
    lateinit var smsListAdapter: SmsListAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(smsReceiver)
    }

    private fun init() {
        setViewModel()
        setUI()
    }

    private fun setUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sms_list)
        smsListAdapter = SmsListAdapter()
        binding.rvMessageList.layoutManager = LinearLayoutManager(this)
        binding.rvMessageList.adapter = smsListAdapter

    }

    private fun setViewModel() {
        smsReceiver = SMSReceiver()
        registerReceiver(smsReceiver, IntentFilter(ACTION_SMS_RECEIVER))
        smsDbViewModel = ViewModelProvider(this,viewModelFactory).get(SMSDbViewModel::class.java)
        smsDbViewModel.getReceiver(smsReceiver)
        smsDbViewModel.getAllMessages().observe(this, {
            Log.e(TAG, Gson().toJson(it))
            if (it.isNotEmpty()) {
                binding.tvNoMessages.visibility = View.GONE
            } else {
                binding.tvNoMessages.visibility = View.VISIBLE
            }
            smsListAdapter.submitList(messageList = it as MutableList<SMSEntity>)
        })

    }

    companion object {
        const val TAG = "SmsListActivity"
    }
}

