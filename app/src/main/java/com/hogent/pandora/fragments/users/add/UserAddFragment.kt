package com.hogent.pandora.fragments.users.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.hogent.pandora.R
import com.hogent.pandora.data.post.Post
import com.hogent.pandora.data.user.User
import com.hogent.pandora.data.user.UserAuthentication
import com.hogent.pandora.data.user.UserViewModel
import com.hogent.pandora.utils.checkPassword
import com.hogent.pandora.utils.inputCheck
import com.hogent.pandora.utils.sha256
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.time.LocalDate
import java.time.Month


class UserAddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    private var usernameIsTaken: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(!UserAuthentication.isLoggedIn()){
            findNavController().navigate(R.id.loginFragment)
        }
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_add, container, false)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        view.findViewById<AppCompatEditText>(R.id.username).addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val username = view.findViewById<EditText>(R.id.username).text.toString()

                GlobalScope.async {
                    usernameIsTaken = mUserViewModel.isTaken(username)
                    if(usernameIsTaken){
                        Toast.makeText(context, "Username already taken!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        view.findViewById<MaterialButton>(R.id.btn_add).setOnClickListener {
            insertDataToDatabase(view)
        }

        return view
    }

    private fun  insertDataToDatabase(view: View) {

        val username = view.findViewById<EditText>(R.id.username).text.toString()
        val email = view.findViewById<EditText>(R.id.email).text.toString()
        val password = view.findViewById<EditText>(R.id.password).text.toString()
        val admin = view.findViewById<CheckBox>(R.id.admin).isChecked
        val birthdate = view.findViewById<DatePicker>(R.id.birthdate)

        val day: Int = birthdate.dayOfMonth
        val month: Int = birthdate.month + 1
        val year: Int = birthdate.year

        if (!inputCheck(username, password)) {
            Toast.makeText(context, "Username or password is empty!", Toast.LENGTH_LONG).show()
            view.findViewById<TextView>(R.id.errors).text = "Username or password is empty"
        } else if (checkPassword(password)) {
            Toast.makeText(context, "Password should be minimum 6 characters!", Toast.LENGTH_LONG)
                .show()
        } else if (usernameIsTaken) {
            Toast.makeText(context, "Username already taken!", Toast.LENGTH_LONG).show()
        } else {
            mUserViewModel.addUser(
                User(
                    0,
                    username,
                    email,
                    password.sha256(),
                    LocalDate.of(year, Month.of(month), day),
                    admin,
                )
            )
            Toast.makeText(context, "Sucessfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_userAddFragment_to_userListFragment)
        }
    }
}