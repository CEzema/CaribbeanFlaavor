package com.example.caribbeanflaavor


import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * A simple [Fragment] subclass.
 */
class SignUp : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sign_in_button.setOnClickListener {
            if(username_edit.text.isBlank() || edit_email.text.isBlank()){
                Toast.makeText(context," Email or username is blank", Toast.LENGTH_LONG).show()
            }
            else if(Patterns.EMAIL_ADDRESS.matcher(edit_email.text).matches()){
                Toast.makeText(context," Email is not valid", Toast.LENGTH_LONG).show()
            }
            else{
                findNavController().navigate(R.id.action_signUp_to_home2)

            }
            }
    }

}
