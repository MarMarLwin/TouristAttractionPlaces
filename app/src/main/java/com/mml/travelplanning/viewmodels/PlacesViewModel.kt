package com.mml.travelplanning.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mml.travelplanning.models.AttractionPlaces
import com.mml.travelplanning.models.GeoLocation

class PlacesViewModel (application: Application) : AndroidViewModel(application) {
    var placesList = MutableLiveData<List<AttractionPlaces>?>().apply{
        value = mutableListOf(AttractionPlaces(1,"Shwedagon Pagoda","Pha Yar Gyi Ward, Yangon",2000.0,"MMK","4:00 AM-10:00PM",
            "https://www.shwedagonpagoda.com.mm/sites/default/files/styles/slider_mobile/public/slider-1.jpg",GeoLocation(16.798780, 96.149515)),
            AttractionPlaces(2,"National Museum","Pyay Road, Yangon",1000.0,"MMK","9:00 AM-5:00 PM",
                "https://d10vk5dg0puvhi.cloudfront.net/images/destinations/origin/5d09c01b9374e.jpg",GeoLocation(16.789350, 96.142225)),
            AttractionPlaces(3,"The Secretariat","Thein Phyu Road, Yangon", 1000.0,"MMK","9:30 AM-4:30 PM",
                "https://www.cruise-asia.com/wp-content/uploads/2019/05/Secretariate-Office-1175x512.jpg",GeoLocation(16.775534, 96.167147)),
            AttractionPlaces(4,"Sule Pagoda","Sule Pagoda Road, မဟာဗန္ဓုလလမ်း, Yangon",1500.0,"MMK","7:00 AM-5:00 PM",
                "https://asiatravelandleisure.com/images/country-guides/sule-pagoda.jpg",GeoLocation(16.774763, 96.158808)),
            AttractionPlaces(5,"Yangon Zoological Garden","Kan Yeik Tha Rd, Yangon",1000.0,"MMK","7:00 AM-5:00 PM",
                "https://www.seawander.asia/userfiles/images/Tips%20for%20Myanmar%20backpack%20to%20a%20travel%20to%20Yangon%20zoological%20garden%202.jpg",GeoLocation(16.794859, 96.158764)))

    }

}