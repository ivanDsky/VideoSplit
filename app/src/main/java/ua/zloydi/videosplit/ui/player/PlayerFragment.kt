package ua.zloydi.videosplit.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import ua.zloydi.videosplit.databinding.FragmentVideoplayerBinding
import ua.zloydi.videosplit.ui.core.BindingFragment

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