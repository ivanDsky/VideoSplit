package ua.zloydi.videosplit.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ua.zloydi.videosplit.R
import ua.zloydi.videosplit.databinding.FragmentRenderBinding

class RenderFragment : BindingFragment<FragmentRenderBinding>() {
	override fun inflate(inflater: LayoutInflater) = FragmentRenderBinding.inflate(inflater)
	
	private val viewModel: RenderViewModel by viewModels()
	private val args: RenderFragmentArgs by navArgs()
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val inputUri = Uri.parse(args.inputUri)
		
		viewModel.renderVideo(inputUri, requireContext().cacheDir.absolutePath + "/output.mp4")
		
		lifecycleScope.launchWhenStarted {
			viewModel.renderingState.collect{
				if (it is RenderState.Result){
					findNavController().navigate(
						RenderFragmentDirections.actionRenderFragmentToPlayerFragment(it.output)
					)
				}
				if (it is RenderState.Loading){
					binding.tvProgress.text = getString(R.string.progress_percent, it.percent)
					binding.progress.progress = it.percent
				}
			}
		}
	}
}