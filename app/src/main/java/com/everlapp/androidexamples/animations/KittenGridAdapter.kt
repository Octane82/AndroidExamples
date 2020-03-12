package com.everlapp.androidexamples.animations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.everlapp.androidexamples.R
import kotlinx.android.synthetic.main.grid_item.view.*

class KittenGridAdapter(private val items: List<Int>,
                        private val onKittenListener: OnKittenListener) :
        RecyclerView.Adapter<KittenGridAdapter.KittenViewHolder>() {


    interface OnKittenListener {
        fun onKittenClick(itemId: Int, holder: KittenViewHolder)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KittenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.grid_item, parent, false)
        return KittenViewHolder(view, onKittenListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: KittenViewHolder, position: Int) {
        val item = items[position]
        // It is important that each shared element in the source screen has a unique transition name.
        // For example, we can't just give all the images in our grid the transition name "kittenImage"
        // because then we would have conflicting transition names.
        // By appending "_image" to the position, we can support having multiple shared elements in each
        // grid cell in the future.
        ViewCompat.setTransitionName(holder.ivImage, "${position}_image")
        holder.bind(item)
    }


    inner class KittenViewHolder(view: View,
                                 private val onKittenClicked: OnKittenListener) : RecyclerView.ViewHolder(view) {

        val ivImage: ImageView = view.image

        fun bind(item: Int) {
            ivImage.setImageResource(item)
            ivImage.setOnClickListener { onKittenClicked.onKittenClick(item, this) }
        }
    }


}