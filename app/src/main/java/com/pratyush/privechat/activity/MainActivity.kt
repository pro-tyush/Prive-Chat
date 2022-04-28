package com.pratyush.privechat.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.pratyush.privechat.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        val intent = when {
            sharedPreferences.getBoolean("loggedin",false) -> {
                Intent(this, UsersActivity::class.java)
            }
            else -> {
                Intent(this,SignUpActivity::class.java)
            }
        }
        startActivity(intent)
        finish()
    }
}