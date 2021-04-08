package com.example.tmdb.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var _appBarLayout: AppBarLayout
    private lateinit var _collapsingLayout: CollapsingToolbarLayout
    private lateinit var _toolbar: Toolbar
    private lateinit var _toolbarImage: ImageView
    private var _navHostFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _appBarLayout = findViewById(R.id.toolbarLayout)
        _collapsingLayout = findViewById(R.id.collapsingLayout)
        _toolbar = findViewById(R.id.toolbar)
        _toolbarImage = findViewById(R.id.toolbarImage)
        _navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        adjustToolbar(resources.getString(R.string.app_name), null, false)
    }

    fun adjustToolbar(title: String, imgPath: String?, backNavigation: Boolean) {
        setSupportActionBar(_toolbar)

        supportActionBar?.let {
            _collapsingLayout.title = title
            if (imgPath == null) {
                _appBarLayout.setExpanded(false, false)
                _collapsingLayout.contentScrim = ColorDrawable(getColor(R.color.orange_primary))
            } else {
                _appBarLayout.setExpanded(true, false)
                Glide.with(toolbarImage).load(imgPath).into(_toolbarImage)
                _collapsingLayout.contentScrim = null
            }

            val allowExpansion = imgPath != null
            ViewCompat.setNestedScrollingEnabled(findViewById(R.id.nestedScroll), allowExpansion)

            it.setDisplayHomeAsUpEnabled(backNavigation)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (_navHostFragment?.childFragmentManager?.backStackEntryCount == 1)
            adjustToolbar(resources.getString(R.string.app_name), null, false)
    }
}