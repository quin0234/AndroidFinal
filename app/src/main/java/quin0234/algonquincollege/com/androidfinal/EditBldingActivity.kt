package quin0234.algonquincollege.com.androidfinal

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.google.gson.Gson
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import okhttp3.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.IOException

/**
 * Created by paulquinnell on 2018-01-10.
 */
class EditBldingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_building)


        val bldInfo = intent.getStringExtra("object")
        val bldInfoObject = Gson().fromJson(bldInfo, Building::class.java)

        val editImgFromFone: ImageView = findViewById(R.id.editImgFromFone)
        val bldingImgURL = "https://doors-open-ottawa.mybluemix.net/" + bldInfoObject.image
        Picasso.with(this).load(bldingImgURL).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(editImgFromFone)

        val editPageTitle: TextView = findViewById(R.id.editingtitle)
        editPageTitle.text = "Editing " + bldInfoObject.nameEN.replace('-', '\n')

        val bldAdd: EditText = findViewById(R.id.editTextAddress)
        bldAdd.setText(bldInfoObject.addressEN, TextView.BufferType.EDITABLE)

        val bldDesEn: EditText = findViewById(R.id.editTextDes)
        bldDesEn.setText(bldInfoObject.descriptionEN, TextView.BufferType.EDITABLE)

        val editNewBld: Switch = findViewById(R.id.editNewBld)
        if (bldInfoObject.isNewBuilding == true) {
            editNewBld.text = "Building is New " + editNewBld.isChecked
        } else {
            editNewBld.text = "Building is Old"
        }
        editNewBld.setOnClickListener{
            editNewBld.text = "Is it a new building -  " + editNewBld.isChecked
        }

        val editSatOpen: EditText = findViewById(R.id.satOpen)
        editSatOpen.setText(bldInfoObject.saturdayStart, TextView.BufferType.EDITABLE)

        val editSatClose: EditText = findViewById(R.id.satClose)
        editSatClose.setText(bldInfoObject.saturdayClose, TextView.BufferType.EDITABLE)

        val editSunOpen: EditText = findViewById(R.id.sunOpen)
        editSunOpen.setText(bldInfoObject.sundayStart, TextView.BufferType.EDITABLE)

        val editSunClose: EditText = findViewById(R.id.sunClose)
        editSunClose.setText(bldInfoObject.sundayClose, TextView.BufferType.EDITABLE)

        val saveEditBlding: Button = findViewById(R.id.editBtnSave)

        saveEditBlding.setOnClickListener {

            val url = "https://doors-open-ottawa.mybluemix.net/buildings/"
            val JSON = MediaType.parse("application/json; charset=utf-8")
            val credential = Credentials.basic("quin0234", "password")

            var newOld: Boolean
            newOld = editNewBld.isChecked == true


            val saving = JSONObject ()
            saving.put ("isNewBuilding", newOld)
            saving.put ("descriptionEN", bldDesEn.text.toString() )
            saving.put ("addressEN", bldAdd.text.toString())
            saving.put ("saturdayStart", editSatOpen.text.toString())
            saving.put ("saturdayClose", editSatClose.text.toString())
            saving.put ("sundayStart", editSunOpen.text.toString())
            saving.put ("sundayClose", editSunClose.text.toString())
            val bodyObject = RequestBody.create (JSON, saving.toString())

            val request = Request.Builder().header("Authorization", credential).url(url + bldInfoObject.buildingId).put(bodyObject).build()
            val client = okhttp3.OkHttpClient()


            client.newCall(request).enqueue(object : Callback{
                override fun onResponse(call: Call?, response: Response?) {
                    runOnUiThread {
                        if (response?.code() != 200) {
                            toast(response.toString())
                        }

                    }
                }
                    override fun onFailure(call: Call?, e: IOException?) {
                        runOnUiThread {
                            toast(e?.message.toString())
                        }
                    }

            })

            val intent = Intent(this@EditBldingActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        val canBldingEdit: Button = findViewById(R.id.editBtnCancel)

        canBldingEdit.setOnClickListener{
            val intent = Intent(this@EditBldingActivity, BuildingDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }


    }
}