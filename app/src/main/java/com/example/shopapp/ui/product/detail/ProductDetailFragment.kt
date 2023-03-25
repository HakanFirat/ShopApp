package com.example.shopapp.ui.product.detail

import android.annotation.SuppressLint
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.R
import com.example.shopapp.data.model.ProductFeatureModel
import com.example.shopapp.data.model.ProductModel
import com.example.shopapp.databinding.FragmentProductDetailBinding
import com.example.shopapp.ui.common.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>() {

    private var productModel = ProductModel()
    private lateinit var featureAdapter: FeatureRecyclerAdapter
    private lateinit var featureViewPagerAdapter: FeatureViewPagerAdapter
    private var featureList = mutableListOf<ProductFeatureModel>()
    private val args: ProductDetailFragmentArgs by navArgs()
    override fun getLayoutRes(): Int = R.layout.fragment_product_detail

    override fun getViewModelKey(): Class<ProductDetailViewModel> = ProductDetailViewModel::class.java

    override fun readDataFromArguments() {
        super.readDataFromArguments()

        arguments?.let {
            val safeArgs = args.objectProduct
            productModel = ProductModel(
                id = safeArgs.id,
                image = safeArgs.image,
                detailImgList = safeArgs.detailImgList,
                productTitle = safeArgs.productTitle,
                productDescription = safeArgs.productDescription,
                productFeatures = safeArgs.productFeatures,
                productPrice = safeArgs.productPrice
            )
        }
    }

    override fun initViews() {
        super.initViews()

        initFeatureRecyclerView()
        initFeatureViewPager()
    }

    override fun initObservers() {
        super.initObservers()
    }

    override fun initLogic() {
        super.initLogic()
        setTextData()

        binding.imgBasket.setOnClickListener {
            binding.imgBasket.setOnClickListener {
                findNavController().navigate(R.id.action_detail_to_basket)
            }
        }

        binding.imgBack.setOnClickListener {
            goBack()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initFeatureRecyclerView() {

        featureList.clear()
        productModel.productFeatures.forEach {
            val model = ProductFeatureModel(
                type = it.key,
                feature = it.value
            )
            featureList.add(model)
        }

        featureAdapter = FeatureRecyclerAdapter(
            featureList = featureList
        )
        binding.rvFeatures.layoutManager = LinearLayoutManager(context)
        binding.rvFeatures.adapter = featureAdapter
    }

    private fun initFeatureViewPager(){
        featureViewPagerAdapter = FeatureViewPagerAdapter(
            imageList = productModel.detailImgList,
        )

        binding.vpProduct.adapter = featureViewPagerAdapter
        TabLayoutMediator(binding.tlCircle, binding.vpProduct) { _, _ ->
        }.attach()
    }

    private fun setTextData(){
        binding.tvTitle.text = productModel.productTitle
        binding.tvDescription.text = productModel.productDescription
        binding.tvAmount.text = getString(R.string.total_tl, productModel.productPrice)
    }

}