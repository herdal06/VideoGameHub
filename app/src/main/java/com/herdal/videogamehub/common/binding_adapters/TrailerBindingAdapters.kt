package com.herdal.videogamehub.common.binding_adapters

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.databinding.BindingAdapter

@BindingAdapter("playVideoByUrl")
fun playVideoByUrl(videoView: VideoView, videoUrl: String?) {
    if (videoUrl == null) return

    val mediaController = MediaController(videoView.context)
    mediaController.setAnchorView(videoView)
    videoView.setMediaController(mediaController)

    val videoUri = Uri.parse(videoUrl)
    videoView.setVideoURI(videoUri)
    videoView.requestFocus()

    videoView.setOnPreparedListener { mediaPlayer ->
        mediaPlayer.setVolume(0f, 0f)
        //mediaPlayer.isLooping = true
        videoView.start()
    }
}