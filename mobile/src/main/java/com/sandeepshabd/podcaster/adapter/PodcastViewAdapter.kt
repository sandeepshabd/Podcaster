package com.sandeepshabd.podcaster.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sandeepshabd.podcaster.R
import com.sandeepshabd.podcaster.models.RSSItem
import com.squareup.picasso.Picasso

/**
 * Created by sandeepshabd on 12/30/17.
 */

class PodcastViewAdapter(private var context:Context,
                         private var rssItems: ArrayList<RSSItem>):RecyclerView.Adapter<PodcastViewHolder>(){

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {
        holder.podcastTitle.text = rssItems.get(position).title
        holder.podcastDuration.text = rssItems.get(position).duration
        holder.podcastPubDate.text = rssItems.get(position).pubDate
        Picasso.with(context)
                .load(rssItems.get(position).imageSource)
                .resize(50, 50)
                .centerCrop().into(holder.podcastPoster)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastViewHolder {
        context = parent.context
        val movieCardView = LayoutInflater.from(context)
                .inflate(R.layout.movie_card_view, parent, false)
        return PodcastViewHolder(movieCardView)
    }

    override fun getItemCount(): Int {
        return rssItems.size
    }


}