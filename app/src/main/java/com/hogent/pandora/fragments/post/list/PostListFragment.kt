package com.hogent.pandora.fragments.post.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.hogent.pandora.R
import com.hogent.pandora.data.user.UserAuthentication

class PostListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_list, container, false)

        if(!UserAuthentication.isLoggedIn()){
            Toast.makeText(context, "You've been logged out.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment, )
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.postListFragment)
        }

        return view
    }
}