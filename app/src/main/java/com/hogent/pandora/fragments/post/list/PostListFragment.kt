package com.hogent.pandora.fragments.post.list

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.hogent.pandora.R
import com.hogent.pandora.data.post.Post
import com.hogent.pandora.data.user.UserAuthentication
import com.hogent.pandora.data.user.UserViewModel
import com.hogent.pandora.utils.md5
import com.squareup.picasso.Picasso
import java.time.LocalDate

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
            findNavController().navigate(R.id.action_postListFragment_to_menuFragment)
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
        postListAdapter.setOnclickFun(::onCommentClicked)
        val postListRecycler = view.findViewById<RecyclerView>(R.id.postListRecyclerView)
        postListRecycler.adapter = postListAdapter
        postListRecycler.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserViewModel.readUsersWithPosts.observe(viewLifecycleOwner) { usersWithPosts ->
            postListAdapter.setUserWithPosts(
                usersWithPosts
                    .flatMap { up -> up.posts }
                    .sortedBy { up -> up.dateAdded }
                    .asReversed(),
                usersWithPosts.map { up -> up.user },
                mUserViewModel
            )
        }

        mUserViewModel.readPostWithComments.observe(viewLifecycleOwner) { postsWithComments ->
            postListAdapter.setPostsWithComments(
                postsWithComments.flatMap { el -> el.comments }
            )
        }

        view.findViewById<MaterialButton>(R.id.btn_post_add).setOnClickListener {
            val postContent =
                view.findViewById<TextView>(R.id.txt_content_add).text.trim().toString()
            if (TextUtils.isEmpty(postContent)) {
                Toast.makeText(context, "Cannot create an empty post", Toast.LENGTH_LONG).show()
            } else {
                val post = Post(
                    postId = 0,
                    userCreatorId = UserAuthentication.user!!.userId,
                    content = postContent,
                    usersFavorite = emptyList(),
                    checkedByAdmin = false,
                    dateAdded = LocalDate.now()
                )

                mUserViewModel.addPost(post)
                view.findViewById<TextView>(R.id.txt_content_add).text = ""
            }
        }

        return view
    }

    fun onCommentClicked(postId: Int) {
        val args = Bundle()
        args.putInt("postId", postId)
        findNavController()
            .navigate(R.id.action_postListFragment_to_commentFragment, args)
    }
}