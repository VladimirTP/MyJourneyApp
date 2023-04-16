package com.example.myjourneyapp.ui.main.fragments.home.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myjourneyapp.databinding.MealFromCategoryItemBinding
import com.example.myjourneyapp.domain.models.MealsPopularData

class MealFromCategoryViewHolder(
    private val binding: MealFromCategoryItemBinding,
    private val itemCLick: (String, String, String) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: MealsPopularData) {
        with(binding) {
            Glide
                .with(itemView.context)
                .load(item.imageUrl)
                .into(ivMealFromCategory)

            tvMealNameFromCategory.text = item.mealName
        }

        itemView.setOnClickListener {
            itemCLick(item.idMeal, item.mealName, item.imageUrl)
        }
    }
}