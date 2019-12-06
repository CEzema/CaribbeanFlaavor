package com.example.caribbeanflaavor


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_log_in.*



/**
 * A simple [Fragment] subclass.
 */
class LogIn : Fragment() {

    lateinit var viewModel: FoodViewModel

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(FoodViewModel::class.java)
        } ?: throw Exception("invalid activity")
        // Inflate the layout for this fragment
        //setSupportActionBar(findViewById(R.id.toolbar))
        (activity as AppCompatActivity).supportActionBar?.hide()

        /*google_button.setOnClickListener {
            // Configure sign-in to request the user's ID, email address, and basic
        }*/

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()


        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username_textview.visibility = View.INVISIBLE
        edit_username.visibility = View.INVISIBLE
        enter_button.visibility = View.INVISIBLE

        continue_as_guest.setOnClickListener {
            username_textview.visibility = View.VISIBLE
            edit_username.visibility = View.VISIBLE
            enter_button.visibility = View.VISIBLE
        }

        sign_up_button.setOnClickListener {
            findNavController().navigate(R.id.action_logIn_to_signUp)
        }

        enter_button.setOnClickListener {
            if (edit_username.text.isNotBlank()){
                viewModel.currentOrder.value?.username = edit_username.text.toString()
                findNavController().navigate(R.id.action_logIn_to_home2)
            }
            else{
                Toast.makeText(context," Enter name and click continue as guest",Toast.LENGTH_LONG).show()
            }
        }
    }

   /* private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }*/

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }

}
