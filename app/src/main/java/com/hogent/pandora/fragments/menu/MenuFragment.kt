package com.hogent.pandora.fragments.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.hogent.pandora.R
import com.hogent.pandora.data.user.UserAuthentication

class MenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        if (!UserAuthentication.isLoggedIn()) {
            findNavController().navigate(R.id.loginFragment)
        } else {
            // Inflate the layout for this fragment
            val userIsAdmin = UserAuthentication.user!!.admin

            if (userIsAdmin) {
                view.findViewById<Button>(R.id.btn_users).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.menu_title_admin_menu).visibility = View.VISIBLE
            }

            view.findViewById<Button>(R.id.btn_posts).setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_postListFragment)
            }

            view.findViewById<Button>(R.id.btn_edit_user).setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_userViewFragment)
            }

            view.findViewById<Button>(R.id.btn_users).setOnClickListener {
                if (userIsAdmin) {
                    findNavController().navigate(R.id.action_menuFragment_to_userListFragment)
                }
            }
        }

        return view
    }
}