package com.getdefault.smsapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.support.DaggerAppCompatActivity


open class BaseActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



}