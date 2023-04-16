package com.example.myjourneyapp.ui.main.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myjourneyapp.databinding.CategoryItemBinding
import com.example.myjourneyapp.domain.models.MealsCategoryData

class MealCategoriesAdapter(
    private val categoryList: List<MealsCategoryData>,
    private val itemCLick: (String, String) -> Unit
): RecyclerView.Adapter<MealCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val categoryItemBinding = CategoryItemBinding.inflate(layoutInflater, parent, false)
        return MealCategoryViewHolder(categoryItemBinding, itemCLick)
    }

    override fun onBindViewHolder(holder: MealCategoryViewHolder, position: Int) {
        holder.onBind(categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size
}