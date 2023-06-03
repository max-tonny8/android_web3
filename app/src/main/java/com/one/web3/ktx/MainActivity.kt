package com.one.web3.ktx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.one.web3.ktx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}