package com.mml.travelplanning

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mml.travelplanning.models.AttractionPlaces
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_layout.view.*

class PlaceListAdapter(placeList: List<AttractionPlaces>): RecyclerView.Adapter<PlaceListAdapter.PlaceViewholder>()  {
    private var placeList = placeList
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout, parent, false)
        return PlaceViewholder(itemView)
    }

    override fun onBindViewHolder(holder: PlaceViewholder, position: Int) {
        holder.bindItem(placeList[position])
        val num = position + 1
        holder.itemView.numTxtView.text = "${num}."
    }

    interface OnItemClickListener {
        fun onItemClick(place: AttractionPlaces)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    inner class PlaceViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener{
                val position=adapterPosition
                if(position!=RecyclerView.NO_POSITION)
                {
                    if(Utils.selectedPlaceList.find { x->x==placeList[position]}==null)
                    {
                        Utils.selectedPlaceList.add(placeList[position])
                        itemView.cardView.setCardBackgroundColor(Color.LTGRAY)
                    }
                    else
                    {
                        Utils.selectedPlaceList.remove(placeList[position])
                        itemView.cardView.setCardBackgroundColor(Color.WHITE)
                    }
                    listener?.onItemClick(placeList[position])

                }
            }
        }
        fun bindItem(place: AttractionPlaces) {
//            val numTxtView = itemView.findViewById<TextView>(R.id.numTxtView)
            val nameTxtView = itemView.findViewById<TextView>(R.id.placeNameTxtView)
            val addressTxtView = itemView.findViewById<TextView>(R.id.addressTxtView)
            val openHrTxtView = itemView.findViewById<TextView>(R.id.openHrTxtView)
            val priceTxtView = itemView.findViewById<TextView>(R.id.ticketPriceTxtView)
            val currencyTxtView = itemView.findViewById<TextView>(R.id.currencyTxtView)
            val photoView=itemView.findViewById<ImageView>(R.id.photoImgView)

            nameTxtView.text= place.place_name
            addressTxtView.text=place.address
            openHrTxtView.text=place.open_hr
            priceTxtView.text=place.ticketPrice.toString()
            currencyTxtView.text=place.currency
            Picasso.get()
                .load(place.url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(photoView)
        }
        }

    override fun getItemCount(): Int {
        return placeList.size
    }

}

