package com.xeross.srapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xeross.srapp.base.BaseFirebaseViewModel
import com.xeross.srapp.data.models.Category
import com.xeross.srapp.utils.Constants.DATABASE_COLLECTION_CATEGORIES
import com.xeross.srapp.utils.Constants.DATABASE_COLLECTION_USERS_CATEGORIES

class MainViewModel : BaseFirebaseViewModel() {
    
    companion object {
        private const val NAME_SPEED_RUNNER = "XeroSs"
        private const val NAME_GAME = "Celeste"
        private const val CATEGORY_ID = "7kjpl1gk"
    }
    
    // private var api: SrcApi? = null
    //   private var networkCallHelper = NetworkRequestHelper()
    
    fun buildViewModel() {
        build()
        //     api = RetrofitHelper.getClient().create(SrcApi::class.java)
    }
    
    fun getCategories(): LiveData<ArrayList<Category>> {
        val categories = ArrayList<Category>()
        val mutableLiveData = MutableLiveData<ArrayList<Category>>()
        
        val collection = getCollection(DATABASE_COLLECTION_USERS_CATEGORIES)
        val uid = getUserId()
        
        if (uid == null) {
            mutableLiveData.postValue(categories)
            return mutableLiveData
        }
        
        collection.document(uid).collection(DATABASE_COLLECTION_CATEGORIES).get().addOnSuccessListener {
            
            it.toObjects(Category::class.java).let { data ->
                categories.addAll(data)
            }
            
            mutableLiveData.postValue(categories)
        }.addOnFailureListener {
            mutableLiveData.postValue(categories)
        }
        
        return mutableLiveData
    }
}