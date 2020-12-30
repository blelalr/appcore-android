package com.android.kotlin_core

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import com.android.kotlin_core.databinding.FragmentHomeBinding
import com.android.kotlin_core.ui.viewBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        binding.btnSyntaxEditText.setOnClickListener {
            controller.navigate(R.id.action_homeFragment_to_syntaxEditTextFragment)
        }

        binding.btnNetwork.setOnClickListener {
            controller.navigate(R.id.action_homeFragment_to_networkFragment)
        }
    }
}