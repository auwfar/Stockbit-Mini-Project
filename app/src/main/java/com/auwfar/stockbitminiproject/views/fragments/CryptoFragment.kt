package com.auwfar.stockbitminiproject.views.fragments

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.auwfar.stockbitminiproject.R
import com.auwfar.stockbitminiproject.databinding.FragmentCryptoBinding
import com.auwfar.stockbitminiproject.models.CryptoDataModel
import com.auwfar.stockbitminiproject.viewmodels.CryptoViewModel
import com.auwfar.stockbitminiproject.views.adapters.CryptoAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException

class CryptoFragment : Fragment() {
    private lateinit var binding: FragmentCryptoBinding
    private val cryptoViewModel by viewModel<CryptoViewModel>()
    private val cryptoAdapter by lazy {
        CryptoAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            val typedValue = TypedValue()
            activity?.theme?.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
            val color = ContextCompat.getColor(it, typedValue.resourceId)
            binding.progressBottom.indeterminateTintList = ColorStateList.valueOf(color)
        }

        binding.recyclerCrypto.run {
            layoutManager = LinearLayoutManager(context)
            adapter = cryptoAdapter
        }

        binding.swipeCrypto.isRefreshing = true
        fetchCrypto()

        binding.swipeCrypto.setOnRefreshListener {
            lifecycleScope.launch {
                cryptoAdapter.refresh()
            }
        }

        cryptoAdapter.addLoadStateListener {
            binding.progressBottom.isVisible = (it.append == LoadState.Loading)

            if (it.refresh is LoadState.NotLoading) {
                binding.swipeCrypto.isRefreshing = false
                if (cryptoAdapter.itemCount == 0) showEmptyState()
            } else if (it.refresh is LoadState.Error) {
                binding.swipeCrypto.isRefreshing = false
                showErrorState()
            }
        }
    }

    private fun fetchCrypto() {
        lifecycleScope.launch {
            cryptoViewModel.cryptoState.collectLatest { pagingData ->
                cryptoAdapter.run {
                    submitData(pagingData)
                }
            }
        }
    }

    private fun showEmptyState() {
        binding.progressBottom.isVisible = false
        binding.recyclerCrypto.isVisible = false
        binding.viewEmpty.isVisible = true
    }

    private fun showErrorState() {
        binding.recyclerCrypto.isVisible = true
        binding.progressBottom.isVisible = false
        binding.viewEmpty.isVisible = false

        Toast.makeText(context, "Data gagal diload, silahkan swipe untuk reload", Toast.LENGTH_LONG).show()
    }
}