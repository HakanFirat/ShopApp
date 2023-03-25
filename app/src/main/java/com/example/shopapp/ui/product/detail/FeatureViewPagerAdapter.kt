package com.example.shopapp.ui.product.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.RowProductImageBinding
import com.example.shopapp.ui.common.BindingAdapter

class FeatureViewPagerAdapter(
    private val imageList: MutableList<String> = mutableListOf()
): RecyclerView.Adapter<FeatureViewPagerAdapter.ProductImageHolder>() {

    class ProductImageHolder(
        private val binding: RowProductImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(
                parent: ViewGroup
            ): ProductImageHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowProductImageBinding.inflate(layoutInflater, parent, false)
                return ProductImageHolder(binding = binding)
            }
        }

        fun bind(item: String) {
            val context = binding.root.context
            BindingAdapter.loadImageFromUrl(binding.ivProduct,item)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageHolder =
        ProductImageHolder.from(parent = parent)

    override fun onBindViewHolder(holder: ProductImageHolder, position: Int) =
        holder.bind(item = imageList[position])

    override fun getItemCount(): Int = imageList.size
}