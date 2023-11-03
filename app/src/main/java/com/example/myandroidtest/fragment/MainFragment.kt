package com.example.myandroidtest.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myandroidtest.R
import com.example.myandroidtest.activity.TestActivity
import com.example.myandroidtest.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFast.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FastFragment)
        }
        binding.buttonViewpage2.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_designFragment)
            startActivity(Intent(requireActivity(), TestActivity::class.java))
        }
        binding.buttonLargeImageView.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_LargeViewFragment)
        }
        binding.buttonCustomView.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_CustomViewFragment)
        }
        binding.buttonDownload.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_downloadFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}