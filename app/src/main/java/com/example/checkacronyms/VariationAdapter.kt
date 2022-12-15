package com.example.checkacronyms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.checkacronyms.data.Variation
import com.example.checkacronyms.databinding.RecyclerviewVariationBinding


class VariationAdapter: RecyclerView.Adapter<VariationAdapter.VariationViewHolder>() {

    private lateinit var list: List<Variation>
    private lateinit var binding: RecyclerviewVariationBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariationViewHolder {
        binding = RecyclerviewVariationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VariationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VariationViewHolder, position: Int) {
        val variation = list[position]
        holder.bind(variation)
    }

    override fun getItemCount(): Int {
        return if(this::list.isInitialized) list.size else 0
    }

    fun setData(variations: List<Variation>){
        list = variations
        notifyDataSetChanged()
    }

    class VariationViewHolder(private val binding: RecyclerviewVariationBinding): RecyclerView.ViewHolder(binding.root) {

       fun bind(variation: Variation) {
           binding.variation = variation
       }

    }
}