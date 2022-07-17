package com.getdefault.smsapplication.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


// appComponent lives in the Application class to share its lifecycle
class SmsApplication : DaggerApplication()  {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().application(this).build()
    }
}