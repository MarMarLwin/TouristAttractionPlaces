package com.mml.travelplanning

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.mml.travelplanning.models.AttractionPlaces

class Utils {
    companion object{

         var selectedPlaceList:MutableList<AttractionPlaces> = mutableListOf()
        var myPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.VIBRATE,
            Manifest.permission.INTERNET
        )

        fun hasPermission(context: Context, vararg permissions: String): Boolean = permissions.all()
        {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        @TargetApi(Build.VERSION_CODES.M)
        fun requestAppPermission(context: Context, selectPermission: Array<String>) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!hasPermission(context, *myPermissions)) {
                    ActivityCompat.requestPermissions(context as Activity, selectPermission, 1)
                }
            }
        }

        fun showDialog(context: Context?, msg:String): AlertDialog
        {
            val llPadding = 30
            val ll = LinearLayout(context)
            ll.orientation = LinearLayout.HORIZONTAL
            ll.setPadding(llPadding, llPadding, llPadding, llPadding)
            ll.gravity = Gravity.CENTER
            var llParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            llParam.gravity = Gravity.CENTER
            ll.layoutParams = llParam
            llParam =
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            llParam.gravity = Gravity.CENTER
            val tvText = TextView(context)
            tvText.text = msg
            tvText.setTextColor(Color.parseColor("#000000"))
            tvText.textSize = 20f
            tvText.setBackgroundColor(Color.TRANSPARENT)
            tvText.layoutParams = llParam

            ll.addView(tvText)
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmation!")
            builder.setMessage("Your total ticket price is $msg")
            builder.setPositiveButton("YES") { _, _ ->  }
            builder.setNegativeButton("NO") { _, _ -> }
            builder.setCancelable(false)
            builder.setView(ll)
            val dialog = builder.create()
            val window = dialog.window
            if (window != null)
            {
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog.window !!.attributes)
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                dialog.window !!.attributes = layoutParams
            }
            return dialog
        }

        fun showToastMessage(context: Context,msg:String)
        {
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        }

        fun bitmapDescriptorFromVector(context: Context, vector: Int): BitmapDescriptor {
            val vectorDrawable = ContextCompat.getDrawable(context, vector)
            vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
            val bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}