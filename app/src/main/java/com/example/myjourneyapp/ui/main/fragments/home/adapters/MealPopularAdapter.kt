package com.example.myjourneyapp.ui.main.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myjourneyapp.databinding.PopularItemBinding
import com.example.myjourneyapp.domain.models.MealsPopularData

class MealPopularAdapter(
    private val mealsListData: List<MealsPopularData>,
    private val itemCLick: (String, String, String) -> Unit): RecyclerView.Adapter<MealPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealPopularViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val popularItemBinding = PopularItemBinding.inflate(layoutInflater, parent, false)
        return MealPopularViewHolder(popularItemBinding, itemCLick)
    }

    override fun onBindViewHolder(holder: MealPopularViewHolder, position: Int) {
        holder.onBind(mealsListData[position])
    }

    override fun getItemCount(): Int = mealsListData.size
}