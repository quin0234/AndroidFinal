package quin0234.algonquincollege.com.androidfinal

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bld_main.view.*


/**
 * Created by paulquinnell on 2018-01-10.
 */
class MainAdapter(val buildingFeed: Array<Building>): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return buildingFeed.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        val layoutInflator = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflator.inflate(R.layout.bld_main, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {
        val building = buildingFeed.get(position)
        holder?.view?.buildingNameText?.text = building.nameEN

        val thumbnailIV = holder?.view?.buildingImageView
        val imageURL = "https://doors-open-ottawa.mybluemix.net/" + building.image
        Picasso.with(holder?.view?.context).load(imageURL).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(thumbnailIV)

        holder?.blding = building

    }

}

class CustomViewHolder(val view: View, var blding: Building? = null): RecyclerView.ViewHolder(view) {

    init{
        view.setOnClickListener {
            println("Testing")

            val intent = Intent(view.context, BuildingDetailActivity::class.java)
            intent.putExtra("object", Gson().toJson(blding))
            view.context.startActivity(intent)
        }
    }
}