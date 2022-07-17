package com.getdefault.smsapplication.login

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import javax.inject.Inject


class SignInViewModel @Inject constructor(val smsApplication: Application,private val signInRepository: SignInRepository) : ViewModel() {
    fun getGoogleLoginData(googleIntentData: Intent): LiveData<ResponseData> {
        return signInRepository.handleGoogleSignInResult(googleIntentData,smsApplication.applicationContext)
    }

    fun getFacebookLoginData(): LiveData<ResponseData> {
        return signInRepository.getLoginResponse(smsApplication.applicationContext)
    }

    fun getFacebookCallback(): FacebookCallback<LoginResult?> {
        return signInRepository.facebookCallback
    }


}