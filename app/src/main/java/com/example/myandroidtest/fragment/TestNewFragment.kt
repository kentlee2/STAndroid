package com.example.myandroidtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myandroidtest.databinding.FragmentTestBinding
import com.example.myandroidtest.databinding.FragmentTestNewBinding

class TestNewFragment : Fragment() {

    private var _binding: FragmentTestNewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestNewBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    companion object {
        fun newInstance(type: Int): TestNewFragment {
            return TestNewFragment().apply {
                arguments = Bundle().apply {
                    putInt("type", type)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv.text = "子Fragment"+arguments?.getInt("type",0).toString()
    }
}