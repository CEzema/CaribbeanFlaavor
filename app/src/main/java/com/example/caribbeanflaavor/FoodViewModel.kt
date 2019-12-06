package com.example.caribbeanflaavor

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.*
import kotlinx.coroutines.*

class FoodViewModel (application: Application): AndroidViewModel(application){

    var orders = MutableLiveData<ArrayList<FoodOrder>>()
    var menuList = MutableLiveData<ArrayList<FoodDetail>>()
    var favoriteList = MutableLiveData<ArrayList<FoodDetail>>()
    var current = MutableLiveData<FoodDetail>()
    var currentOrder = MutableLiveData<FoodOrder>()
    private var database = MutableLiveData<DatabaseReference>()


    init {
        orders.value = ArrayList()
        menuList.value = ArrayList()
        favoriteList.value = ArrayList()
        currentOrder.value = FoodOrder()
        database.value = FirebaseDatabase.getInstance().reference
        database.value?.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                orders.value!!.clear()
                p0.child("Orders").children.forEach {
                    it.getValue(FoodOrder::class.java)?.let {
                        orders.value?.add(it)
                    }
                }
                listener?.updateRecycler()
            }

        })
    }

   fun uploadData(order: FoodOrder){
        viewModelScope.launch {
            async { upload(order) }.await()
        }
    }
    suspend fun upload(order: FoodOrder) = withContext(Dispatchers.IO) {
        //println(current.value?.username)
        database.value?.child("Orders")?.child(currentOrder.value?.username.toString())?.setValue(order)

    }
   /* fun sendOrder(order: FoodOrder){

       val intent = Intent(Intent.ACTION_SENDTO)
       intent.data = Uri.parse("mailto:") // only email apps should handle this
       intent.putExtra(Intent.EXTRA_EMAIL, "Fowusu7340@Gmail.com")
       intent.putExtra(Intent.EXTRA_SUBJECT,"Order")
       intent.
       intent.putExtra(Intent.EXTRA_,order)
       startActivity(intent)
   }*/

    var listener: DataChangedListener? = null
    interface DataChangedListener{
        fun updateRecycler()
    }

    fun addToFavorite(foodDetail: FoodDetail){
        if(favoriteList.value?.indexOf(foodDetail) == -1){
            favoriteList.value?.add(foodDetail)
        }
    }
    fun removeFromOrders(index: Int){
        orders.value?.removeAt(index)
    }

    fun removeFromFavorite(index: Int){
        favoriteList.value?.removeAt(index)
    }
}