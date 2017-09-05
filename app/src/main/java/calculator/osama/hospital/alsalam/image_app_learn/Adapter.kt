package calculator.osama.hospital.alsalam.image_app_learn

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class Adapter(private val image:ArrayList<image>, val context:Context): RecyclerView.Adapter<Adapter.viewholder>(){

    override fun onBindViewHolder(holder: viewholder, position: Int) {



        var image:image = image[position]


        Glide.with(context).load(image.url).into(holder.img)
        holder.name.text = image.name
        holder.des.text = image.des
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): viewholder {

        var view = LayoutInflater.from(view.context).inflate(R.layout.cart,view, false)

        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return image.size
    }


    class viewholder(item:View):RecyclerView.ViewHolder(item){
        var img = item.findViewById<ImageView>(R.id.v_img) as ImageView
        var name = item.findViewById<TextView>(R.id.v_name) as TextView
        var des = item.findViewById<TextView>(R.id.v_des) as TextView
    }
}