package quin0234.algonquincollege.com.androidfinal

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson

/**
 * Created by paulquinnell on 2018-01-10.
 */
class EditBldingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_building)

        val bldInfo = intent.getStringExtra("object")
        val bldInfoObject = Gson().fromJson(bldInfo, Building::class.java)

        val editPageTitle: TextView = findViewById(R.id.editingtitle)
        editPageTitle.text = "Editing " + bldInfoObject.nameEN.replace('-', '\n')

        val bldAdd: EditText = findViewById(R.id.editTextAddress)
        bldAdd.setText(bldInfoObject.addressEN, TextView.BufferType.EDITABLE)

        val bldDesEn: EditText = findViewById(R.id.editTextDes)
        bldDesEn.setText(bldInfoObject.descriptionEN, TextView.BufferType.EDITABLE)

        val editSatOpen: EditText = findViewById(R.id.satOpen)
        editSatOpen.setText(bldInfoObject.saturdayStart, TextView.BufferType.EDITABLE)

        val editSatClose: EditText = findViewById(R.id.satClose)


        val editSunOpen: EditText = findViewById(R.id.sunOpen)


        val editSunClose: EditText = findViewById(R.id.sunClose)


        val saveEditBlding: Button = findViewById(R.id.editBtnSave)

        saveEditBlding.setOnClickListener {
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

        val getPicBtn: Button = findViewById(R.id.editBtnSavePic)

        getPicBtn.setOnClickListener{
            val intent = Intent(this@EditBldingActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}