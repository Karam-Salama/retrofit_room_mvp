package com.example.mvp_meal_db.features.categories.presenter

import android.util.Log
import com.example.mvc_meal_db.core.model.CategoryModel
import com.example.mvc_meal_db.core.model.cache.CategoryDao
import com.example.mvp_meal_db.core.model.ICommonView
import com.example.mvp_meal_db.core.model.server.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryPresenter(private var view: ICommonView, private val dao: CategoryDao, var retrofitHelper: RetrofitHelper) {

    suspend fun fetchCategories() {
        try {
            val response = retrofitHelper.retrofitService.getCategories()
            val categories = response.categories

            withContext(Dispatchers.Main) {
                if (categories.isNullOrEmpty()){
                    view.showMessage("Could not fetch categories")
                } else {
                    view.showCategories(categories)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                view.showMessage("Error Fetching Categories")
                Log.e("===>", "Error Fetching Categories", e)
            }
        }
    }

    suspend fun saveCategories(category: CategoryModel) {
        try {
            val addingResult = dao.addCategory(category)
            withContext(Dispatchers.Main){
                // update ui by showing message to confirm the completion
                if (addingResult > 0) {
                    view.showMessage("added to favorites")
                } else {
                    view.showMessage("is already in favorites")
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e("===>", "Error Saving Categories", e)
            }
        }
    }
}