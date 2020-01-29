package com.mml.travelplanning.views

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mml.travelplanning.CustomMapWindowAdapter
import com.mml.travelplanning.PlaceListAdapter
import com.mml.travelplanning.R
import com.mml.travelplanning.Utils
import com.mml.travelplanning.models.AttractionPlaces
import com.mml.travelplanning.viewmodels.PlacesViewModel
import kotlinx.android.synthetic.main.activity_maps.*
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var adapter:PlaceListAdapter
    private  var placeList:List<AttractionPlaces> = listOf()

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(PlacesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

            viewModel.placesList.observe(this, Observer { list ->
                if (list != null)
                    placeList = list
            })
            doneButton.setOnClickListener {showConfimation(totalTxtView.text.toString()) }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        addMarker()
    }

    override fun onBackPressed() {
        showConfimation("Are you sure want to exit?")

    }

    override fun onResume() {
        super.onResume()
        setRecyclerView(placeList)
        adapter.setOnItemClickListener(object :PlaceListAdapter.OnItemClickListener{
            override fun onItemClick(place: AttractionPlaces) {
                totalTxtView.text="${resources.getString(R.string.total)} ${Utils.selectedPlaceList.sumByDouble { x->x.ticketPrice }}"
                if(Utils.selectedPlaceList.firstOrNull()?.currency!=null)
                    totalTxtView.text ="${totalTxtView.text}${Utils.selectedPlaceList.firstOrNull()?.currency}"
            }

        })
    }
    private fun addMarker(){
    try
    {
        for(i in placeList.indices)
        {
            val latlng=LatLng(placeList[i].geolocation.latitude,placeList[i].geolocation.longitude)
            val marker=mMap.addMarker(MarkerOptions().position(latlng).title(placeList[i].place_name)
                .icon(Utils.bitmapDescriptorFromVector(applicationContext,R.drawable.ic_location_red)))

            val adapter=CustomMapWindowAdapter(this)
            mMap.setInfoWindowAdapter(adapter)

            mMap.setOnInfoWindowClickListener {
                if(Utils.selectedPlaceList.find { x->x==it.tag as AttractionPlaces}==null)
                {
                    Utils.selectedPlaceList.add(it.tag as AttractionPlaces)
                    it.setIcon( Utils.bitmapDescriptorFromVector(this,R.drawable.ic_location_green))
                    it.isFlat=false
                }

                else
                {
                    Utils.selectedPlaceList.remove(it.tag as AttractionPlaces)
//                    selectBtn.text= cxt.resources.getString(R.string.select)
                    it.setIcon( Utils.bitmapDescriptorFromVector(this,R.drawable.ic_location_red))
                    it.isFlat=true
                }
                totalTxtView.text="${resources.getString(R.string.total)} ${Utils.selectedPlaceList.sumByDouble { x->x.ticketPrice }}"
                if(Utils.selectedPlaceList.firstOrNull()?.currency!=null)
                    totalTxtView.text ="${totalTxtView.text}${Utils.selectedPlaceList.firstOrNull()?.currency}"
            it.hideInfoWindow()
            }
            marker.tag=placeList[i]
            marker.isFlat=true

        }
        mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        placeList[0].geolocation.latitude,
                        placeList[0].geolocation.longitude
                    ), 14.0f
                )
                )
//        val location = LocationServices.getFusedLocationProviderClient(this).lastLocation
//        location.addOnCompleteListener{
//            if (it.isSuccessful && it.result != null) {
//                val lastLocation = it.result
//                mMap.moveCamera(
//                    CameraUpdateFactory.newLatLngZoom(
//                        LatLng(
//                            lastLocation!!.latitude,
//                            lastLocation.longitude
//                        ), 12.0f
//                    )
//                )
//            }
//        }
    }
    catch(ex:Exception)
    {
        Utils.showToastMessage(this,ex.message.toString())
    }
    }

    private fun setRecyclerView(list: List<AttractionPlaces>) {

        adapter = PlaceListAdapter(list)
        placesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        placesRecyclerView.adapter = adapter
    }

    private fun showConfimation(message:String)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation!")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { _, _ ->
            finish()
            Utils.selectedPlaceList.clear()
        }
        builder.setNegativeButton("CANCEL") { _, _ ->

        }
        builder.setCancelable(false)
        builder.create()
        builder.show()

    }
}
