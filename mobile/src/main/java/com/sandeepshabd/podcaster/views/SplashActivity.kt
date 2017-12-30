package com.sandeepshabd.podcaster.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sandeepshabd.podcaster.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class SplashActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info("starting splash activity")
        setContentView(R.layout.activity_splash)

    }

}
