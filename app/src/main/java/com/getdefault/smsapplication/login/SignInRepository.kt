package com.getdefault.smsapplication.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.getdefault.smsapplication.utils.Constants
import com.getdefault.smsapplication.utils.Constants.RESPONSE_SUCCESS
import com.getdefault.smsapplication.utils.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject

class SignInRepository @Inject constructor() {
    private var faceBookLiveData: MutableLiveData<ResponseData> = MutableLiveData()
    private var googleLiveData: MutableLiveData<ResponseData> = MutableLiveData()
     lateinit var context:Context


    fun getLoginResponse(appContext: Context): LiveData<ResponseData> {
        this.context = appContext
        return faceBookLiveData
    }

    var facebookCallback = object : FacebookCallback<LoginResult?> {
        override fun onSuccess(loginResult: LoginResult?) {
            val token = loginResult?.accessToken?.token
            token?.let {
                Utils.setAuthToken(context, token)
            }
            faceBookLiveData.value = ResponseData(RESPONSE_SUCCESS, token!!)
        }

        override fun onCancel() {
            faceBookLiveData.value =
                ResponseData(Constants.RESPONSE_CANCEL, "Sign-in cancelled")
        }

        override fun onError(exception: FacebookException) {
            faceBookLiveData.value =
                ResponseData(Constants.RESPONSE_ERROR, exception.localizedMessage!!)
        }
    }


    fun handleGoogleSignInResult(googleIntentData: Intent,appContext: Context): MutableLiveData<ResponseData> {
        this.context =appContext
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(googleIntentData)
            val account = task.getResult(ApiException::class.java)
            val token = account?.idToken
            token?.let {
               Utils.setAuthToken(context, token)
            }
            googleLiveData.value = ResponseData(RESPONSE_SUCCESS, token!!)
        } catch (e: ApiException) {
            googleLiveData.value =
                ResponseData(Constants.RESPONSE_ERROR, e.localizedMessage!!)
        }
        return googleLiveData
    }


}