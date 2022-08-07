package com.hogent.pandora.fragments.users.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hogent.pandora.R
import com.hogent.pandora.data.user.UserAuthentication
import com.hogent.pandora.data.user.UserViewModel


class UserListFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(!UserAuthentication.isLoggedIn()){
            findNavController().navigate(R.id.loginFragment)
        }
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        // RecyclerView
        val userListAdapter = UserListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.userRecyclerView)
        recyclerView.adapter = userListAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserViewModel.readAllUsers.observe(viewLifecycleOwner, Observer{ user ->
            userListAdapter.setData(user)
        })

        view.findViewById<FloatingActionButton>(R.id.userAddFloatingActionButton).setOnClickListener{
            findNavController().navigate(R.id.action_userListFragment_to_userAddFragment)
        }

        return view
    }
}