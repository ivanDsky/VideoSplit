package ua.zloydi.videosplit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import ua.zloydi.videosplit.databinding.FragmentVideoplayerBinding

class PlayerFragment : BindingFragment<FragmentVideoplayerBinding>() {
	override fun inflate(inflater: LayoutInflater) =
		FragmentVideoplayerBinding.inflate(layoutInflater)
	
	private val viewModel: PlayerViewModel by viewModels()
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.player.setShowNextButton(false)
		binding.player.setShowPreviousButton(false)
		binding.player.player = viewModel.player
		viewModel.player.play()
	}
}