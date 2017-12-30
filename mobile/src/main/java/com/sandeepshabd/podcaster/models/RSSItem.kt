package com.sandeepshabd.podcaster.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by sandeepshabd on 12/29/17.
 */
data class RSSItem(var title: String = "",
                   var subTitle: String = "",
                   var pubDate: String = "",
                   var duration: String = "",
                   var imageSource: String = "",
                   var audioURL: String = "") : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(subTitle)
        parcel.writeString(pubDate)
        parcel.writeString(duration)
        parcel.writeString(imageSource)
        parcel.writeString(audioURL)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RSSItem> {
        override fun createFromParcel(parcel: Parcel): RSSItem {
            return RSSItem(parcel)
        }

        override fun newArray(size: Int): Array<RSSItem?> {
            return arrayOfNulls(size)
        }
    }
}