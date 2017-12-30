package com.sandeepshabd.podcaster.presenters

import com.sandeepshabd.podcaster.helpers.RSSHelper
import com.sandeepshabd.podcaster.views.ISplashView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread

/**
 * Created by sandeepshabd on 12/30/17.
 */
class SplashPresenter(val splashView : ISplashView): AnkoLogger {

    var rssHelper = RSSHelper()

    fun getDataFromBo() {
        doAsync() {
            var displayData = rssHelper.fetchEconomicTimesRSSData()
            // Do something in a secondary thread
            uiThread {
                info("data fetched from BO. call to display.")
                splashView.onDataFetched(displayData)
                // Back to the main thread
            }
        }
    }

}