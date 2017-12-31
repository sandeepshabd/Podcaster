package com.sandeepshabd.podcaster.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sandeepshabd.podcaster.R
import com.sandeepshabd.podcaster.models.RSSItem
import com.sandeepshabd.podcaster.views.IDisplayView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by sandeepshabd on 12/30/17.
 */

class PodcastViewAdapter(private var displayView: IDisplayView,
                         private var rssItems: ArrayList<RSSItem>) : RecyclerView.Adapter<PodcastViewHolder>(), AnkoLogger {

        override fun onViewDetachedFromWindow(holder: PodcastViewHolder?) {
        super.onViewDetachedFromWindow(holder)
        holder?.linearLayout?.background = context?.getDrawable(R.drawable.rect_border)
    }

    private val selectedItems = SparseBooleanArray()
    var context: Context? = null
    var prevPos = -1

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {
        holder.linearLayout.isSelected = false
        holder.podcastTitle.text = rssItems.get(position).title
        holder.podcastDuration.text = rssItems.get(position).duration
        holder.podcastPubDate.text = rssItems.get(position).pubDate
        Picasso.with(context)
                .load(rssItems.get(position).imageSource)
                .resize(100, 100)
                .centerCrop().into(holder.podcastPoster)
        if(prevPos == -1 && position ==0){
            holder.linearLayout.isSelected = true
            prevPos = 0
        }

        holder.cardView.setOnClickListener({ view ->
            if(position != prevPos) {
                displayView.onCardSelected(position)
                holder.linearLayout.isSelected = true
                prevPos = position
            }
        })


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