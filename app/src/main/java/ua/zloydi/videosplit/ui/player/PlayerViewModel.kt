package ua.zloydi.videosplit.ui.player

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import java.io.File

class PlayerViewModel(app: Application, savedStateHandle: SavedStateHandle) : AndroidViewModel(app) {
	val player = ExoPlayer.Builder(app).build()
	private var wasPlayerPlaying = true
	
	init {
		val path: String = savedStateHandle["path"] ?: error("No path")
		val uri = Uri.fromFile(File(path))
		player.setMediaItem(MediaItem.fromUri(uri))
		player.prepare()
	}
	
	fun onStart(){
		player.playWhenReady = wasPlayerPlaying
	}
	
	fun onStop(){
		wasPlayerPlaying = player.playWhenReady
		player.playWhenReady = false
	}
	
	override fun onCleared() {
		super.onCleared()
		player.release()
	}
}