package com.example.shopapp.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.R
import com.example.shopapp.data.model.ProductModel
import com.example.shopapp.databinding.RowProductBinding
import com.example.shopapp.ui.common.BindingAdapter
import com.example.shopapp.ui.common.base.RecyclerItemClickListener

class ProductAdapter(
    private val productList: List<ProductModel>,
    private val onClicked: RecyclerItemClickListener
): RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    class ProductHolder(
        private val binding: RowProductBinding,
        private val onClicked: RecyclerItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(
                parent: ViewGroup,
                onClicked: RecyclerItemClickListener
            ): ProductHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowProductBinding.inflate(layoutInflater, parent, false)
                return ProductHolder(binding = binding, onClicked = onClicked)
            }
        }

        init {

            binding.btnAddBasket.setOnClickListener {
                onClicked(adapterPosition)
            }
        }

        fun bind(item: ProductModel) {
            val context = binding.root.context
            BindingAdapter.loadImageFromUrl(binding.imgProduct, item.image)
            binding.tvTitle.text = item.productTitle
            binding.tvPrice.text = context.getString(R.string.total_tl, item.productPrice)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder =
        ProductHolder.from(parent = parent,onClicked = onClicked)

    override fun onBindViewHolder(holder: ProductHolder, position: Int) =
        holder.bind(item = productList[position])

    override fun getItemCount(): Int = productList.size
}