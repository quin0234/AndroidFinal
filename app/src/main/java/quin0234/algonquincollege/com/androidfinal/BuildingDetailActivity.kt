package quin0234.algonquincollege.com.androidfinal

import android.content.Intent
import android.net.Credentials
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.blg_details.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import java.io.IOException
import okhttp3.*

/**
 * Created by paulquinnell on 2018-01-10.
 */
class BuildingDetailActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.blg_details)

        val bldInfo = intent.getStringExtra("object")
        val bldInfoObject = Gson().fromJson(bldInfo, Building::class.java)

        val bldingName: TextView = findViewById(R.id.buildingName)
        bldingName.text = bldInfoObject.nameEN.replace('-', '\n')

        val bldingImg: ImageView = findViewById(R.id.buildingPic)
        val bldingImgURL = "https://doors-open-ottawa.mybluemix.net/" + bldInfoObject.image
        Picasso.with(this).load(bldingImgURL).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(bldingImg)

        val bldDes: TextView = findViewById(R.id.buildingDesEN)
        bldDes.text = bldInfoObject.descriptionEN

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bldingOpeningTime: TextView = findViewById(R.id.bldingopentime)
        bldingOpeningTime.text = "Opening Times\nSaturdays: " + bldInfoObject.saturdayStart.substringAfter(' ') + " to " + bldInfoObject.saturdayClose.substringAfter(' ')

        val editBuildingBtn: Button = findViewById(R.id.editBtn)

        editBuildingBtn.setOnClickListener {
           val intent = Intent (editBuildingBtn.context, EditBldingActivity::class.java)
            intent.putExtra("object", Gson().toJson(bldInfoObject))
            editBuildingBtn.context.startActivity(intent)

        }

        val deleteBuildingBtn: Button = findViewById(R.id.deleteBtn)

        deleteBuildingBtn.setOnClickListener{
            deleteBuildingDialog()
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        val blgInfo = intent.getStringExtra("object")
        val blgInfoObject = Gson().fromJson(blgInfo, Building::class.java)
        mMap = googleMap

        // Add a marker in destination and zoom to 15
        val marker = LatLng(blgInfoObject.latitude.toDouble(), blgInfoObject.longitude.toDouble())
        mMap.addMarker(MarkerOptions().position(marker).title(blgInfoObject.nameEN))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 16.toFloat()))
    }

    fun deleteBuildingDialog() {
        alert("Are you sure you wish to delete this building?","Delete") {
            positiveButton("YES") {
                deleteBuildings()

            }
            negativeButton("NO") {
                toast("Delete Cancelled")
            }
        }.show()
    }

     private fun deleteBuildings() {
        val url = "https://doors-open-ottawa.mybluemix.net/buildings"
        val credential = okhttp3.Credentials.basic("quin0234", "password")
        val getRequest = Request.Builder().header("Authorization", credential).url(url).get().build()
        val client = okhttp3.OkHttpClient()

        client.newCall(getRequest).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = Gson()
                val buildingFeed = gson.fromJson(body, Array<Building>::class.java)

                for (item in buildingFeed) {
                    if (item.nameEN == "quin0234") {
                        val url2 = url + '/' + item.buildingId.toString()
                        val deleteRequest = Request.Builder().header("Authorization", credential).url(url2).delete().build()
                        val client2 = okhttp3.OkHttpClient()
                        client2.newCall(deleteRequest).enqueue(object : Callback {

                            override fun onResponse(call: Call?, response: Response?) {
                                runOnUiThread {
                                    toast("Building Deleted!!")
                                    val intent = Intent(this@BuildingDetailActivity, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    startActivity(intent)
                                }

                            }

                            override fun onFailure(call: Call?, e: IOException?) {
                                runOnUiThread {
                                    toast("That Didnt Work")
                                }
                            }
                        })
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                runOnUiThread {
                    toast("That Didnt Work")
                }

            }
        })
    }
}