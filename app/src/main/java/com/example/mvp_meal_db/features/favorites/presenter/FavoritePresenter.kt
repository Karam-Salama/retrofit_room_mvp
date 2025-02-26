package com.example.mvp_meal_db.features.favorites.presenter

import android.util.Log
import com.example.mvc_meal_db.core.model.CategoryModel
import com.example.mvc_meal_db.core.model.cache.CategoryDao
import com.example.mvp_meal_db.core.model.ICommonView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritePresenter(private var view: ICommonView, private val dao: CategoryDao){

    suspend fun fetchFavorites() {
        try {
            val categories = dao.getAllCategories()
            withContext(Dispatchers.Main) {
                if (categories.isNullOrEmpty()) {
                    view.showMessage("No categories available")
                    view.showCategories(emptyList())
                } else {
                    view.showCategories(categories)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e("===>", "Error Fetching Favorites", e)
            }
        }
    }


    suspend fun removeCategory(category: CategoryModel) {
        try {
            val removingResult = dao.removeCategory(category)
            withContext(Dispatchers.Main) {
                if (removingResult > 0) {
                    view.showMessage("removed successfully")
                    fetchFavorites()
                } else {
                    view.showMessage("Couldn't remove")
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e("===>", "Error Removing Category", e)
            }
        }
    }

}