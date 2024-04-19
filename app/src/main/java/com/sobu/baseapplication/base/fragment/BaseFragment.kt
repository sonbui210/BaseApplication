package com.sobu.baseapplication.base.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import androidx.lifecycle.withResumed
import androidx.lifecycle.withStarted
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.sobu.baseapplication.base.viewmodel.BaseViewModel
import com.sobu.baseapplication.commons.koin.DIComponent
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

abstract class BaseFragment<viewModel : BaseViewModel, viewBinding : ViewBinding>(viewModelClass: KClass<viewModel>) :
    FragmentGeneral() {

    protected val viewModel by createViewModelLazy(viewModelClass, { viewModelStore })
    private var _viewBinding: viewBinding? = null
    protected val viewBinding get() = _viewBinding!! // ktlint-disable

    private var hasInitializedRootView = false
    private var rootView: View? = null

    protected val diComponent by lazy { DIComponent() }

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): viewBinding

    protected abstract fun initialize()

    abstract fun onViewCreatedOneTime()

    abstract fun onViewCreatedEverytime()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView?.let {
            _viewBinding = DataBindingUtil.bind(it)
            (it.parent as? ViewGroup)?.removeView(rootView)
            return it
        } ?: kotlin.run {
            _viewBinding = inflateViewBinding(inflater, container)
            return viewBinding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        onSubscribeObserver()

        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            onViewCreatedOneTime()
        }
        onViewCreatedEverytime()
    }

    /**
     * Fragments outlive their views. Make sure you clean up any references to
     * the binding class instance in the fragment's onDestroyView() method.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        hasInitializedRootView = false
        rootView = null
    }

    open fun onSubscribeObserver() {

    }

    protected fun navigateTo(fragmentId: Int, action: Int, bundle: Bundle) {
        launchWhenCreated {
            if (isAdded && isCurrentDestination(fragmentId)) {
                findNavController().navigate(action, bundle)
            }
        }
    }

    protected fun navigateTo(fragmentId: Int, action: Int) {
        launchWhenCreated {
            if (isAdded && isCurrentDestination(fragmentId)) {
                findNavController().navigate(action)
            }
        }
    }

    protected fun navigateTo(fragmentId: Int, action: NavDirections) {
        launchWhenCreated {
            if (isAdded && isCurrentDestination(fragmentId)) {
                findNavController().navigate(action)
            }
        }
    }

    protected fun popFrom(fragmentId: Int) {
        launchWhenCreated {
            if (isAdded && isCurrentDestination(fragmentId)) {
                findNavController().popBackStack()
            }
        }
    }

    protected fun popFrom(fragmentId: Int, destinationFragmentId: Int, inclusive: Boolean = false) {
        launchWhenCreated {
            if (isAdded && isCurrentDestination(fragmentId)) {
                findNavController().popBackStack(destinationFragmentId, inclusive)
            }
        }
    }

    private fun isCurrentDestination(fragmentId: Int): Boolean {
        return findNavController().currentDestination?.id == fragmentId
    }

    protected fun launchWhenCreated(callback: () -> Unit) {
        lifecycleScope.launch { lifecycle.withCreated(callback) }
    }

    protected fun launchWhenStarted(callback: () -> Unit) {
        lifecycleScope.launch { lifecycle.withStarted(callback) }
    }

    protected fun launchWhenResumed(callback: () -> Unit) {
        lifecycleScope.launch { lifecycle.withResumed(callback) }
    }
}
