package com.getdefault.smsapplication.smslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.getdefault.smsapplication.base.SmsViewModelFactory
import com.getdefault.smsapplication.base.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SmsListModule {
    @SmsListScope
    @Binds
    @IntoMap
    @ViewModelKey(SMSDbViewModel::class)
    abstract fun bindSmsDbViewModels(smsDbViewModel: SMSDbViewModel): ViewModel

    @Binds
    abstract fun bindLoginViewModelFactory(viewModelFactory: SmsViewModelFactory?): ViewModelProvider.Factory?

}