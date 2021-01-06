package com.auwfar.stockbitminiproject.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.auwfar.stockbitminiproject.databinding.ItemCryptoBinding
import com.auwfar.stockbitminiproject.models.CryptoDataModel

class CryptoAdapter: PagingDataAdapter<CryptoDataModel, CryptoAdapter.CryptoViewHolder>(CryptoComparator) {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        context = parent.context
        return CryptoViewHolder(ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class CryptoViewHolder(private val binding: ItemCryptoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cryptoDataModel: CryptoDataModel?) {
            binding.textCoinName.text = cryptoDataModel?.coinInfo?.name
            binding.textFullname.text = cryptoDataModel?.coinInfo?.fullName
            binding.textVolume24h.text = cryptoDataModel?.display?.usd?.volume24h
            binding.textRating.run {
                text = cryptoDataModel?.coinInfo?.getRating()
                cryptoDataModel?.coinInfo?.getRatingColor()?.let { ratingColor ->
                    setTextColor(ContextCompat.getColor(context, ratingColor))
                }
            }

        }
    }

    private object CryptoComparator : DiffUtil.ItemCallback<CryptoDataModel>() {
        override fun areItemsTheSame(oldItem: CryptoDataModel, newItem: CryptoDataModel): Boolean {
            return oldItem.coinInfo?.id == newItem.coinInfo?.id
        }

        override fun areContentsTheSame(oldItem: CryptoDataModel, newItem: CryptoDataModel): Boolean {
            return oldItem == newItem
        }
    }
}