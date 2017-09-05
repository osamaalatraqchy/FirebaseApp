package calculator.osama.hospital.alsalam.image_app_learn

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.widget.ProgressBar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_upload_image.*

class Upload_image : AppCompatActivity() {

    
    var table:Uri? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)



        show.setOnClickListener {

            var intent = Intent(this, show_data::class.java)
            startActivity(intent)

        }



        upload.setOnClickListener {

            upload()

        }

        image.setOnClickListener{

            checkP()

        }

    }

///////////////////////////////////////////////////////////////////////
    fun checkP(){

        if(Build.VERSION.SDK_INT >=23){

            if(ActivityCompat.checkSelfPermission(this,

                    android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 10)
                return


            }

        }


        getImage()
    }


    fun getImage(){
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,111)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 111 && data != null && resultCode == RESULT_OK){


             table = data.data
            var colum = arrayOf(MediaStore.Images.Media.DATA)

            var query = contentResolver.query(table, colum,null,null,null)

            query.moveToFirst()

            var columIndex = query.getColumnIndex(colum[0])

            var ImagePath = query.getString(columIndex)
            image.setImageBitmap(BitmapFactory.decodeFile(ImagePath))

        }
    }
/////////////////////////////////////////////////////////////////////////////////////


    fun upload(){

        var image_name = image_name.text.toString()
        var image_des = image_des.text.toString()

        var storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://image-fe588.appspot.com")
        var db = FirebaseDatabase.getInstance().getReference()
        var imagepath = image_name+".jpg"

        var upload = storage.child("image/"+ imagepath)

        var p = ProgressDialog(this)
        p.setTitle("جاري تحميل الصورة")
        p.show()
        
        upload.putFile(table!!).addOnSuccessListener {  taskSnapshot ->

            var uel = taskSnapshot.downloadUrl!!.toString()


            p.dismiss()
            var id = db.push().key


            var table = image(uel,image_name,image_des)

            db.child("image").child(id).setValue(table)


            
            
        }.addOnFailureListener{
            
            p.dismiss()
            
        }

    }


}
