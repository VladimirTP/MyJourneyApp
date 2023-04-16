package com.example.myjourneyapp.ui.main.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myjourneyapp.databinding.MealFromCategoryItemBinding
import com.example.myjourneyapp.domain.models.MealsPopularData

class MealFromCategoryAdapter(
    private val categoryList: List<MealsPopularData>,
    private val itemCLick: (String, String, String) -> Unit): RecyclerView.Adapter<MealFromCategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealFromCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val mealFromCategoryItemBinding = MealFromCategoryItemBinding.inflate(layoutInflater, parent, false)
        return MealFromCategoryViewHolder(mealFromCategoryItemBinding, itemCLick)
    }

    override fun onBindViewHolder(holder: MealFromCategoryViewHolder, position: Int) {
        holder.onBind(categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size
}