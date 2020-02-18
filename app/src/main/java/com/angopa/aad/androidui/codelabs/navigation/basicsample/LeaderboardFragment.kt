package com.angopa.aad.androidui.codelabs.navigation.basicsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentNavigationLeaderboardBinding

/**
 *  Created by Andres Gonzalez on 02/17/2020.
 */
class LeaderboardFragment : Fragment() {
    private lateinit var binding: FragmentNavigationLeaderboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNavigationLeaderboardBinding>(
            inflater,
            R.layout.fragment_navigation_leaderboard,
            container,
            false
        ).apply {
            val viewAdapter = MyAdapter(arrayOf("Flo", "Ly", "Jo"))
            leaderboardList.run {
                setHasFixedSize(true)
                adapter = viewAdapter
            }
        }

        return binding.root
    }
}

class MyAdapter(private val myDataset: Array<String>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_navigation, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = myDataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.findViewById<TextView>(R.id.username_text).text = myDataset[position]
        holder.item.findViewById<ImageView>(R.id.user_avatar_image)
            .setImageResource(listOfAvatars[position])

        holder.item.setOnClickListener {
            val bundle = bundleOf("userName" to myDataset[position])

            Navigation.findNavController(holder.item)
                .navigate(R.id.action_leaderboard_to_user_profile, bundle)
        }
    }

    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)
}


private val listOfAvatars =
    listOf(R.drawable.avatar_1_raster, R.drawable.avatar_2_raster, R.drawable.avatar_3_raster)
