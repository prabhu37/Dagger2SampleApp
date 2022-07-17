package com.getdefault.smsapplication.application

import com.getdefault.smsapplication.login.LoginModule
import com.getdefault.smsapplication.login.LoginScope
import com.getdefault.smsapplication.login.SignInActivity
import com.getdefault.smsapplication.smslist.RoomDbModule
import com.getdefault.smsapplication.smslist.SmsListActivity
import com.getdefault.smsapplication.smslist.SmsListModule
import com.getdefault.smsapplication.smslist.SmsListScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @LoginScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginActivity(): SignInActivity

    @SmsListScope
    @ContributesAndroidInjector(modules = [SmsListModule::class,RoomDbModule::class])
    abstract fun contributeSmsListActivity(): SmsListActivity


}