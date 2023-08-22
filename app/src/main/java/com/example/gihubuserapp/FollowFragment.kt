package com.example.gihubuserapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gihubuserapp.databinding.FragmentFollowBinding
import com.example.gihubuserapp.ui.ListUserAdapter

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private var position: Int = 0
    private lateinit var username: String
    private val followFragmentViewModel: FollowFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)!!
        }

        if (position == 1){
            followFragmentViewModel.getFollowing(username)
        } else {
            followFragmentViewModel.getFollowers(username)
        }

        followFragmentViewModel.githubUsers.observe(viewLifecycleOwner) { githubUsers ->
            val listUserAdapter = ListUserAdapter(githubUsers!!) {}
            binding.rvFollow.adapter = listUserAdapter
        }

        followFragmentViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBarFollow.visibility = View.VISIBLE
                binding.rvFollow.visibility = View.INVISIBLE
            } else {
                binding.progressBarFollow.visibility = View.GONE
                binding.rvFollow.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_POSITION = "args_position"
        const val ARG_USERNAME = "args_username"
    }
}