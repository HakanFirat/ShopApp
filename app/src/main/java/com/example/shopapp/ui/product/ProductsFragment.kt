package com.example.shopapp.ui.product


import android.annotation.SuppressLint
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopapp.R
import com.example.shopapp.data.model.BasketModel
import com.example.shopapp.data.model.ProductModel
import com.example.shopapp.databinding.FragmentProductsBinding
import com.example.shopapp.ui.common.base.BaseFragment
import com.example.shopapp.ui.common.base.GridItemDecoration
import com.example.shopapp.ui.ext.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding,ProductsViewModel>() {

    private val productList = mutableListOf<ProductModel>()
    private lateinit var productAdapter: ProductAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_products

    override fun getViewModelKey(): Class<ProductsViewModel> = ProductsViewModel::class.java

    override fun initViews() {
        super.initViews()
        initProductRecyclerView()
    }

    override fun initLogic() {
        super.initLogic()
        viewModel.getProductList()

        binding.imgBasket.setOnClickListener {
            findNavController().navigate(R.id.action_products_to_basket)
        }
    }


    override fun initObservers() {
        super.initObservers()
        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            productList.clear()
            productList.addAll(it)
            fillAndNotifyProductRecyclerView()
        }
    }

    private fun initProductRecyclerView() {
        productAdapter = ProductAdapter(
            productList = productList,
            onClicked = {
                addToBasket(productList[it])
            }
        )
        binding.rvProduct.layoutManager = GridLayoutManager(context, 2)
        binding.rvProduct.adapter = productAdapter
        binding.rvProduct.addItemDecoration(
            GridItemDecoration(
                resources.getDimensionPixelOffset(R.dimen.standard_margin),
                2,
                true
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fillAndNotifyProductRecyclerView() {
        productAdapter.notifyDataSetChanged()
    }

    private fun addToBasket(product: ProductModel){
        viewModel.addToBasket(
            BasketModel(
                id,
                product.id,
                product.image,
                product.productTitle,
                product.productPrice.toInt(),
                product.productCount
            )
        )
        requireView().showErrorSnackBar(getString(R.string.product_add), false)
    }

}