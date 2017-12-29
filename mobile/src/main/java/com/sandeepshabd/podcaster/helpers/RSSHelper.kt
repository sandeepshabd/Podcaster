package com.sandeepshabd.podcaster.helpers

import android.support.annotation.WorkerThread
import com.sandeepshabd.podcaster.handler.RssXMLDataHandler
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.xml.sax.InputSource
import javax.xml.parsers.SAXParserFactory

/**
 * Created by sandeepshabd on 12/29/17.
 */

class RSSHelper{



    @WorkerThread
    fun fetchEconomicTimesRSSData():String{
        val url = "https://rss.acast.com/theeconomistallaudio";
        val urlLoaderURL = HttpUrl.parse(url)

        try{
            val rssClient = OkHttpClient();
            val rssRequest = Request.Builder().url(urlLoaderURL).build()
            val getRssReponse = rssClient.newCall(rssRequest).execute()
            if(getRssReponse.isSuccessful){
                parseRSS(getRssReponse.body())
            }else{
                print("failure during call.")
            }

        }
        catch (e:Exception){
            print("Exception occurred.")
        }
        return "done"
    }

    private fun parseRSS(rssBody: ResponseBody?):String {
        try {
            val saxFactory = SAXParserFactory.newInstance()
            val xmlParser = saxFactory.newSAXParser()
            val xmlReader = xmlParser.xmlReader
            val xmlToRead = InputSource(rssBody?.byteStream())

            val handler = RssXMLDataHandler()

            xmlReader.contentHandler = handler
            xmlReader.parse(xmlToRead)

            return "test Data"
        }  catch (e: Exception) {
            print("error reading the file.")

        }


}