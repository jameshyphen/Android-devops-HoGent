package com.hogent.pandora.fragments.auth

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hogent.pandora.R
import com.hogent.pandora.data.user.User
import com.hogent.pandora.data.user.UserAuthentication
import com.hogent.pandora.data.user.UserDatabase
import com.hogent.pandora.data.user.UserViewModel
import com.hogent.pandora.utils.inputCheck
import com.hogent.pandora.utils.sha256
import kotlin.concurrent.thread

class LoginFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.loginFragment)
        }
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            val userName: String = view.findViewById<TextView>(R.id.username).text.toString()
            val password: String = view.findViewById<TextView>(R.id.password).text.toString()

            if (!inputCheck(userName, password)) {
                Toast.makeText(context, "Fill all fields!", Toast.LENGTH_SHORT).show()
            } else {
                val user: User = mUserViewModel.login(userName, password.sha256())
                if (user == null) {
                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                } else {
                    UserAuthentication.login(user)
                    findNavController().navigate(R.id.menuFragment)
                }
            }
        }

        return view
    }
}