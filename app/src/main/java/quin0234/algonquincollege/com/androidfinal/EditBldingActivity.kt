package quin0234.algonquincollege.com.androidfinal

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

/**
 * Created by paulquinnell on 2018-01-10.
 */
class EditBldingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.edit_building)


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

        val getPicBtn: Button = findViewById(R.id.getEditedPic)

        getPicBtn.setOnClickListener{
            val intent = Intent(this@EditBldingActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}