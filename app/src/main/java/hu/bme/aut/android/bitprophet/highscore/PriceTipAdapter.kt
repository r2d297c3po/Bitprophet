package hu.bme.aut.android.bitprophet.highscore

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import hu.bme.aut.android.bitprophet.database.PriceTip
import hu.bme.aut.android.bitprophet.databinding.ScoreItemBinding
import hu.bme.aut.android.bitprophet.round
import hu.bme.aut.android.bitprophet.toDate

// Google Jetpack recommended adapter pattern, which I don't fully understand yet, using diffutil
class PriceTipAdapter : ListAdapter<PriceTip, PriceTipAdapter.PriceTipViewHolder>(
    PriceTipDiffCallBack()
) {


    class PriceTipViewHolder private constructor(private val binding: ScoreItemBinding) : ViewHolder(binding.root) {
        fun bind(item: PriceTip) {
            binding.apply {
                tvPriceTime.text = item.tipStartTime.toDate()
                tvPriceTipTime.text = item.tipEndTime.toDate()
                tvPriceTipped.text = item.tippedPrice.round()
                tvTipPriceReal.text = item.realPrice?.round()
                tvTipPoints.text = item.tipPoint.round()
            }
            //binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PriceTipViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ScoreItemBinding.inflate(layoutInflater, parent, false)


                return PriceTipViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceTipViewHolder {

        return PriceTipViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: PriceTipViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class PriceTipDiffCallBack : DiffUtil.ItemCallback<PriceTip>() {
    override fun areItemsTheSame(oldItem: PriceTip, newItem: PriceTip): Boolean {
        return oldItem.tipId == newItem.tipId
    }

    override fun areContentsTheSame(oldItem: PriceTip, newItem: PriceTip): Boolean {
        return oldItem == newItem
    }
}