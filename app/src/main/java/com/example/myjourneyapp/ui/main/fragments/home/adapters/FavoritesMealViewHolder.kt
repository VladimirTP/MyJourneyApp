package com.example.myjourneyapp.ui.main.fragments.home.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myjourneyapp.databinding.MealFromCategoryItemBinding
import com.example.myjourneyapp.domain.models.MealsData

class FavoritesMealViewHolder(
    private val binding: MealFromCategoryItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: MealsData) {
        with(binding) {
            Glide
                .with(itemView.context)
                .load(item.imageUrl)
                .into(ivMealFromCategory)

            tvMealNameFromCategory.text = item.mealName
        }
    }
}