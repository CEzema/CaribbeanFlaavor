package com.example.caribbeanflaavor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorite.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class Favorite : Fragment() {

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

        val holder = inflater.inflate(R.layout.fragment_favorite, container, false)

        viewModel.favoriteList.observe(this, Observer {
            viewManager = LinearLayoutManager(context)
            viewAdapter = RecyclerViewAdapter(viewModel.favoriteList.value!!) {
                viewModel.current.value = it
                findNavController().navigate(R.id.action_favorite_to_detail)
            }

            favorite_recycler.apply {
                this.layoutManager = viewManager
                this.adapter = viewAdapter
            }
        })

        return holder
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ItemTouchHelper(SwipeHandler()).attachToRecyclerView(favorite_recycler)

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
            viewModel.removeFromFavorite(viewHolder.adapterPosition)
            viewAdapter.notifyDataSetChanged()
        }

    }

    class RecyclerViewAdapter(val foodData: ArrayList<FoodDetail>, val click: (FoodDetail) -> Unit) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerViewHolder {
            val viewItem =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_list, parent, false)
            return RecyclerViewHolder(viewItem)
        }

        override fun getItemCount(): Int {
            return foodData.size
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            holder.bind(foodData[position])
        }

        inner class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            fun bind(foodData: FoodDetail) {

                viewItem.run {
                    viewItem.findViewById<TextView>(R.id.food_description).text = foodData.description
                    viewItem.findViewById<TextView>(R.id.food_name).text = foodData.name
                    viewItem.findViewById<TextView>(R.id.food_size).text = foodData.size
                    viewItem.findViewById<ImageView>(R.id.food_image).setImageResource(foodData.image)
                    viewItem.setOnClickListener { click(foodData) }

                }
            }
        }
    }

}

