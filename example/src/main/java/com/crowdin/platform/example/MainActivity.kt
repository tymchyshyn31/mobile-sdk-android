package com.crowdin.platform.example

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.crowdin.platform.Crowdin
import com.crowdin.platform.example.fragments.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        replaceFragment(HomeFragment.newInstance())
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.post { navigationView.setCheckedItem(R.id.nav_home) }
        setTitle(R.string.home)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)
        Crowdin.updateMenuItemsText(menu, resources, R.menu.activity_menu)
        return true
    }

    override fun onBackPressed() =
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val fragment: Fragment?
        var title = 0

        when (menuItem.itemId) {
            R.id.nav_home -> {
                fragment = HomeFragment.newInstance()
                title = R.string.home
            }
            R.id.nav_camera -> {
                fragment = CameraFragment.newInstance()
                title = R.string.camera
            }
            R.id.nav_gallery -> {
                fragment = GalleryFragment.newInstance()
                title = R.string.gallery
            }
            R.id.nav_slideshow -> {
                fragment = SlideshowFragment.newInstance()
                title = R.string.slideshow
            }
            R.id.nav_manage -> {
                fragment = ToolsFragment.newInstance()
                title = R.string.tools
            }
            R.id.nav_share -> {
                fragment = ShareFragment.newInstance()
                title = R.string.share
            }
            R.id.nav_send -> {
                fragment = SendFragment.newInstance()
                title = R.string.send
            }
            else -> fragment = null
        }

        if (fragment != null) {
            replaceFragment(fragment)
            setTitle(title)
        }

        menuItem.isChecked = true
        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun replaceFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit()
}