package com.yousufjamil.igcseaccountingplatform

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : ChangeTheme() {

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedGradients = arrayOf(getSharedPreferences("themeInfo", MODE_PRIVATE))
        val userTheme = sharedGradients[0].getString("userTheme", "Theme.Default")
        val themeId = resources.getIdentifier(userTheme.toString(), "style", packageName)
        setTheme(themeId)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout:DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA)
//        val themeResId: Int = packageInfo.applicationInfo.theme
//        val currenttheme = resources.getResourceEntryName(themeResId)

        when (userTheme) {
            "Theme.Default" -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#8787c6")))
            }
            "Theme.Red" -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#d56464")))
            }
            "Theme.Green" -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00c98e")))
            }
            "Theme.Blue" -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#008ec9")))
            }
            "Theme.Teal" -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#009696")))
            }
        }
        supportActionBar?.title = Html.fromHtml("<font color='#FFFFFF'>Accorm</font>")

        navView.setNavigationItemSelectedListener {item ->
            when(item.itemId) {
                R.id.home -> {
                    TODO("Development in progress")
                }
                R.id.subjects -> {
                    TODO("Development in progress")
                }
                R.id.features -> {
                    TODO("Development in progress")
                }
                R.id.contribute -> {
                    TODO("Development in progress")
                }
                R.id.about -> {
                    TODO("Development in progress")
                }
                else -> Toast.makeText(this, "Unknown Error", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        if (item.itemId == R.id.settings) {
            val settingsintent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsintent)
        }
        return super.onOptionsItemSelected(item)
    }
}