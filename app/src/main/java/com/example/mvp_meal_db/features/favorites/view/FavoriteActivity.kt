package com.example.mvp_meal_db.features.favorites.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvc_meal_db.core.model.CategoryModel
import com.example.mvc_meal_db.core.model.cache.CategoryDatabase
import com.example.mvp_meal_db.R
import com.example.mvp_meal_db.core.model.ICommonListener
import com.example.mvp_meal_db.core.model.ICommonView
import com.example.mvp_meal_db.core.view.CategoryAdapter
import com.example.mvp_meal_db.features.favorites.presenter.FavoritePresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() ,ICommonView, ICommonListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private lateinit var favoritePresenter: FavoritePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite)

        initUI()
        setUpPresenter()
    }

    private fun initUI() {
        recyclerView = findViewById(R.id.rc_favorite)
        adapter = CategoryAdapter(listOf(), recyclerView.context, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setUpPresenter() {
        val dao = CategoryDatabase.getDatabase(applicationContext).getCategoryDao()
        favoritePresenter = FavoritePresenter(this,dao)
        lifecycleScope.launch {
            favoritePresenter.fetchFavorites()
        }
    }

    override fun showCategories(categories: List<CategoryModel>) {
        adapter.categories = categories
        adapter.notifyDataSetChanged()
    }

    override fun showMessage(str: String) {
        Snackbar.make(recyclerView, str , Snackbar.LENGTH_SHORT).show()
    }

    override fun onCategoryClick(category: CategoryModel) {
        lifecycleScope.launch {
            favoritePresenter.removeCategory(category)
            favoritePresenter.fetchFavorites()
        }
    }
}