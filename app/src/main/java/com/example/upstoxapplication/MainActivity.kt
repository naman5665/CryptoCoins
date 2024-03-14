package com.example.upstoxapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.upstoxapplication.ui.CryptocoinsFragment
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
//        setSupportActionBar(toolbar)
        if(findViewById<View>(R.id.main_activity) != null){
            supportFragmentManager.beginTransaction().replace(R.id.main_activity , CryptocoinsFragment()).commit()
        }
    }
}