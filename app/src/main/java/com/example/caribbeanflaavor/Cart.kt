package com.example.caribbeanflaavor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cart_recycler.*
import kotlinx.android.synthetic.main.fragment_cart.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class Cart : Fragment(), FoodViewModel.DataChangedListener{

    override fun updateRecycler() {
        viewAdapter.notifyDataSetChanged()
    }

    lateinit var viewAdapter: RecyclerViewAdapter
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewModel: FoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(FoodViewModel::class.java)
        } ?: throw Exception("invalid activity")

        viewModel.orders.observe(this, Observer {
            viewManager = LinearLayoutManager(context)
            viewAdapter = RecyclerViewAdapter(viewModel.orders.value!!) {
                viewModel.currentOrder.value = it
            }

            recycler.apply {
                this.layoutManager = viewManager
                this.adapter = viewAdapter
            }

        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ItemTouchHelper(SwipeHandler()).attachToRecyclerView(recycler)

        add_more_button.setOnClickListener {
            findNavController().navigate(R.id.action_cart_to_menu)
        }
        place_order_button.setOnClickListener {
            if(viewModel.orders.value!!.isNotEmpty()) {
                for (food in viewModel.orders.value!!){
                    viewModel.uploadData(food)
                }
                findNavController().navigate(R.id.action_cart_to_confirmation)
            }
            else{
                Toast.makeText(context," Cart is empty", Toast.LENGTH_LONG).show()

            }
        }


    }
    inner class SwipeHandler: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.removeFromOrders(viewHolder.adapterPosition)
            viewAdapter.notifyDataSetChanged()
        }

    }
    class RecyclerViewAdapter(val foodData: ArrayList<FoodOrder>, val click: (FoodOrder) -> Unit) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context).inflate(R.layout.cart_recycler, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return foodData.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(foodData[position], click)
        }

        inner class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            fun bind(foodData: FoodOrder, click: (FoodOrder) -> Unit) {

                viewItem.run {
                    viewItem.findViewById<TextView>(R.id.foodname).text = foodData.name
                    viewItem.findViewById<TextView>(R.id.quantity).text = foodData.quantity.toString()
                    viewItem.findViewById<TextView>(R.id.price).text = foodData.price.toString()
                    viewItem.findViewById<ImageView>(R.id.foodimage).setImageResource(foodData.image)
                    viewItem.setOnClickListener { click(foodData) }
                    viewItem.findViewById<Button>(R.id.edit_food_button).setOnClickListener {
                        findNavController().navigate(R.id.action_cart_to_detail)
                    }
                }
            }
        }
    }


}
