package com.example.gihubuserapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gihubuserapp.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private var position: Int = 0
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)!!
        }
        if (position == 1){
            binding.textFollow.text = "Get Follower $username"
        } else {
            binding.textFollow.text = "Get Following $username"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_POSITION = "args_position"
        const val ARG_USERNAME = "args_username"
    }
}