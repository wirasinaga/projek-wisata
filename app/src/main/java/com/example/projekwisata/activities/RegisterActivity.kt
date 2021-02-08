package com.example.projekwisata.activities

import android.content.ContentValues
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

class RegisterActivity : AppCompatActivity() {

    var TxUsername: EditText? = null
    var TxPassword: EditText? = null
    var TxConPassword: EditText? = null
    var BtnRegister: Button? = null
    var TxEmail: EditText? = null

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
        setContentView(R.layout.activity_register)
        TxUsername = findViewById<View>(R.id.txUsernameReg) as EditText
        TxPassword = findViewById<View>(R.id.txPasswordReg) as EditText
        TxConPassword = findViewById<View>(R.id.txConPassword) as EditText
        BtnRegister = findViewById<View>(R.id.btnRegister) as Button
        TxEmail = findViewById<EditText>(R.id.txEmail)
        val tvRegister = findViewById<View>(R.id.tvRegister) as TextView

        tvRegister.text = fromHtml(
            "Back to " +
                    "</font><font color='#3b5998'>Login</font>"
        )
        tvRegister.setOnClickListener {
            startActivity(
                Intent(
                    this@RegisterActivity,
                    LoginActivity::class.java
                )
            )
        }

        BtnRegister!!.setOnClickListener {
            val username = TxUsername!!.text.toString().trim { it <= ' ' }
            val password = TxPassword!!.text.toString().trim { it <= ' ' }
            val conPassword = TxConPassword!!.text.toString().trim { it <= ' ' }
            val email = TxEmail!!.text.toString().trim { it <= ' ' }

            val values = ContentValues()

            if (password != conPassword) {
                Toast.makeText(this@RegisterActivity, "Password not match", Toast.LENGTH_SHORT)
                    .show()
            } else if (password == "" || username == "") {
                Toast.makeText(
                    this@RegisterActivity,
                    "Username or Password cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val queue = Volley.newRequestQueue(this)
                val url = "$BASE_URL/user/register"

                val jsonObject = JSONObject()
                jsonObject.apply {
                    put("name", username)
                    put("email", email)
                    put("password", password)
                }

                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST, url, jsonObject,
                    {
                        Toast.makeText(this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show()

                        finish()
                    }, {
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                )

                queue.add(jsonObjectRequest)
            }
        }
    }
}