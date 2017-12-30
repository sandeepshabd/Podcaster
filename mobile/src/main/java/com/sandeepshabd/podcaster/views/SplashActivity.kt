package com.sandeepshabd.podcaster.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sandeepshabd.podcaster.R
import com.sandeepshabd.podcaster.handler.ConstantHandler
import com.sandeepshabd.podcaster.models.RSSItem
import com.sandeepshabd.podcaster.presenters.SplashPresenter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity(), AnkoLogger,ISplashView {
    private val splashPresenter = SplashPresenter(this)


    override fun onDataFetched(rssData:  ArrayList<RSSItem>) {
        startActivity<DisplayDataActivity>(ConstantHandler.RSS_DATA to rssData)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info("starting splash activity")
        setContentView(R.layout.activity_splash)
        splashPresenter.getDataFromBo()

    }

}