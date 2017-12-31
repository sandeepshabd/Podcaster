package com.sandeepshabd.podcaster.services


import android.content.Intent

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat

import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.MediaSessionCompat
import com.sandeepshabd.podcaster.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info


class PodcastMediaService : MediaBrowserServiceCompat(), AnkoLogger {


    private val TAG = "PodcastMediaService"

    private var mMediaSession: MediaSessionCompat? = null
    private var mStateBuilder: PlaybackStateCompat.Builder? = null

    override fun onCreate() {
        super.onCreate()

        // Create a MediaSessionCompat
        mMediaSession = MediaSessionCompat(applicationContext, TAG)

        mStateBuilder = PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)


        // Enable callbacks from MediaButtons and TransportControls
        mMediaSession.let {
            var localStateBuilder = mStateBuilder
            it?.setFlags(
                    MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
            it?.setPlaybackState(localStateBuilder?.build())

            // MySessionCallback() has methods that handle callbacks from a media controller
            it?.setCallback(PodcastMediaCallBack())

            // Set the session's token so that client activities can communicate with it.
            sessionToken = it?.sessionToken
        }

    }

    private class PodcastMediaCallBack: MediaSessionCompat.Callback(), AnkoLogger  {
        override fun onMediaButtonEvent(mediaButtonIntent: Intent?): Boolean {
            return super.onMediaButtonEvent(mediaButtonIntent)
        }

        override fun onSeekTo(pos: Long) {
            super.onSeekTo(pos)
        }

        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun onPause() {
            super.onPause()
            info("onPause of media session")
        }

        override fun onPrepare() {
            super.onPrepare()
            debug("onPrepare of media session")
        }

        override fun onPlay() {
            super.onPlay()
            info("onPlay of media session")
        }

        override fun onStop() {
            super.onStop()
            info("onStop of media session")
        }

        override fun onSkipToNext() {
            super.onSkipToNext()
        }

    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        result.sendResult(null)
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        return  MediaBrowserServiceCompat.BrowserRoot(getString(R.string.app_name), // Name visible in Android Auto
                null)
    }




}
