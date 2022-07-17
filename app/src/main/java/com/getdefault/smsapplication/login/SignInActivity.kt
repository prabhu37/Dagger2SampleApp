package com.getdefault.smsapplication.login


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.getdefault.smsapplication.R
import com.getdefault.smsapplication.base.BaseActivity
import com.getdefault.smsapplication.databinding.ActivityLoginBinding
import com.getdefault.smsapplication.smslist.SmsListActivity
import com.getdefault.smsapplication.utils.Constants.FB_SIGN_IN
import com.getdefault.smsapplication.utils.Constants.GOOGLE_SIGN_IN
import com.getdefault.smsapplication.utils.Constants.RESPONSE_CANCEL
import com.getdefault.smsapplication.utils.Constants.RESPONSE_ERROR
import com.getdefault.smsapplication.utils.Constants.RESPONSE_SUCCESS
import com.getdefault.smsapplication.utils.Constants.SMS_REQUEST_CODE
import com.getdefault.smsapplication.utils.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import javax.inject.Inject


class SignInActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    lateinit var signInViewModel: SignInViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private var callbackManager: CallbackManager? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.RECEIVE_SMS), SMS_REQUEST_CODE
            )
        }
        setUI()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "$requestCode")
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GOOGLE_SIGN_IN -> {
                signInViewModel.getGoogleLoginData(data!!).observe(this, {
                    loginResult(responseData = it)

                })
            }
            FB_SIGN_IN -> {
                callbackManager?.let {
                    callbackManager!!.onActivityResult(requestCode, resultCode, data)
                }

            }
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btSignInButton -> {
                googleSignIn(this)
            }
        }

    }

    private fun setUI() {
        googleLoginInitialization(this.applicationContext)
        fbLoginInitialization(this.applicationContext)
        signInViewModel = ViewModelProvider(this, viewModelFactory).get(SignInViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.btSignInButton.setOnClickListener(this)
        binding.btFacebookLoginButton.setReadPermissions("email", "public_profile", "user_friends")
        binding.btFacebookLoginButton.registerCallback(
            callbackManager,
            signInViewModel.getFacebookCallback()
        )
        signInViewModel.getFacebookLoginData().observe(this@SignInActivity, {
            loginResult(responseData = it)

        })

    }

    private fun loginResult(responseData: ResponseData) {
        when (responseData.responseType) {
            RESPONSE_SUCCESS -> {
                goToHomeActivity()
            }
            RESPONSE_ERROR -> {
                Toast.makeText(this, responseData.result, Toast.LENGTH_SHORT).show()
            }
            RESPONSE_CANCEL -> {
                Toast.makeText(this, responseData.result, Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, SmsListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.RECEIVE_SMS), SMS_REQUEST_CODE
                )
            }

        }
    }

    private fun fbLoginInitialization(context: Context) {
        Log.e(TAG, "FbInitialization")
        FacebookSdk.sdkInitialize(context)
        callbackManager = CallbackManager.Factory.create()
    }

    private fun googleLoginInitialization(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.client))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    private fun googleSignIn(activity: Activity) {
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }


    companion object {
        const val TAG = "SignInActivity"
    }
}
