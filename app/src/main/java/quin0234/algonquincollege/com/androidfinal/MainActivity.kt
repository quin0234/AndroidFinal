package quin0234.algonquincollege.com.androidfinal

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Button
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener{
            val intent = Intent(this@MainActivity, AddNewBuilding::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        recyclerview_main.layoutManager = LinearLayoutManager(this)

        fetchJson(0)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.sortaz -> {
                fetchJson(12)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.sortza -> {
                fetchJson(13)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.regBldings -> {
                fetchJson(1)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.embBldings -> {
                fetchJson(2)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.govBldings -> {
                fetchJson(3)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.funBldings -> {
                fetchJson(4)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.artBlgins -> {
                fetchJson(5)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.eduBldings -> {
                fetchJson(6)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.sportBldings -> {
                fetchJson(7)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.combldings -> {
                fetchJson(8)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.busBldings -> {
                fetchJson(9)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.historyBldings -> {
                fetchJson(10)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.otherBldings -> {
                fetchJson(11)
                drawer_layout.closeDrawer(GravityCompat.START)
                return true
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun fetchJson(event: Int) {
        println("Fetching JSON")

        var url = "https://doors-open-ottawa.mybluemix.net/buildings"

        if (event == 0) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings"
        } else if (event == 1) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[0]"
        } else if (event == 2) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[1]"
        } else if (event == 3) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[2]"
        } else if (event == 4) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[3]"
        } else if (event == 5) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[4]"
        } else if (event == 6) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[5]"
        } else if (event == 7) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[6]"
        } else if (event == 8) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[7]"
        } else if (event == 9) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[8]"
        } else if (event == 10) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[9]"
        } else if (event == 11) {
            url = "https://doors-open-ottawa.mybluemix.net/buildings?categoryId=[10]"
        }

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: okhttp3.Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = Gson()
                val buildingFeed = gson.fromJson(body, Array<Building>::class.java)

                if (event == 12) {
                    buildingFeed.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.nameEN }))

                } else if (event == 13) {
                    buildingFeed.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.nameEN }))
                    buildingFeed.reverse()

                }

                runOnUiThread {
                    recyclerview_main.adapter = MainAdapter(buildingFeed)
                }


            }

            override fun onFailure(call: okhttp3.Call?, e: IOException?) {
                println("FAILED")
            }
        })
    }
}
