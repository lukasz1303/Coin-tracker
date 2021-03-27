package com.lukasz.cointracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lukasz.cointracker.databinding.ListCoinItemBinding
import com.lukasz.cointracker.network.Data

class CoinAdapter(val clickListener: CoinListener): ListAdapter<Data, CoinAdapter.DataViewHolder>(CoinDiffCallback()) {

    class DataViewHolder (val binding: ListCoinItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Data, clickListener: CoinListener){
            binding.viewHolder = data
            binding.data = data

            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    class CoinDiffCallback: DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(ListCoinItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener.onClick(holder.binding.data!!)
        }

        holder.bind(getItem(position)!!, clickListener)
    }

}

class CoinListener(val clickListener: (data: Data) -> Unit) {
    fun onClick(data: Data) = clickListener(data)


}
