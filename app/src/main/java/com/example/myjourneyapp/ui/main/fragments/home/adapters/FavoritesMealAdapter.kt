package com.example.myjourneyapp.ui.main.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myjourneyapp.databinding.MealFromCategoryItemBinding
import com.example.myjourneyapp.domain.models.MealsData

class FavoritesMealAdapter(): RecyclerView.Adapter <FavoritesMealViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<MealsData>() {

        override fun areItemsTheSame(oldItem: MealsData, newItem: MealsData): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealsData, newItem: MealsData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer (this, diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val mealItemBinding = MealFromCategoryItemBinding.inflate(layoutInflater, parent, false)
        return FavoritesMealViewHolder(mealItemBinding)
    }

    override fun onBindViewHolder(holder: FavoritesMealViewHolder, position: Int) {
        val meal = differ.currentList[position]
        holder.onBind(meal)
    }

    override fun getItemCount(): Int = differ.currentList.size
}