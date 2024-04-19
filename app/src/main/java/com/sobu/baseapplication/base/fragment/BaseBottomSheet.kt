package com.sobu.baseapplication.base.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sobu.baseapplication.base.viewmodel.BaseViewModel
import com.sobu.baseapplication.commons.koin.DIComponent
import kotlin.reflect.KClass

abstract class BaseBottomSheet<viewModel : BaseViewModel, viewBinding : ViewBinding>(
    viewModelClass: KClass<viewModel>
) : BottomSheetDialogFragment() {

    protected val viewModel by createViewModelLazy(viewModelClass, { viewModelStore })
    private var _viewBinding: viewBinding? = null
    protected val viewBinding get() = _viewBinding!! // ktlint-disable

    protected val diComponent by lazy { DIComponent() }

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): viewBinding

    protected abstract fun initialize()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = inflateViewBinding(inflater, container)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            // Thiết lập dialog trong suốt
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        initialize()
        onSubscribeObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


    private fun showLoading(isShow: Boolean) {

    }

    open fun onSubscribeObserver() {
        viewModel.run {

        }
    }
}