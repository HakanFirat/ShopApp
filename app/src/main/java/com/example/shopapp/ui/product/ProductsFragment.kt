package com.example.shopapp.ui.product


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
import java.util.*

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

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
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
            onClickedRoot = {
                onClickDetail(productList[it])
            },
            onClickedBasket = {
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

    private fun onClickDetail(productModel: ProductModel) {
        val action =
            ProductsFragmentDirections.actionProductsToProductDetail(productModel)
        findNavController().navigate(action)
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = mutableListOf<ProductModel>()
            for (i in productList) {
                if (i.productTitle.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                productAdapter.setFilteredList(filteredList)
            }
        }
    }

}