package com.example.myandroidtest.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myandroidtest.R
import com.example.myandroidtest.databinding.FragmentCustomViewBinding


/**
 * @author andy
 * @date 2023/10/17
 */
class CustomViewFragment : Fragment() {

    private var _binding: FragmentCustomViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomViewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dragBtn.setOnClickListener {
            findNavController().navigate(R.id.action_CustomViewFragment_to_DetailViewFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}