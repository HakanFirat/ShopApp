package com.example.shopapp.ui.basket

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.R
import com.example.shopapp.data.model.BasketModel
import com.example.shopapp.databinding.RowBasketBinding
import com.example.shopapp.ui.common.BindingAdapter
import com.example.shopapp.ui.ext.showErrorSnackBar

class BasketAdapter(
    private val onRemoveBasketClick: (String) -> Unit = {},
    private val onIncreaseClick: (BasketModel) -> Unit = {},
    private val onDecreaseClick: (BasketModel) -> Unit = {}
) : RecyclerView.Adapter<BasketAdapter.BasketHolder>() {

    private var basketList = listOf<BasketModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BasketHolder(
        RowBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BasketHolder, position: Int) =
        holder.bind(basketList[position])

    inner class BasketHolder(private var itemBasketBinding: RowBasketBinding) :
        RecyclerView.ViewHolder(itemBasketBinding.root) {

        fun bind(basketModel: BasketModel) = with(itemBasketBinding) {

            tvBasketName.text = basketModel.productTitle
            val productPrice = "${basketModel.productPrice} TL"
            tvBasketPrice.text = productPrice
            BindingAdapter.loadImageFromUrl(ivBasket, basketModel.img)

            tvAmount.text = basketModel.count.toString()

            btnMinus.setOnClickListener {
                if (basketModel.count != 1) {
                    onDecreaseClick(basketModel)
                    basketModel.count--
                    tvAmount.text = basketModel.count.toString()
                } else {
                    onRemoveBasketClick(basketModel.uuid)
                }
            }

            btnPlus.setOnClickListener {
                onIncreaseClick(basketModel)
                basketModel.count++
                tvAmount.text = basketModel.count.toString()
            }
        }
    }

    override fun getItemCount() = basketList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(updatedList: List<BasketModel>) {
        this.basketList = updatedList
        notifyDataSetChanged()
    }
}