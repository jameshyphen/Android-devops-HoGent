package com.hogent.pandora.fragments.users.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.hogent.pandora.R
import com.hogent.pandora.data.user.User
import com.hogent.pandora.data.user.UserViewModel
import java.time.LocalDate
import java.time.Month


class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        view.findViewById<MaterialButton>(R.id.btn_add).setOnClickListener {
            insertDataToDatabase(view)
        }

        return view
    }

    private fun insertDataToDatabase(view: View) {

        val userName = view.findViewById<EditText>(R.id.username).text.toString()
        val password = view.findViewById<EditText>(R.id.password).text.toString()
        val admin = view.findViewById<CheckBox>(R.id.admin).isChecked
        val birthdate = view.findViewById<DatePicker>(R.id.birthdate)

        val day: Int = birthdate.dayOfMonth
        val month: Int = birthdate.month + 1
        val year: Int = birthdate.year

        mUserViewModel.addUser(
            User(0, userName, password, LocalDate.of(year, Month.of(month), day), admin)
        )
        if (!inputCheck(userName, password)) {
            view.findViewById<TextView>(R.id.errors).text = "Username or password is empty"
        } else if (checkPassword(password)) {
            view.findViewById<TextView>(R.id.errors).text =
                "Password should be minimum 6 characters"
        } else {
            Toast.makeText(context, "Sucessfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }

    private fun checkPassword(password: String): Boolean {
        return password.length < 6
    }

    private fun inputCheck(userName: String, password: String): Boolean {
        return !(TextUtils.isEmpty(userName) && TextUtils.isEmpty(password))
    }
}