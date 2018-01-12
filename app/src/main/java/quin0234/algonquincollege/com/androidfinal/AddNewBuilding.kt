package quin0234.algonquincollege.com.androidfinal

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import quin0234.algonquincollege.com.androidfinal.R.id.imgFromFone
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
    lateinit var imageFilePath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_blding)

        val newBldingName: EditText = findViewById(R.id.newBldingName)
        val newBldingAdd: EditText = findViewById(R.id.newBldingAddress)
        val addBldingBtn: Button = findViewById(R.id.AddBtnSave)


        val takePicBtn: Button = findViewById(R.id.AddPicPhone)

        takePicBtn.setOnClickListener{
            try {
                val imageFile = createImageFile()
                val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if(callCameraIntent.resolveActivity(packageManager) != null) {
                    val authorities = packageName + ".fileprovider"
                    val imageUri = FileProvider.getUriForFile(this, authorities, imageFile)
                    callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    startActivityForResult(callCameraIntent, MY_REQUEST_CAMERA)
                }
            } catch (e: IOException) {
                Toast.makeText(this, "Could not create file!", Toast.LENGTH_SHORT).show()
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



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            MY_REQUEST_CAMERA -> {
/*                if(resultCode == Activity.RESULT_OK && data != null) {
                    photoImageView.setImageBitmap(data.extras.get("data") as Bitmap)
                }*/
                if (resultCode == Activity.RESULT_OK) {
                    imgFromFone.setImageBitmap(setScaledBitmap())
                }
            }

            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName: String = "JPEG_" + timeStamp + "_"
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if(!storageDir.exists()) storageDir.mkdirs()
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = imageFile.absolutePath
        return imageFile
    }

    fun setScaledBitmap(): Bitmap {
        val imageViewWidth = imgFromFone.width
        val imageViewHeight = imgFromFone.height

        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFilePath, bmOptions)
        val bitmapWidth = bmOptions.outWidth
        val bitmapHeight = bmOptions.outHeight

        val scaleFactor = Math.min(bitmapWidth/imageViewWidth, bitmapHeight/imageViewHeight)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        return BitmapFactory.decodeFile(imageFilePath, bmOptions)

    }

    fun uploadBldingPic(newBldingName: String = "", newBldingAddress: String = "") {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val url = "https://doors-open-ottawa.mybluemix.net/buildings/form"
        println("image" + imageFilePath)
        val bitmap = BitmapFactory.decodeStream(URL("file:///" + imageFilePath).content as InputStream)

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


}