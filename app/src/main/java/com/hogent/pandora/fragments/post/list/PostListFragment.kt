package com.hogent.pandora.fragments.post.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hogent.pandora.R
import com.hogent.pandora.data.user.UserAuthentication
import com.hogent.pandora.data.user.UserViewModel
import com.hogent.pandora.utils.md5
import com.squareup.picasso.Picasso

class PostListFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_list, container, false)

        if (!UserAuthentication.isLoggedIn()) {
            Toast.makeText(context, "You've been logged out.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
            return view
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.postListFragment)
        }
        val user = UserAuthentication.user!!
        val hash = user.email.trim().lowercase().md5()
        val url = "https://www.gravatar.com/avatar/$hash?s=50"
        val imageView = view.findViewById<ImageView>(R.id.user_avatar_logged)

        Picasso.get()
            .load(url)
            .resize(25, 25)
            .centerCrop()
            .into(imageView)

        view.findViewById<TextView>(R.id.txt_username_add).text = user.userName


        val postListAdapter = PostListAdapter()
        val postListRecycler = view.findViewById<RecyclerView>(R.id.postListRecyclerView)
        postListRecycler.adapter = postListAdapter
        postListRecycler.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserViewModel.readUsersWithPosts.observe(viewLifecycleOwner, Observer { usersWithPosts ->
            postListAdapter.setData(
                usersWithPosts
                    .flatMap { up -> up.posts }
                    .sortedBy { up -> up.dateAdded }
                    .asReversed(),
                usersWithPosts.map { up -> up.user }
            )
        })

        return view
    }
}