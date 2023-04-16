package com.example.myjourneyapp.ui.main.fragments.home.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myjourneyapp.databinding.PopularItemBinding
import com.example.myjourneyapp.domain.models.MealsPopularData

class MealPopularViewHolder(
    private val binding: PopularItemBinding,
    private val itemCLick: (String, String, String) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: MealsPopularData) {
        with(binding) {
            Glide
                .with(itemView.context)
                .load(item.imageUrl)
                .into(ivPopularMealItem)
        }

        itemView.setOnClickListener {
            itemCLick(item.idMeal, item.mealName, item.imageUrl)
        }
    }
}