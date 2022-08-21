package com.hogent.pandora.fragments.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hogent.pandora.R
import com.hogent.pandora.data.user.UserAuthentication
import com.hogent.pandora.data.user.UserViewModel

class CommentFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_comment, container, false)

        if (!UserAuthentication.isLoggedIn()) {
            Toast.makeText(context, "You've been logged out.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
            return view
        }

        val args = arguments
        val postId = args!!.getInt("postId", 0)
        if (postId == 0) {
            findNavController().navigate(R.id.postListFragment)
        }
        val commentAdapter = CommentAdapter()
        val commentRecycler = view.findViewById<RecyclerView>(R.id.commentRecyclerView)
        commentRecycler.adapter = commentAdapter
        commentRecycler.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserViewModel.readUsersWithPosts.observe(viewLifecycleOwner) { usersWithPosts ->
            commentAdapter.setUsers(
                usersWithPosts.map { up -> up.user },
            )
        }
        mUserViewModel.readPostWithCommentsByPostId(postId).observe(
            viewLifecycleOwner
        ) { postWithComments ->
            commentAdapter.setData(
                postWithComments[0].post,
                postWithComments[0].comments,
                mUserViewModel
            )
        }

        return view
    }
}