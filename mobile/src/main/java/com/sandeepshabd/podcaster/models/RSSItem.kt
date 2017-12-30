package com.sandeepshabd.podcaster.models

/**
 * Created by sandeepshabd on 12/29/17.
 */
data class RSSItem(
    var title: String = "",
    var subTitle: String = "",
    var pubDate: String = "",
    var duration: String = "",
    var imageSource: String = "",
    var audioURL: String = "")