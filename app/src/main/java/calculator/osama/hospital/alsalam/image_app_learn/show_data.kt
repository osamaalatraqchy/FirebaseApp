package calculator.osama.hospital.alsalam.image_app_learn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_show_data.*

class show_data : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)

        var db = FirebaseDatabase.getInstance().getReference()
        rec.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        var image = ArrayList<image>()


        db.child("image").addValueEventListener(object:ValueEventListener{


            override fun onDataChange(p0: DataSnapshot?) {

                image.clear()

                var id = p0!!.value as HashMap<String,Any>

                for(k in id.keys){

                    var data = id[k] as HashMap<String,Any>

                    image.add (image(

                          data["url"] as String,
                            data["name"] as String,
                            data["des"] as String

                    ))
                }

                var adapter = Adapter(image,this@show_data)
                rec.adapter = adapter

            }


            override fun onCancelled(p0: DatabaseError?) {

            }


        })

    }
}
