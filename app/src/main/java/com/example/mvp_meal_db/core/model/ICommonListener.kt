package com.example.mvp_meal_db.core.model

import com.example.mvc_meal_db.core.model.CategoryModel

interface ICommonListener {
    fun onCategoryClick(category: CategoryModel)
}