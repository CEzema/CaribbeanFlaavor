package com.example.caribbeanflaavor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_review.*

/**
 * A simple [Fragment] subclass.
 */
class Review : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        save_button.setOnClickListener {
            if (review_edit.text.isBlank()){
                Toast.makeText(context," Review is empty", Toast.LENGTH_LONG).show()
            }
            else{
                findNavController().navigate(R.id.action_review_to_home2)

            }
        }
        cancel_button.setOnClickListener {
            findNavController().navigate(R.id.action_review_to_home2)
        }
    }



}
