package com.example.shopapp.ui.product.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.data.model.ProductFeatureModel
import com.example.shopapp.databinding.RowFeatureBinding

class FeatureRecyclerAdapter(
    private val featureList: MutableList<ProductFeatureModel>
): RecyclerView.Adapter<FeatureRecyclerAdapter.FeatureHolder>() {

    class FeatureHolder(
        private val binding: RowFeatureBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(
                parent: ViewGroup,
            ): FeatureHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowFeatureBinding.inflate(layoutInflater, parent, false)
                return FeatureHolder(binding = binding)
            }
        }

        fun bind(item: ProductFeatureModel) {
            binding.tvType.text = item.type
            binding.tvValue.text = item.feature
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureHolder =
        FeatureHolder.from(parent = parent)

    override fun onBindViewHolder(holder: FeatureHolder, position: Int) =
        holder.bind(item = featureList[position])

    override fun getItemCount(): Int = featureList.size
}