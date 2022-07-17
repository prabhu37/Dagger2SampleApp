package com.getdefault.smsapplication.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
 class ActivityModule(private var activity: AppCompatActivity?) {


    @Provides
    @ActivityContext
    fun provideContext(): Context? {
        return activity
    }

    @Provides
    fun provideActivity(): AppCompatActivity? {
        return activity
    }


//    //Alive until activity lives
//    @ActivityScope
//    @ContributesAndroidInjector(modules = [LoginModule::class, FragmentFactoryModule::class])
//    internal abstract fun contributeMainActivity(): MainActivity


}