package com.lukasz.cointracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lukasz.cointracker.databinding.ListCoinItemBinding
import com.lukasz.cointracker.network.Coin

class CoinAdapter(val clickListener: CoinListener): ListAdapter<Coin, CoinAdapter.CoinViewHolder>(CoinDiffCallback()) {

    class CoinViewHolder (val binding: ListCoinItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(coin: Coin, clickListener: CoinListener){
            binding.viewHolder = coin

            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    class CoinDiffCallback: DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(ListCoinItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener.onClick(holder.binding.viewHolder!!)
        }

        holder.bind(getItem(position)!!, clickListener)
    }

}

class CoinListener(val clickListener: (coin: Coin) -> Unit) {
    fun onClick(coin: Coin) = clickListener(coin)


}
