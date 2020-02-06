package com.angopa.aad.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.HomeViewPagerFragmentDirections
import com.angopa.aad.R
import com.angopa.aad.data.localdata.Link
import com.angopa.aad.databinding.ListItemLinkBinding

class LinkAdapter :
    ListAdapter<Link, LinkAdapter.ViewHolder>(
        LinkDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_link,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemLinkBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.link?.linkUri?.let { linkUri ->
                    navigateToDetail(linkUri, view)
                }
            }
        }

        fun navigateToDetail(linkUri: String, view: View) {
            val direction = HomeViewPagerFragmentDirections
                .actionAndroidCoreToDetail(linkUri)
            view.findNavController().navigate(direction)
        }

        fun bind(bindingLink: Link) {
            with(binding) {
                link = bindingLink
                executePendingBindings()
            }
        }
    }
}

private class LinkDiffCallback : DiffUtil.ItemCallback<Link>() {
    override fun areItemsTheSame(oldItem: Link, newItem: Link): Boolean {
        return oldItem.linkId == newItem.linkId
    }

    override fun areContentsTheSame(oldItem: Link, newItem: Link): Boolean {
        return oldItem == newItem
    }

}