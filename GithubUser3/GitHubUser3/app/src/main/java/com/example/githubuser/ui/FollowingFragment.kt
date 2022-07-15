package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.adapter.FollowingAdapter
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.model.User
import com.example.githubuser.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private lateinit var rvFollowing: RecyclerView
    private lateinit var pBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollowing = view.findViewById(R.id.rv_following)
        setReclerView()

        pBar = view.findViewById(R.id.progressBar4)

        val data = requireActivity().intent.getStringExtra("DATA")

        val followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)

        followingViewModel.showFollowing(data.toString()).observe(requireActivity(), {
            showFollowingData(it)
        })

        followingViewModel.isLoading.observe(requireActivity(), {
            showLoading(it)
        })
    }

    private fun showLoading(boolean: Boolean) {
//        (activity as DetailActivity)!!.showLoading(boolean)
        if (boolean) {
            pBar.visibility = View.VISIBLE
        } else {
            pBar.visibility = View.GONE
        }
    }

    private fun showFollowingData(listFollowing: ArrayList<User>) {
        val adapter = FollowingAdapter(listFollowing)
        rvFollowing.adapter = adapter
        rvFollowing.setHasFixedSize(true)
    }

    private fun setReclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        rvFollowing.addItemDecoration(itemDecoration)
    }
}