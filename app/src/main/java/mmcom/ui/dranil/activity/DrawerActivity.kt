package mmcom.ui.dranil.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.app_bar_drawer.*
import mmcom.ui.dranil.Adapter.NavigationAdapter
import mmcom.ui.dranil.R
import mmcom.ui.dranil.db.AppDatabase
import mmcom.ui.dranil.fragment.ViewAllCoursesFragment
import mmcom.ui.dranil.model.NavigationItemModel


class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    val navItemList: ArrayList<NavigationItemModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        setSupportActionBar(toolbar)

        var toolbar = findViewById<View>(R.id.toolbar) as Toolbar


        setSupportActionBar(toolbar)



        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        // nav_view.setNavigationItemSelectedListener(this)
        img_back_button.setOnClickListener({
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        })
        btn_logout.setOnClickListener({
            Observable.fromCallable({
                var db = AppDatabase.getAppDatabase(context = this@DrawerActivity)
                var userDao = db?.userDao()

                with(userDao) {
                   var usr =  userDao?.getLogInUser("1")
                    usr?.let { it1 -> userDao?.deleteUser(it1) }
                    startActivity(Intent(  this@DrawerActivity,LoginActivity::class.java))
                    finish()

                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()

        })

        val viewallcourses = ViewAllCoursesFragment()

        createNavigationItemList()
        rv_nav_item_list.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_nav_item_list.adapter = NavigationAdapter(navItemList,this)
        replaceFragment(viewallcourses)
    }

    fun createNavigationItemList() {
        navItemList.add(NavigationItemModel("My Courses", true))
        navItemList.add(NavigationItemModel("Blog", false))
        navItemList.add(NavigationItemModel("Notifications", false))
        navItemList.add(NavigationItemModel("About Us", false))
        navItemList.add(NavigationItemModel("Contact Us", false))
    }


    override fun onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val fragments = supportFragmentManager.backStackEntryCount
            if (fragments == 1) {
                finish()
            } else {
                if (fragmentManager.backStackEntryCount > 1) {
                    fragmentManager.popBackStack()
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // menuInflater.inflate(R.menu.drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /* when (item.itemId) {
             R.id.action_settings -> return true
             else -> return super.onOptionsItemSelected(item)
         }*/
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name


        val manager = supportFragmentManager
        var fragmentPopped = false
        try {

            fragmentPopped = manager.popBackStackImmediate(backStateName, 0)

        } catch (ignored: IllegalStateException) {
            // There's no way to avoid getting this if saveInstanceState has already been called.
        }

        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            val ft = manager.beginTransaction()
            ft.replace(R.id.fragment_container, fragment, backStateName)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.addToBackStack(backStateName)
            ft.commitAllowingStateLoss()
        }

    }
}
