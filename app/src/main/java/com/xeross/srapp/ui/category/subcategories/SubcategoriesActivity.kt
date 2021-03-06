package com.xeross.srapp.ui.category.subcategories

import android.content.Intent
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.data.models.SubCategory
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.ui.adapters.SubcategoriesAdapter
import com.xeross.srapp.ui.category.subcategory.SubCategoryActivity
import com.xeross.srapp.ui.categoryform.subcategory.SubcategoryFormActivity
import com.xeross.srapp.utils.Constants
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_ID
import com.xeross.srapp.utils.Constants.EXTRA_CATEGORY_NAME
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_subcategories.*
import kotlinx.android.synthetic.main.activity_subcategory.*
import kotlinx.android.synthetic.main.cell_subcategory.*
import kotlinx.android.synthetic.main.fragment_bottom_navigation_menu.*

class SubcategoriesActivity : BaseActivity(), ClickListener<SubCategory> {
    
    companion object {
        const val RC_REFRESH = 888
    }
    
    override fun getFragmentId() = R.layout.activity_subcategories
    
    override fun getViewModelClass() = SubcategoriesViewModel::class.java
    private var viewModel: SubcategoriesViewModel? = null
    
    private var subCategories = ArrayList<SubCategory>()
    private var adapter: SubcategoriesAdapter? = null
    
    private lateinit var categoryId: String
    private lateinit var categoryName: String
    
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RC_REFRESH) {
            refresh()
        }
    }
    
    override fun setUp() {
        viewModel = vm as SubcategoriesViewModel?
        viewModel?.build()
        
        categoryName = intent.getStringExtra(EXTRA_CATEGORY_NAME) ?: "???"
        categoryId = intent.getStringExtra(EXTRA_CATEGORY_ID) ?: run {
            // TODO("do error message")
            finish()
            return
        }
        
    }
    
    // TODO("make color gradient")
    override fun ui() {
        
        buildHeader(R.string.subcategory, 25f)
    
        buildBottomNavigationMenu()
        setStatusBarTransparent()
        
        adapter = SubcategoriesAdapter(this, subCategories, this).also { a ->
            list_subcategories.apply {
                val linearLayoutManager = LinearLayoutManager(this@SubcategoriesActivity, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                itemAnimator = DefaultItemAnimator()
                adapter = a
            }
        }
    
        list_subcategories.post {
            // Add margin bottom to recyclerview for this one don't hide by bottom navigation menu
            val paramsRecyclerViewRanking = list_subcategories.layoutParams as ViewGroup.MarginLayoutParams
            paramsRecyclerViewRanking.bottomMargin = bottom_navigation_menu.measuredHeight
        }
        
        refresh()
    }
    
    private fun refresh() {
        subCategories.clear()
        adapter?.notifyDataSetChanged()
        viewModel?.getSubcategories(categoryId)?.observe(this, {
            it.takeIf { it != null && it.isNotEmpty() }?.let { list ->
                refresh(list)
            }
        })
    }
    
    private fun refresh(list: ArrayList<SubCategory>) {
        subCategories.clear()
        subCategories.addAll(list)
        adapter?.notifyDataSetChanged()
    }
    
    override fun onClick() {
        add_subcategory.setOnClickListener {
            val intent = Intent(this, SubcategoryFormActivity::class.java)
            //     intent.putExtra(EXTRA_CATEGORY_NAME, o.name)
            intent.putExtra(EXTRA_CATEGORY_ID, categoryId)
            
            resultLauncher.launch(intent)
        }
    }
    
    override fun onClick(o: SubCategory) {
        val intent = Intent(this, SubCategoryActivity::class.java)
        
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName)
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId)
        
        intent.putExtra(Constants.EXTRA_SUBCATEGORY_ID, o.id)
        intent.putExtra(Constants.EXTRA_SUBCATEGORY_NAME, o.name)
        intent.putExtra(Constants.EXTRA_SUBCATEGORY_URL, o.imageURL)
        
        resultLauncher.launch(intent)
    }
    
    override fun onLongClick(o: SubCategory) {
    }
}