package com.example.myandroidtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myandroidtest.adapter.ScreenSlidePagerAdapter2
import com.example.myandroidtest.databinding.FragmentTestBinding

class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    companion object {
        fun newInstance(type: Int): TestFragment {
            return TestFragment().apply {
                arguments = Bundle().apply {
                    putInt("type", type)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("type").let {
            if (it == 0) {
                binding.tv.visibility = View.GONE
                val adapter = ScreenSlidePagerAdapter2(requireActivity())
                binding.vPager.adapter = adapter
            } else {
                binding.tv.text = it.toString()
            }
        }


    }
}