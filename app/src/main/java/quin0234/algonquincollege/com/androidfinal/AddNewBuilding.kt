package quin0234.algonquincollege.com.androidfinal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

/**
 * Created by paulquinnell on 2018-01-10.
 */
class AddNewBuilding: Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_blding)

        val addBldingBtn: Button = findViewById(R.id.AddBtnSave)

        addBldingBtn.setOnClickListener {
            val intent = Intent(this@AddNewBuilding, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        val canAddBlding: Button = findViewById(R.id.AddBtnCancel)

        canAddBlding.setOnClickListener{
            val intent = Intent(this@AddNewBuilding, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        val getPicBtn: Button = findViewById(R.id.getNewPic)

        getPicBtn.setOnClickListener{
            val intent = Intent(this@AddNewBuilding, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }


    }
}