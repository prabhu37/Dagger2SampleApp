package com.getdefault.smsapplication.smslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.getdefault.smsapplication.data.db.entity.SMSEntity
import com.getdefault.smsapplication.databinding.ItemSmsBinding

class SmsListAdapter() :
    RecyclerView.Adapter<SmsListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val smsDetails: SMSEntity = messages.currentList[position]
        holder.bind(smsDetails)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemListBinding = ItemSmsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemListBinding)
    }

    override fun getItemCount(): Int {
        return messages.currentList.size
    }

    fun submitList(messageList: List<SMSEntity>) {
        messages.submitList(messageList)
    }

    private val diffCallback: DiffUtil.ItemCallback<SMSEntity> =
        object : DiffUtil.ItemCallback<SMSEntity>() {
            override fun areItemsTheSame(
                oldProduct: SMSEntity,
                newProduct: SMSEntity
            ): Boolean {
                return oldProduct.time.equals(newProduct.time)
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldProduct: SMSEntity,
                newProduct: SMSEntity
            ): Boolean {
                return oldProduct.time === newProduct.time
            }
        }
    var messages: AsyncListDiffer<SMSEntity> = AsyncListDiffer(this, diffCallback)
    inner class ViewHolder(val binding: ItemSmsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(smsDetails: SMSEntity) {
            binding.tvSenderName.text = smsDetails.number
            binding.tvMessage.text = smsDetails.message
            binding.tvTime.text = smsDetails.time

        }
    }

}