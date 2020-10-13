package com.developer.vitapbookhub.Activity

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.developer.vitapbookhub.Fragment.*
import com.developer.vitapbookhub.R

private  const val PERMISSION_REQUEST=10
class HomeActivity : AppCompatActivity() {
    lateinit var drawerlayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var frame: FrameLayout
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    lateinit var navigationview: NavigationView
    private var permissions= arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var context:Context
    var bundle=Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if(checkPermission(this,permissions))
        {
           // Toast.makeText(context,"Permission are already provided",Toast.LENGTH_SHORT).show()
        }
        else
        {
                requestPermissions(permissions, PERMISSION_REQUEST)
        }
        }

        /*if(intent!=null)
        {
            var email=intent.getStringExtra("email")
        }*/

        var previousMenuItem: MenuItem?=null
        drawerlayout=findViewById(R.id.drawerlayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        frame=findViewById(R.id.frame)
        toolbar=findViewById(R.id.toolbar)
        navigationview=findViewById(R.id.navigation)
        SetUpToolbar()
        val actionBarDrawerToggle=
            ActionBarDrawerToggle(this@HomeActivity,drawerlayout,
                R.string.open_drawer,
                R.string.close_drawer
            )
        drawerlayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        openDashboardFragment()
        var bundle=Bundle()
        bundle.putString("email",intent.getStringExtra("email"))
        navigationview.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId)
            {
                R.id.dashboard ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        HomeFragment()
                    ).commit()
                    drawerlayout.closeDrawers()
                    supportActionBar?.title="DashBoard"
                }
                R.id.profile ->{

                    var frag=ProfileFragment()
                    frag.arguments=bundle
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        frag
                    )
                        .commit()

                    drawerlayout.closeDrawers()
                    supportActionBar?.title="Profile"
                }
                R.id.aboutUs ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        AboutUsFragment()
                    ).commit()
                    drawerlayout.closeDrawers()
                    supportActionBar?.title="About Us"
                }
                /*R.id.favourites ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        FavoritesFragment()
                    ).commit()
                    drawerlayout.closeDrawers()
                    supportActionBar?.title="Favourites"
                }*/
                R.id.Feedback ->{
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        FeedBackFragment()
                    ).commit()
                    drawerlayout.closeDrawers()
                    supportActionBar?.title="FeedBack"
                }
                R.id.logout ->{
                    var frag=LogoutFragment()
                    frag.arguments=bundle
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frame,
                        frag
                    ).commit()
                    drawerlayout.closeDrawers()
                    supportActionBar?.title="Logout"
                }

            }
            return@setNavigationItemSelectedListener true
        }

    }
    fun openDashboardFragment()
    {
        //val fragment= HomeFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.frame,
            HomeFragment()
        )
        transaction.commit()
        supportActionBar?.title="DashBoard"
        navigationview.setCheckedItem(R.id.dashboard)
        drawerlayout.closeDrawers()

    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){
            !is HomeFragment -> openDashboardFragment()
            else ->{
                ActivityCompat.finishAffinity(this@HomeActivity)
                super.onBackPressed()}
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item.itemId

        if(id==android.R.id.home)
        {
            drawerlayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)


    }
    fun SetUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    fun checkPermission(context: Context,PermissionArray:Array<String>):Boolean
    {
        var allsuccess=true
        for(i in PermissionArray.indices)
        {
            if(checkCallingOrSelfPermission(PermissionArray[i] )==PackageManager.PERMISSION_DENIED)
            {
                allsuccess=false
            }
        }
        return allsuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== PERMISSION_REQUEST)
        {
            var allsuccess=true
            for(i in permissions.indices)
            {
                if(grantResults[i]==PackageManager.PERMISSION_DENIED)
                {
                    allsuccess=false
                    var requestAgain =Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if(requestAgain)
                    {
                        Toast.makeText(context,"Permission Denied",Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(context,"Go to setting and enabled the permission",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if(allsuccess)
            {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
