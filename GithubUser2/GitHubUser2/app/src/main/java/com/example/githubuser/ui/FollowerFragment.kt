package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.adapter.FollowersAdapter
import com.example.githubuser.model.User
import com.example.githubuser.viewmodel.FollowerViewModel

class FollowerFragment : Fragment() {

    private lateinit var rvFollower: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollower = view.findViewById(R.id.rv_follower)

        setRecyclerView()

        val data = requireActivity().intent.getStringExtra("DATA")

        val followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)

        followerViewModel.showFollower(data.toString()).observe(requireActivity(), {
            showFollowerData(it)
        })

        followerViewModel.isLoading.observe(requireActivity(), {
            showLoading(it)
        })
    }
    private fun showLoading(boolean: Boolean) {
        (activity as DetailActivity)!!.showLoading(boolean)
    }

    private fun showFollowerData(listFollower: ArrayList<User>) {
        val adapter = FollowersAdapter(listFollower)
        rvFollower.adapter = adapter
        rvFollower.setHasFixedSize(true)
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvFollower.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        rvFollower.addItemDecoration(itemDecoration)
    }
}