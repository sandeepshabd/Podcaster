package com.sandeepshabd.podcaster.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sandeepshabd.podcaster.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by sandeepshabd on 12/30/17.
 */

class PodcastViewHolder(view: View) : ViewHolder(view), View.OnClickListener, AnkoLogger {


    internal var cardView = view.findViewById<View>(R.id.podcastCardViewID) as CardView
    internal var podcastPoster = view.findViewById<View>(R.id.podcastPoster) as ImageView
    internal var podcastTitle = view.findViewById<View>(R.id.podcastTitle) as TextView
    internal var podcastDuration = view.findViewById<View>(R.id.podcastDuration) as TextView
    internal var podcastPubDate = view.findViewById<View>(R.id.pubDate) as TextView

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        info(" The view was clicked.")
    }

}