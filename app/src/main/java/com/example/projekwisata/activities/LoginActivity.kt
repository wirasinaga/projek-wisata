package com.example.projekwisata.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html


import android.text.Spanned
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.projekwisata.R
import com.example.projekwisata.utils.Constant.BASE_URL
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {
    var TxEmail: EditText? = null
    var TxPassword: EditText? = null
    var BtnLogin: Button? = null

    companion object {
        fun fromHtml(html: String?): Spanned {
            val result: Spanned
            result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
            return result
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        setContentView(R.layout.activity_login)
        TxEmail = findViewById<View>(R.id.txEmailLogin) as EditText
        TxPassword = findViewById<View>(R.id.txPassword) as EditText
        BtnLogin = findViewById<View>(R.id.btnLogin) as Button
        val tvCreateAccount =
            findViewById<View>(R.id.tvCreateAccount) as TextView
        tvCreateAccount.text = fromHtml(
            "I don't have account yet. " +
                    "</font><font color='#3b5998'>create one</font>"
        )
        tvCreateAccount.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }
        BtnLogin!!.setOnClickListener {
            val username: String = TxEmail!!.text.toString().trim { it <= ' ' }
            val password: String = TxPassword!!.text.toString().trim { it <= ' ' }
            val queue = Volley.newRequestQueue(this)
            val url = "$BASE_URL/user/login"

            val jsonObject = JSONObject()
            jsonObject.apply {
                put("email", username)
                put("password", password)
            }

            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, jsonObject,
                { json ->
                    val user = json.getJSONObject("data")

                    if (user.toString() != "null") {
                        Toast.makeText(this, "Berhasil masuk", Toast.LENGTH_SHORT).show()

                        startActivity(
                            Intent(this, MainActivity::class.java)
                        )
                    }
                }, {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
            )

            queue.add(jsonObjectRequest)
        }

//            if (res == true) {
//                Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT)
//                    .show()
//                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//            } else {
//                Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
//            }

    }
}