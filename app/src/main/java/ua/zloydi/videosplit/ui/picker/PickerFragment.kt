package ua.zloydi.videosplit.ui.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.VideoOnly
import androidx.navigation.fragment.findNavController
import ua.zloydi.videosplit.databinding.FragmentPickerBinding
import ua.zloydi.videosplit.ui.core.BindingFragment

class PickerFragment : BindingFragment<FragmentPickerBinding>() {
	override fun inflate(inflater: LayoutInflater) = FragmentPickerBinding.inflate(inflater)
	
	private val launcher =
		registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
			if (uri != null) {
				findNavController().navigate(
					PickerFragmentDirections.actionPickerFragmentToRenderFragment(uri.toString())
				)
			}
		}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.btnSelect.setOnClickListener {
			launcher.launch(PickVisualMediaRequest(VideoOnly))
		}
	}
}