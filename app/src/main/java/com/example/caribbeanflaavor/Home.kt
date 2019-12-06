package com.example.caribbeanflaavor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_log_in.*

/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.show()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(review_textView.text.isNotBlank()){

        }
        else{
            ratingBar.visibility = View.INVISIBLE
            review_textView.visibility = View.INVISIBLE
        }


        write_a_review_button.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_review)
        }
        menu_button.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_menu)
        }

    }

}
