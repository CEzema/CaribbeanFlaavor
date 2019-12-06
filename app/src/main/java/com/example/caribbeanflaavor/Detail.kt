package com.example.caribbeanflaavor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_confirmation.*
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class Detail : Fragment() {

    lateinit var viewModel: FoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(FoodViewModel::class.java)
        } ?: throw Exception("invalid activity")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.current.observe(this, Observer {
            food_image.setImageResource(it.image)
            food_name.text = it.name
            food_description.setText(it.description)
            edit_instructions.setText(it.instruction)

            /* if(size_radio_group.checkedRadioButtonId == small_button.id){
                 it.size = "Small"
                 it.price = it.smallPrice
                 println("small value")
             }
             else if(size_radio_group.checkedRadioButtonId == large_button.id){
                 it.size = "Large"
                 it.price = it.largePrice
             }
             else if(size_radio_group.checkedRadioButtonId == -1){
                 it.size = ""
                // println("empty")
             }*/

            when (size_radio_group.checkedRadioButtonId) {
                small_button.id -> {
                    it.size = "Small"
                    it.price = it.smallPrice
                    println("small value")
                }
                large_button.id -> {
                    it.size = "Large"
                    it.price = it.largePrice
                    println("large value")
                }
                else -> {it.size = ""
                println("Emptyy")}


            }

        })


        add_to_cart_button.setOnClickListener {
            //add to database
            viewModel.current.value!!.instruction = edit_instructions.text.toString()
            viewModel.currentOrder.value!!.instruction = edit_instructions.text.toString()


            viewModel.current.value!!.quantity = edit_quantity.text.toString().toInt()
            viewModel.currentOrder.value!!.quantity = edit_quantity.text.toString().toInt()


            println(viewModel.current.value!!.size + "here")
           /* if (viewModel.current.value?.size.isNullOrBlank() || viewModel.current.value?.quantity == 0) {
                Toast.makeText(context, "Size or quantity is empty", Toast.LENGTH_LONG).show()
            } else {*/
                val food = FoodOrder()
                food.username = viewModel.currentOrder.value?.username.toString()
                food.name = viewModel.current.value?.name.toString()
                food.size = viewModel.current.value?.size.toString()
                food.quantity = viewModel.current.value?.quantity!!.toInt()
                food.price = viewModel.current.value!!.price * food.quantity.toDouble()
                food.instruction = viewModel.current.value?.instruction.toString()
                food.image = viewModel.current.value?.image!!.toInt()
                viewModel.orders.value?.add(food)
                viewModel.currentOrder.value = food
                findNavController().navigate(R.id.action_detail_to_cart)
          //  }
        }

        like_button.setOnClickListener {
            Toast.makeText(context,"Added to favorite list", Toast.LENGTH_LONG).show()
            viewModel.addToFavorite(viewModel.current.value!!)

        }

    }


}
