package com.sandeepshabd.podcaster.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sandeepshabd.podcaster.R
import com.sandeepshabd.podcaster.presenters.SplashPresenter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity(), AnkoLogger,ISplashView {

    override fun onDataFetched() {
        startActivity<DisplayDataActivity>()
    }

    val splashPresenter = SplashPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info("starting splash activity")
        setContentView(R.layout.activity_splash)

    }

}
