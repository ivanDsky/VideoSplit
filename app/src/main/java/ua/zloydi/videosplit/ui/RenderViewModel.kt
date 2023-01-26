package ua.zloydi.videosplit.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import ua.zloydi.videosplit.ffmpeg.VideoEditor

sealed interface RenderState {
	object Idle : RenderState
	
	@JvmInline
	value class Loading(val percent: Int) : RenderState
	data class Result(val output: String) : RenderState
}

class RenderViewModel(app: Application) : AndroidViewModel(app) {
	private val videoEditor = VideoEditor(app.applicationContext)
	private val _renderingState = MutableStateFlow<RenderState>(RenderState.Idle)
	val renderingState = _renderingState.asStateFlow()
	
	fun renderVideo(uri: Uri, output: String) {
		viewModelScope.launch {
			_renderingState.value = RenderState.Loading(0)
			videoEditor.swapVideo(uri, output)
				.takeWhile { it < 100 }
				.collect {
					_renderingState.value = RenderState.Loading(it)
				}
			_renderingState.value = RenderState.Result(output)
		}
	}
}