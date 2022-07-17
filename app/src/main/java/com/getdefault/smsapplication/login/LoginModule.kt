package com.getdefault.smsapplication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.getdefault.smsapplication.base.SmsViewModelFactory
import com.getdefault.smsapplication.base.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {
    @LoginScope
    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun loginViewModels(signInViewModel: SignInViewModel): ViewModel

    @Binds
    abstract fun bindLoginViewModelFactory(viewModelFactory: SmsViewModelFactory?): ViewModelProvider.Factory?



}