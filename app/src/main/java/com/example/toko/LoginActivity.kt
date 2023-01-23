package com.example.toko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.toko.api.BaseRetrofit
import com.example.toko.response.login.LoginResponse
import com.example.toko.utils.SessionManager
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val api by lazy { BaseRetrofit().endpoint }

    companion object {
        lateinit var sessionManager: SessionManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        val loginStatus = sessionManager.getBoolean("LOGIN_STATUS")
        if(loginStatus){
            val moveIntent =  Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(moveIntent)
            finish()
        }

        val btnLogin = findViewById<Button>(R.id.btnlogin)
        val txtEmail = findViewById<TextInputEditText>(R.id.txtEmail)
        val txtPassword = findViewById<TextInputEditText>(R.id.txtPassword)

        btnLogin.setOnClickListener{
       Toast.makeText(this, "Proses Login", Toast.LENGTH_SHORT).show()

            api.login(txtEmail.text.toString(),txtPassword.text.toString()).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val correct = response.body()!!.success
                    if(correct){
                        //untuk menyimpan session dengan mendapatkan data token dari response body
                        val  token = response.body()!!.data.token
                        sessionManager.saveString("TOKEN","Bearer " + token)
                        sessionManager.saveBoolean("LOGIN_STATUS",true)
                        sessionManager.saveString("ADMIN_ID",response.body()!!.data.admin.id.toString())
                        Toast.makeText(applicationContext,"Password Benar",Toast.LENGTH_LONG).show()
                        val moveIntent =  Intent(this@LoginActivity,MainActivity::class.java)
                        startActivity(moveIntent)
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"Password salah",Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("LoginError",t.toString())
                }

        })
        }

    }
}