package com.example.mvp_meal_db.core.model

import com.example.mvc_meal_db.core.model.CategoryModel

interface ICommonView {
    fun showCategories(categories: List<CategoryModel>)
    fun showMessage(str: String)
}