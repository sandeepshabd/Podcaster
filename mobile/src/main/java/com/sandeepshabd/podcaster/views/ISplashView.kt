package com.sandeepshabd.podcaster.views

import com.sandeepshabd.podcaster.models.RSSItem

/**
 * Created by sandeepshabd on 12/30/17.
 */
interface ISplashView {
    fun onDataFetched(rssItelList: ArrayList<RSSItem>)
}