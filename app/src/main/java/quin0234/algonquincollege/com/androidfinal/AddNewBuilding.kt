package quin0234.algonquincollege.com.androidfinal

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.add_new_blding.*
import kotlinx.android.synthetic.main.edit_building.*
import okhttp3.*
import org.jetbrains.anko.toast
import quin0234.algonquincollege.com.androidfinal.R.id.imgFromPhone
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by paulquinnell on 2018-01-10.
 */
class AddNewBuilding: AppCompatActivity(){

    val MY_REQUEST_CAMERA = 10
    val MY_REQUEST_GALLERY = 12


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_blding)

        val newBldingName: EditText = findViewById(R.id.newBldingName)
        val newBldingAdd: EditText = findViewById(R.id.newBldingAddress)
        val addBldingBtn: Button = findViewById(R.id.AddBtnSave)


        val takePicBtn: Button = findViewById(R.id.AddPicPhone)

        takePicBtn.setOnClickListener{
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(callCameraIntent.resolveActivity(packageManager) !=null) {
                startActivityForResult(callCameraIntent, MY_REQUEST_CAMERA)
            }
        }

        val getPicBtn: Button = findViewById(R.id.AddPicGallery)

        getPicBtn.setOnClickListener{
            val callGalleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            callGalleryIntent.type = "image/*"
            if(callGalleryIntent.resolveActivity(packageManager) !=null) {
                startActivityForResult(callGalleryIntent, MY_REQUEST_GALLERY)
            }
        }


        addBldingBtn.setOnClickListener {
            addNewBlding(newBldingName.text.toString(), newBldingAdd.text.toString())
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

        val SavePicBtn: Button = findViewById(R.id.getNewPic)

        SavePicBtn.setOnClickListener{
            uploadBldingPic(newBldingName.text.toString(), newBldingAdd.text.toString())
            val intent = Intent(this@AddNewBuilding, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

    }

    fun addNewBlding ( newBldingName: String, newBldingAddress: String) {
        val MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown: charset-utf-8")

        val url = "https://doors-open-ottawa.mybluemix.net/buildings"
        val credential = Credentials.basic("quin0234", "password")
        val postBody = "{ 'nameEN': '" + newBldingName + "', 'addressEN': '" + newBldingAddress + "' }"
        val request = Request.Builder().header("Authorization", credential).url(url).post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody)).build()
        val client = okhttp3.OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {

                runOnUiThread {
                    toast(response.toString())
                }
        }

            override fun onFailure(call: Call?, e: IOException?) {
                runOnUiThread {
                    toast(e?.message.toString())
                }
            }
        })

    }

    fun uploadBldingPic(newBldingName: String = "", newBldingAddress: String = "") {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val url = "https://doors-open-ottawa.mybluemix.net/buildings/form"

        val bitmap = BitmapFactory.decodeStream(URL("https://statgisesi.files.wordpress.com/2013/10/the-valley-5.jpg").content as InputStream)
        val baos = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)

        val MEDIA_TYPE_JPEG = MediaType.parse("image/JPEG")
        val credential = Credentials.basic("quin0234", "password")
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("nameEN", newBldingName)
                .addFormDataPart("addressEN", newBldingAddress)
                .addFormDataPart("image", "building_pic.jpg",
                        RequestBody.create(MEDIA_TYPE_JPEG, baos.toByteArray()))
                .build()

        val request = Request.Builder().header("Authorization", credential)
                .url(url).post(requestBody)
                .build()

        val client = okhttp3.OkHttpClient()

        // run it on background thread
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                runOnUiThread {
                   toast(response.toString())

                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                runOnUiThread {
                    toast(e?.message.toString())
                }
            }
        })
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            MY_REQUEST_CAMERA -> {
                if(resultCode == Activity.RESULT_OK && data != null) {
                    imgFromFone.setImageBitmap(data.extras.get("data")as Bitmap)

                }
            }
            MY_REQUEST_GALLERY -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    imgFromFone.setImageBitmap(data.extras.get("data") as Bitmap)

                }
            }
            else -> {
                Toast.makeText(this, "Unrecognised Request Code", Toast.LENGTH_SHORT).show()

            }
        }
    }


}