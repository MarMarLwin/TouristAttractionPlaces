package com.mml.travelplanning

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.mml.travelplanning.models.AttractionPlaces

class CustomMapWindowAdapter(context : Context) : GoogleMap.InfoWindowAdapter
{
    private val cxt = context
    private var listener: OnItemClickListener? = null

    override fun getInfoContents(marker : Marker?) : View?
    {
        val view = LayoutInflater.from(cxt).inflate(R.layout.custom_map_layout, null)

        try
        {
            val name = view.findViewById<TextView>(R.id.nameTextView)
            val address = view.findViewById<TextView>(R.id.addressTxtView)
            val openHr = view.findViewById<TextView>(R.id.openHrTextView)
            val ticketPrice = view.findViewById<TextView>(R.id.priceTextView)
            val currency = view.findViewById<TextView>(R.id.currencyTxtView)
            val selectBtn=view.findViewById<Button>(R.id.selectBtn)

            if (marker?.title == null)
                return null
            if(marker.isFlat)
                selectBtn.text=cxt.resources.getString(R.string.select)
            else
                selectBtn.text=cxt.resources.getString(R.string.unselect)

            name.text = marker.title
            val infoWindowData = marker.tag as AttractionPlaces

            address.text = infoWindowData.address
            openHr.text = infoWindowData.open_hr
            ticketPrice.text = infoWindowData.ticketPrice.toString()
            currency.text = infoWindowData.currency


            selectBtn.setOnClickListener {
                try{
//                    if(Utils.selectedPlaceList.find { x->x==infoWindowData}==null)
//                    {
//                        Utils.selectedPlaceList.add(infoWindowData)
//                        marker.setIcon( Utils.bitmapDescriptorFromVector(cxt,R.drawable.ic_location_green))
//                        selectBtn.text= cxt.resources.getString(R.string.unselect)
//                    }
//
//                    else
//                    {
//                        Utils.selectedPlaceList.remove(infoWindowData)
//                        selectBtn.text= cxt.resources.getString(R.string.select)
//                        marker.setIcon( Utils.bitmapDescriptorFromVector(cxt,R.drawable.ic_location_red))
//                    }


                    listener?.onItemClick(selectBtn)

                }catch (ex:java.lang.Exception)
                {
                    Utils.showToastMessage(cxt,ex.toString())
                }
            }
        }
        catch (ex : Exception)
        {
            Utils.showToastMessage(cxt, ex.toString())
        }
        return view
    }
    interface OnItemClickListener {
        fun onItemClick(view: View)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    override fun getInfoWindow(marker : Marker?) : View?
    {
        return null
    }
}