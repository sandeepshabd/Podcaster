package com.sandeepshabd.podcaster.helpers

import android.support.annotation.WorkerThread
import com.sandeepshabd.podcaster.handler.ConstantHandler
import com.sandeepshabd.podcaster.handler.ConstantHandler.Companion.AUDIO_ATTR
import com.sandeepshabd.podcaster.handler.ConstantHandler.Companion.IMG_ATTR
import com.sandeepshabd.podcaster.models.RSSItem
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.xml.parsers.DocumentBuilderFactory


/**
 * THe class will make BO call and fetch rss and parse the data.
 * Created by sandeepshabd on 12/29/17.
 */

class RSSHelper : AnkoLogger {

    @WorkerThread
    fun fetchEconomicTimesRSSData(): ArrayList<RSSItem> {
        val url = "https://rss.acast.com/theeconomistallaudio" //url to read
        val urlLoaderURL = HttpUrl.parse(url)

        try {
            val rssClient = OkHttpClient()
            val rssRequest = Request.Builder().url(urlLoaderURL).build()
            info("making call to BO")
            val getRssReponse = rssClient.newCall(rssRequest).execute()
            info("received response from BO")
            if (getRssReponse.isSuccessful) {
                return parseRSS(getRssReponse.body())
            } else {
                info("failure during call.")
            }

        } catch (e: Exception) {
            error("Exception occurred." + e.message)
        }
        return ArrayList<RSSItem>()
    }

    fun parseRSS(rssBody: ResponseBody?): ArrayList<RSSItem> {
        info("parsing the data.")
        var economicTimesList = ArrayList<RSSItem>()
        try {
            var newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            var parse = newDocumentBuilder.parse(rssBody?.byteStream())
            val resultNodeList = parse.getElementsByTagName(ConstantHandler.ITEM)
            var resultNodeListSize = ConstantHandler.ITEMS_TO_DISPLAY
            if (resultNodeList.length > ConstantHandler.ITEMS_TO_DISPLAY) {
                resultNodeListSize = ConstantHandler.ITEMS_TO_DISPLAY
            }
            info("length of data from bo:" + resultNodeList.length)
            for (j in 0 until resultNodeListSize) {
                var newRsItem = RSSItem()
                var resultNode = resultNodeList.item(j)
                for (childCount in 0 until resultNode.childNodes.length) {
                    var currentnode = resultNode.childNodes.item(childCount)
                    when (currentnode.nodeName) {
                        ConstantHandler.TITLE -> newRsItem.title = currentnode.textContent
                        ConstantHandler.SUBTITLE -> newRsItem.subTitle = currentnode.textContent
                        ConstantHandler.DURATION -> newRsItem.duration = currentnode.textContent
                        ConstantHandler.PUB_DATE -> newRsItem.pubDate = currentnode.textContent
                        ConstantHandler.IMAGE -> newRsItem.imageSource = currentnode.attributes.getNamedItem(IMG_ATTR).textContent
                        ConstantHandler.AUDIO -> newRsItem.audioURL = currentnode.attributes.getNamedItem(AUDIO_ATTR).textContent
                        else -> {
                        }
                    }
                }
                economicTimesList.add(newRsItem)
            }
        } catch (e1: Exception) {
        }
        return economicTimesList
    }
}