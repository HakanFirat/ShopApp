package com.example.shopapp.ui.basket


import android.view.View
import com.example.shopapp.R
import com.example.shopapp.databinding.FragmentBasketBinding
import com.example.shopapp.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding, BasketViewModel>() {

    private val adapter by lazy {
        BasketAdapter(
            onRemoveBasketClick = ::onRemoveBasketClick,
            viewModel::increase,
            viewModel::decrease
        )
    }

    override fun getLayoutRes(): Int = R.layout.fragment_basket

    override fun getViewModelKey(): Class<BasketViewModel> = BasketViewModel::class.java


    override fun initViews() {
        super.initViews()
        binding.rvBasket.adapter = adapter

        binding.tvCleanBasket.setOnClickListener {
            viewModel.deleteAllBasket()
        }
    }

    override fun initObservers() {
        super.initObservers()

        viewModel.apply {
            readAllBasket.observe(viewLifecycleOwner) { basketList ->
                totalBasket()
                adapter.updateList(basketList)
            }

            totalAmount.observe(viewLifecycleOwner) {
                if (it == null)
                    binding.tvAmount.text = getString(R.string._0_TL)
                else
                    binding.tvAmount.text = getString(R.string.total_tl, it.toString())
            }

            totalCount.observe(viewLifecycleOwner) {
                if (it == null){
                    binding.tvProductCount.visibility = View.GONE
                }
                else{
                    binding.tvProductCount.visibility = View.VISIBLE
                    binding.tvProductCount.text = getString(R.string.product, it.toString())
                }

            }
        }
    }

    override fun initLogic() {
        super.initLogic()

        binding.imgBack.setOnClickListener {
            goBack()
        }
    }

    private fun onRemoveBasketClick(s: String) {
        viewModel.deleteFromBasket(s)
        viewModel.resetTotalAmount()
    }
}