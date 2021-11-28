package com.toyourstore.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.preference.SessionData
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PopupUtils
import de.footprinttech.wms.db.DataBaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import java.util.HashMap


class LoginActivity : BaseActivity() {

    private lateinit var rlLoginSignUp: RelativeLayout
    private lateinit var txtCreate: TextView
    private lateinit var edtUser: EditText
    private lateinit var edtPassword: EditText
    private lateinit var pd: MyProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        try {
            init()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun init() {
        rlLoginSignUp = findViewById(R.id.rlLoginSignUp)
        txtCreate = findViewById(R.id.txtCreate)
        edtUser = findViewById(R.id.edtUser)
        edtPassword = findViewById(R.id.edtPassword)
        pd = MyProgressDialog(this, R.drawable.icons8_loader)
        rlLoginSignUp.setOnClickListener {
            if (isValidateData()) {
                apiCallingForLogin(
                    edtUser.text.toString().trim(),
                    edtPassword.text.toString().trim()
                )

            }
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


    private fun apiCallingForLogin(email: String, password: String) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["email"] = email
            params["password"] = password
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

}