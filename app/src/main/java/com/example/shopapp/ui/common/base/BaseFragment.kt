package com.example.shopapp.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopapp.data.AppError
import com.example.shopapp.ui.common.DialogUtils
import com.example.shopapp.ui.common.autoCleared
import javax.inject.Inject

abstract class BaseFragment<VB: ViewDataBinding, VM: ViewModel>: Fragment(), BaseView {

    val viewModel: VM by lazy { ViewModelProvider(this).get(getViewModelKey()) }
    var binding by autoCleared<VB>()

    @Inject
    lateinit var dialogUtils: DialogUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readDataFromArguments()
        initViews()
        initObservers()
        initLogic()
    }

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected abstract fun getViewModelKey(): Class<VM>

    open fun readDataFromArguments() {}

    open fun initViews() {}

    open fun initObservers() {}

    open fun initLogic() {}

    override fun showLoading() {
        dialogUtils.showProgressDialog(context = context)
    }

    override fun hideLoading() {
        dialogUtils.hideProgressDialog()
    }

    override fun showError(error: AppError) {
        dialogUtils.showAlertDialog(context = context, error = error)
    }
}