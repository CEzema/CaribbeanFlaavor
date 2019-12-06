package com.example.caribbeanflaavor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_menu.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class Menu : Fragment() {

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

        val holder = inflater.inflate(R.layout.fragment_menu, container, false)

        // Inflate the layout for this fragment
        viewModel.menuList.observe(this, Observer {
            viewManager = LinearLayoutManager(context)
            viewAdapter = RecyclerViewAdapter(viewModel.menuList.value!!){
                viewModel.current.value = it
                findNavController().navigate(R.id.action_menu_to_detail)
            }

            //name size description image
            viewModel.menuList.value?.add(FoodDetail("Jerk Pork", "Small $11/Large $14",14.00,11.00,"This is succulent, roasted pork spiced and bursting with Jamaican jerk seasoning served with rice.",R.drawable.jerkpork, 1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Cow Foot", "Small $11/Large $14", 14.00,11.00," A delicious meat stew slowly simmered with spices served with broad beans and rice.",R.drawable.cowfoot,1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Oxtail", "Small $11/Large $14",14.00,11.00,"A deep and rich stew slowly simmered to bring out the delicious flavour and texture served with rice.",R.drawable.oxtail, 1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Curried Goat", "Small $11/Large $14",14.00,11.00,"Goat meat and potatoes infused with the flavours of Jamaican curry powder creating a delicious stew served with rice and vegetables.",R.drawable.curriedgoatpng, 1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Stewed Chicken", "Small $10/Large $12",12.00,10.00,"Chicken intenselt marinated in aromatic spices and then braised in savoury browning sauce and  liquids, served with rice.",R.drawable.chickenstew,1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Jerked Chicken", "Small $10/Large $12", 12.00,10.00,"Wet marinated meat with a Jamaican jerk spice.",0, 1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Fried Chicken", "Small $10/Large $12", 12.00,10.00,"Marinated and seasoned chicken deep fried and served with rice and vegetables.",R.drawable.friedchicken, 1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Chicken & Fries", "Small $8/Large $11", 11.00,8.00,"Seasoned chicken breasts accompanied by fries and fried dumpling.",R.drawable.chickenandfries, 1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Curried Chicken", "Small $10/Large $12",12.00,10.00,"Chicken and potatoes infused with the flavours of Jamaican curry powder creating a delicious stew served with rice and vegetables.",R.drawable.curriedchicken, 1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Escovitched Fish", "Small $18/Large $25", 25.00,18.00," Fried fish topped with a spicy sauce and pickled vegetable medley. Served with rice.",R.drawable.escovitchfish, 1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Brown Stewed Fish", "Small $18/Large $25", 25.00,18.00,"A simple stew with marinated snapper and tilapia generously infused with spices and herbs and served with rice",R.drawable.brownstewfish, 1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Steamed Fish", "Small $18/Large $25",25.00,18.00,"Firm snapper fish cooked in a spicy broth with vegetables served with rice.",R.drawable.steamedfish, 1, 0.0, ""))
            viewModel.menuList.value?.add(FoodDetail("Callaloo", "Small $10/Large $12",12.00,10.00,"A local staple, green leafy vegetable cooked in various spices and seasonings. Delicious as a side or as a meal accompanied by green bananas and plantain.",R.drawable.callaloo, 1, 0.0, ""))

            menu_recycler.apply {
                this.layoutManager = viewManager
                this.adapter = viewAdapter
            }

        })



        return holder
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
            holder.bind(foodData[position],click)
        }

        inner class RecyclerViewHolder(val viewItem: View) : RecyclerView.ViewHolder(viewItem) {
            fun bind(foodData: FoodDetail, click: (FoodDetail) -> Unit) {

                viewItem.run {
                    viewItem.findViewById<TextView>(R.id.food_description).text =
                        foodData.description
                    viewItem.findViewById<TextView>(R.id.food_name).text = foodData.name
                    viewItem.findViewById<TextView>(R.id.food_size).text = foodData.size
                    viewItem.findViewById<ImageView>(R.id.food_image).setImageResource(foodData.image)
                    viewItem.findViewById<TextView>(R.id.food_description).text = foodData.description
                    viewItem.setOnClickListener { click(foodData) }
                }
            }
        }
    }


}
