package com.everlapp.androidexamples.animations

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everlapp.androidexamples.R
import com.everlapp.androidexamples.animations.DetailsFragment.Companion.ARG_IMAGE_RES_ID
import timber.log.Timber

class GridFragment : Fragment(), KittenGridAdapter.OnKittenListener {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_animation_fragment_grid, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler: RecyclerView = view.findViewById(R.id.recyclerview)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = GridLayoutManager(activity, 2)
        val items = mutableListOf(R.drawable.placekitten_1,
                R.drawable.placekitten_2,
                R.drawable.placekitten_3,
                R.drawable.placekitten_4,
                R.drawable.placekitten_5,
                R.drawable.placekitten_6)
        recycler.adapter = KittenGridAdapter(items, this)
    }

    override fun onKittenClick(itemId: Int, holder: KittenGridAdapter.KittenViewHolder) {
        Timber.e("On click")
        val detailsFragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putInt(ARG_IMAGE_RES_ID, itemId)
        detailsFragment.arguments = bundle

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detailsFragment.sharedElementEnterTransition = DetailsTransition()
            detailsFragment.enterTransition = Fade()
            exitTransition = Fade()
            detailsFragment.sharedElementReturnTransition = DetailsTransition()
        }

        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.addSharedElement(holder.ivImage, "kittenImage")                             // ViewCompat.getTransitionName(imageView)
                ?.replace(R.id.fragment_container, detailsFragment)
                ?.addToBackStack(null)
                ?.commit()

    }
}