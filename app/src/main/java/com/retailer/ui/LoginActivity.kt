package com.retailer.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.retailer.R
import com.retailer.api.ApiCallingRequest
import com.retailer.preference.SessionData
import com.retailer.utils.MyProgressDialog
import com.retailer.utils.PopupUtils
import de.footprinttech.wms.db.DataBaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import com.facebook.login.LoginManager

import com.facebook.CallbackManager
import com.facebook.FacebookException

import org.json.JSONException

import com.facebook.GraphResponse

import org.json.JSONObject

import com.facebook.GraphRequest

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback
import com.facebook.GraphRequest.GraphJSONObjectCallback
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.NullPointerException
import java.util.*


class LoginActivity : BaseActivity() {

    private lateinit var rlLoginSignUp: RelativeLayout
    private lateinit var txtCreate: TextView
    private lateinit var edtUser: EditText
    private lateinit var edtPassword: EditText
    private lateinit var lnt_facebook_lgoing: LinearLayout
    private lateinit var lnr_google_signin: LinearLayout
    private lateinit var pd: MyProgressDialog
    private var callbackManager: CallbackManager? = null
    private var  login_button : LoginButton?=null
    private var loginManager: LoginManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        try {
            init()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun facebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        // Callback registration
        login_button!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
                if (loginResult != null) {
                    try {
                        Log.v("@@@", "----onSuccess: ")
//                        val name = `object`.getString("name")
//                        val email = `object`.getString("email")
//                        val fbUserID = `object`.getString("id")

                        // do action after Facebook login success
                        // or call your API
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onCancel() {
                // App code
                Log.v("@@@", "----onCancel: ")
            }

            override fun onError(exception: FacebookException) {
                // App code
                Log.v("@@@", "----onError: "+ exception.message)
            }
        })


        LoginManager.getInstance().registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        val request = GraphRequest.newMeRequest(
                            loginResult.accessToken
                        ) { `object`, response ->
                            if (`object` != null) {
                                try {
                                    val name = `object`.getString("name")
                                    val email = `object`.getString("email")
                                    val fbUserID = `object`.getString("id")

                                    // do action after Facebook login success
                                    // or call your API
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                } catch (e: NullPointerException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        val parameters = Bundle()
                        parameters.putString(
                            "fields",
                            "id, name, email, gender, birthday"
                        )
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {
                        Log.v("LoginScreen", "---onCancel")
                    }

                    override fun onError(error: FacebookException) {
                        // here write code when get error
                        Log.v(
                            "LoginScreen", "----onError: "
                                    + error.message
                        )
                    }
                })
    }

    private val EMAIL = "email"
    override fun init() {
        rlLoginSignUp = findViewById(R.id.rlLoginSignUp)
        txtCreate = findViewById(R.id.txtCreate)
        edtUser = findViewById(R.id.edtUser)
        edtPassword = findViewById(R.id.edtPassword)
        lnt_facebook_lgoing= findViewById(R.id.lnt_facebook_lgoing)
        lnr_google_signin= findViewById(R.id.lnr_google_signin)
        pd = MyProgressDialog(this, R.drawable.icons8_loader)
        login_button =  findViewById(R.id.login_button) as LoginButton
        login_button!!.setReadPermissions(Arrays.asList(EMAIL))
        rlLoginSignUp.setOnClickListener {
            if (isValidateData()) {
                val deviceTokenFB = SessionData.getInstance(applicationContext).getFirebaseToken()?:""
                apiCallingForLogin(
                    edtUser.text.toString().trim(),
                    edtPassword.text.toString().trim(),
                    deviceTokenFB
                )

            }
        }

        lnr_google_signin.setOnClickListener {
            GoogleSignIn()
        }

        lnt_facebook_lgoing.setOnClickListener {
            facebookLogin()
        }

        txtCreate.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }


    private fun isValidateData(): Boolean {
        val user = edtUser.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        if (TextUtils.isEmpty(user)) {
            PopupUtils.alertMessage(this, "Please enter user name")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            PopupUtils.alertMessage(this, "Please enter valid user/email")
            return false
        } else if (TextUtils.isEmpty(password)) {
            PopupUtils.alertMessage(this, "Please enter password")
            return false
        } else {
            return true
        }
    }


    private fun apiCallingForLogin(email: String, password: String, tokenFB: String) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["email"] = email
            params["password"] = password
            params["social_id"] = ""
            params["device_token"] = tokenFB
            try {
                val responseOfLogin =
                    apiCallingRequest.apiCallingForLogin(
                        params
                    )
                val message = responseOfLogin.message
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if (TextUtils.isEmpty(message)) {
                        responseOfLogin.token?.let {
                            SessionData.getInstance(this@LoginActivity).saveToken(it)
                        }
                        SessionData.getInstance(this@LoginActivity)
                            .saveUserId(responseOfLogin.user?.id.toString())
                        lifecycleScope.launch(Dispatchers.IO) {
                            responseOfLogin.user?.let {
                                DataBaseHelper.getDatabaseDao(this@LoginActivity).saveUser(it)
                            }
                            withContext(Dispatchers.Main) {
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent);
                                overridePendingTransition(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left
                                )
                                finish()
                            }
                        }
                    } else {
                        PopupUtils.alertMessage(this@LoginActivity, message!!)
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    private fun isUserSignedIn(): Boolean {

        val account = com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this)
        return account != null

    }

    fun getGoogleSinginClient() : GoogleSignInClient {
        /**
         * Configure sign-in to request the user's ID, email address, and basic
         * profile. ID and basic profile are included in DEFAULT_SIGN_IN.
         */
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()


        /**
         * Build a GoogleSignInClient with the options specified by gso.
         */
        return com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso);
    }

    private fun GoogleSignIn(){
        val signInIntent = getGoogleSinginClient().signInIntent
        startActivityForResult(signInIntent, RegisterActivity.RC_SIGN_IN)
//
//        if (!isUserSignedIn()){
//
//        } else {
//            Toast.makeText(this, " User already signed-in ", Toast.LENGTH_SHORT).show()
//        }
    }


    private fun signout() {
        if (isUserSignedIn()){
            getGoogleSinginClient().signOut().addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, " Signed out ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, " Error ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    var socialID = ""
    var deviceToken = ""
    var socialName = ""
    var socialemail = ""
    private fun handleSignData(data: Intent?) {
        // The Task returned from this call is always completed, no need to attach
        // a listener.
        com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnCompleteListener {
                "isSuccessful ${it.isSuccessful}".print()
                if (it.isSuccessful){
                    // user successfully logged-in
                    "account ${it.result?.account}".print()
                    "displayName ${it.result?.displayName}".print()
                    "Email ${it.result?.email}".print()
                    try {
                        socialID = it.result?.id.toString()
                        deviceToken = it.result?.idToken.toString()
                        socialName = it.result?.displayName.toString()
                        socialemail = it.result?.email.toString()
                        val deviceTokenFB = SessionData.getInstance(applicationContext).getFirebaseToken()?:""
                        apiCallingForSocialLogin(socialemail,socialName,socialID,deviceTokenFB)
                    }catch (ex:Exception){
                        Log.v(TAG_KOTLIN," Exception ${ex.message}")
                    }
                } else {
                    // authentication failed
                    "exception ${it.exception}".print()
                }
            }
    }
    private fun apiCallingForSocialLogin(email: String, socialName: String,socialID: String, deviceToken: String) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["email"] = email
            params["social_id"] = socialID
            params["device_token"] = deviceToken
            params["password"] = ""
            try {
                val responseOfLogin =
                    apiCallingRequest.apiCallingForLogin(
                        params
                    )
                val message = responseOfLogin.message
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if (TextUtils.isEmpty(message)) {
                        responseOfLogin.token?.let {
                            SessionData.getInstance(this@LoginActivity).saveToken(it)
                        }
                        SessionData.getInstance(this@LoginActivity)
                            .saveUserId(responseOfLogin.user?.id.toString())
                        lifecycleScope.launch(Dispatchers.IO) {
                            responseOfLogin.user?.let {
                                DataBaseHelper.getDatabaseDao(this@LoginActivity).saveUser(it)
                            }

                            withContext(Dispatchers.Main) {
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent);
                                overridePendingTransition(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left
                                )
                                finish()
                            }
                        }
                    } else {
                        PopupUtils.alertMessage(this@LoginActivity, message!!)
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
        callbackManager?.onActivityResult(
            requestCode,
            resultCode,
            data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            handleSignData(data)
        }
    }

    companion object{
        const val RC_SIGN_IN = 0
        const val TAG_KOTLIN = "@@@TAG_KOTLIN"
    }

    fun Any.print(){
        Log.v(TAG_KOTLIN, " $this")
    }

}