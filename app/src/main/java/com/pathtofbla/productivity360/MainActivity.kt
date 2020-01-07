package com.pathtofbla.productivity360

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        drawerNav()
    }

    private fun drawerNav() {

        //onClick listener for the drawer nav button
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //listener for when a navigation button is pressed
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        //sets the default fragment to Overview and selects it in the drawer nav
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            OverviewFragment()
        ).commit()
        nav_view.setCheckedItem(R.id.nav_home)

        /*
         * The burnout button is not in the activity_main_drawer menu because we wanted a big red
         * button instead of the default item style. As a result, the fragment switch can't be
         * defined in the onNavigationItemSelected() function, so its here instead
         */
        burnoutButton.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                BurnoutFragment()
            ).commit()
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    OverviewFragment()
                ).commit()
            }
            R.id.nav_calendar -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    CalendarFragment()
                ).commit()
            }
            R.id.nav_timetable -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    TimetableFragment()
                ).commit()
            }
            R.id.nav_streaks -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    StreaksFragment()
                ).commit()
            }
            R.id.nav_rewards -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    RewardsFragment()
                ).commit()
            }
            R.id.nav_subjects -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    SubjectsFragment()
                ).commit()
            }
            R.id.nav_attendance -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    AttendanceFragment()
                ).commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings -> {
                //TODO
            }
            R.id.friends -> {
                //TODO
            }
            R.id.signout -> {
                FirebaseAuth.getInstance().signOut()
                val switchToLogin = Intent(this, Login::class.java)
                switchToLogin.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(switchToLogin)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //closes the navigation drawer by pressing the back button
    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }
    }
}
