package ua.zloydi.videosplit.ffmpeg

import android.content.Context
import android.net.Uri
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFmpegKitConfig
import com.arthenica.ffmpegkit.FFprobeKit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Session(
	val sessionId: Long,
	val progress: StateFlow<Int>
)

class VideoEditor(private val context: Context) {
	fun getSafPath(uri: Uri) = FFmpegKitConfig.getSafParameterForRead(context, uri)
	
	fun getDuration(uri: Uri): Float{
		val path = getSafPath(uri)
		val mediaInfo = FFprobeKit.getMediaInformation(path).mediaInformation
		return mediaInfo.duration.toFloat()
	}
	
	fun swapVideo(uri: Uri, output: String): Session {
		val duration = getDuration(uri)
		val durationMillis = (duration * 1000).toInt()
		val half = duration / 2
		val path = getSafPath(uri)
		val progress = MutableStateFlow(0)
		val session = FFmpegKit.executeAsync("-i $path  -filter_complex \"" +
								   " [0:v]trim=0:$half,setpts=PTS-STARTPTS[v0];" +
								   " [0:a]atrim=0:$half,asetpts=PTS-STARTPTS[a0];" +
								   " [0:v]trim=$half:$duration,setpts=PTS-STARTPTS[v1]; " +
								   " [0:a]atrim=$half:$duration,asetpts=PTS-STARTPTS[a1]; " +
								   " [v1][a1][v0][a0]concat=n=2:v=1:a=1[outv][outa] " +
								   " \" -c:v libx264 -map \"[outv]\" -map \"[outa]\" -y $output",
							   {progress.value = 100},
							   {},
							   {progress.value = it.time * 100 / durationMillis})
		return Session(session.sessionId, progress)
	}
	
	fun cancel(sessionId: Long){
		FFmpegKit.cancel(sessionId)
	}
	
	
	
	
}