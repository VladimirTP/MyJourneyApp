package com.example.myjourneyapp.ui.main.fragments.home.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myjourneyapp.databinding.CategoryItemBinding
import com.example.myjourneyapp.domain.models.MealsCategoryData

class MealCategoryViewHolder(
    private val binding: CategoryItemBinding,
    private val itemCLick: (String, String) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: MealsCategoryData) {
        with(binding) {
            Glide
                .with(itemView.context)
                .load(item.strCategoryThumb)
                .into(ivCategory)

            tvCategoryName.text = item.strCategory
        }

        itemView.setOnClickListener {
            itemCLick(item.strCategoryThumb, item.strCategory)
        }
    }
}