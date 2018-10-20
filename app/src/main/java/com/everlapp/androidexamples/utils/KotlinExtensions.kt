package com.everlapp.androidexamples.utils

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager


/**
 * Extension Utils
 * =================================================================================================
 */

/**
 * Start activity with put Extra
 */
inline fun <reified T : Activity> Activity.navigate(key: String = "", extra : String = "") {
    val intent = Intent(this, T::class.java)
    intent.putExtra(key, extra)
    startActivity(intent)
}

/**
 * Find fragment by TAG
 */
fun FragmentActivity.findFragmentByTagExt(fragmentTag: String) : Boolean =
        this.supportFragmentManager.findFragmentByTag(fragmentTag) != null

/**
 * Inflate container
 * Example: val view = container?.inflate(R.layout.news_fragment)
 */
fun ViewGroup.inflate(@LayoutRes layoutId : Int, attachToRoot : Boolean = false) : View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}


/**
 * Add fragment without backStack
 */
fun FragmentActivity.setFragment(fragment : Fragment,
                                 containerId : Int = android.R.id.content) =
        supportFragmentManager
                .beginTransaction()
                .add(containerId, fragment)
                .commit()


/**
 * Add fragment with backStack
 */
fun FragmentActivity.setFragment(fragment : Fragment,
                                 containerId : Int = android.R.id.content,
                                 backStackName: String?) =
        supportFragmentManager
                .beginTransaction()
                .add(containerId, fragment)
                .addToBackStack(backStackName)
                .commit()

/**
 * Replace fragment without backStack
 */
fun FragmentActivity.replaceFragment(fragment: Fragment ,
                                     containerId: Int = android.R.id.content) =
        supportFragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .commit()

/**
 * Replace fragment with backStack
 */
fun FragmentActivity.replaceFragment(fragment: Fragment ,
                                     containerId: Int = android.R.id.content,
                                     backStackName: String?,
                                     fragmentTag: String = "") =
        supportFragmentManager
                .beginTransaction()
                .replace(containerId, fragment, fragmentTag)
                .addToBackStack(backStackName)
                .commit()

/**
 * !!! If BUG with (like - don't exec after onSavedInstanceState())
 */
fun FragmentActivity.replaceFragmentAllowingStateLoss(fragment: Fragment ,
                                                      containerId: Int = android.R.id.content,
                                                      backStackName: String?,
                                                      fragmentTag: String = "") =
        supportFragmentManager
                .beginTransaction()
                .replace(containerId, fragment, fragmentTag)
                .addToBackStack(backStackName)
                .commitAllowingStateLoss()

/**
 * Get string in fragment
 */
//fun Fragment.getString(@StringRes resId : Int) =
//        activity?.resources?.getString(resId)


/**
 * View visible
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

/**
 * View gone
 */
fun View.hide() {
    this.visibility = View.GONE
}

/**
 * View invisible
 */
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * Hide keyboard
 */
fun Activity.hideKeyboard() {
    val inputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
}



/**
 * Check Device is Online
 */
fun Context.isOnline(): Boolean {
    val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}

/**
 * Fill same color action bar and status bar
 */
fun Activity.setScreenHeaderColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // Set color status bar
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.window.statusBarColor = color
        // Set color action bar
        val appCompatActivity = this as AppCompatActivity
        appCompatActivity.supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
    }
}


/**
 * Fill different statBarColor action bar and status bar
 */
fun Activity.setScreenHeaderColor(statBarColor: Int, actionBarColor: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // Set statBarColor status bar
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.window.statusBarColor = statBarColor
        // Set statBarColor action bar
        val appCompatActivity = this as AppCompatActivity
        appCompatActivity.supportActionBar?.setBackgroundDrawable(ColorDrawable(actionBarColor))
    }
}


/**
 * Set transparent status bar (ONLY SDK >= 19)
 */
fun Activity.setTransparentStatusBar() : Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        this.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        true
    } else false
}

/**
 * Set transparent status bar (ONLY SDK >= 19)
 */
fun Activity.setTransparentStatusBar(makeTranslucent: Boolean) : Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        return if (makeTranslucent) {
            this.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            true
        } else {
            this.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            false
        }
    }else
        return false
}

/**
 * Get colorResource from color resource
 */
fun Activity.getColorExt(@ColorRes colorResource: Int) : Int
        = ActivityCompat.getColor(this, colorResource)