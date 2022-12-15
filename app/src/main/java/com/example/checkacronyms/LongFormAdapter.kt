package com.example.checkacronyms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.checkacronyms.data.LongForm
import com.example.checkacronyms.databinding.RecyclerviewLongformBinding

class LongFormAdapter: RecyclerView.Adapter<LongFormAdapter.LongFormViewHolder>() {

    private lateinit var binding: RecyclerviewLongformBinding
    private lateinit var list: List<LongForm>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LongFormViewHolder {
        binding = RecyclerviewLongformBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LongFormViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LongFormViewHolder, position: Int) {
        val longForm = list[position]
        holder.bind(longForm)
        val variants = longForm.vars
        val variationAdapter = VariationAdapter()
        holder.recyclerView.apply {
            adapter = variationAdapter
        }
        variationAdapter.setData(variants)
    }

    override fun getItemCount(): Int {
         return if (this::list.isInitialized) list.size else 0
    }

    fun setData(longForms: List<LongForm>) {
        list = longForms
        notifyDataSetChanged()
    }

    class LongFormViewHolder(private var binding: RecyclerviewLongformBinding): RecyclerView.ViewHolder(binding.root){

        val recyclerView = binding.recyclerViewVariation

        fun bind(longForm: LongForm) {
            binding.longForm = longForm
        }
    }
}