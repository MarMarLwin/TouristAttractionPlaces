package com.mml.travelplanning.views

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.mml.travelplanning.R
import com.mml.travelplanning.Utils
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT>=23)
            Utils.requestAppPermission(this,Utils.myPermissions)
        submitButton.setOnClickListener {
            try{
                if(!isValid())
                return@setOnClickListener

                startActivity(Intent(this,MapsActivity::class.java))

            }catch (ex:Exception)
            {
                Utils.showToastMessage(this,ex.message.toString())
            }
        }
    }

    private fun isValid():Boolean{
        if(nameEditText.text.toString().isEmpty())
        {
            nameInputTextLayout.error=resources.getString(R.string.require_field)
            return false
        }else
            nameInputTextLayout.error=null

        if(emailEditText.text.toString().isEmpty())
        {
            emailInputTextLayout.error=resources.getString(R.string.require_field)
            return false
        }else
            emailInputTextLayout.error=null

        if(!Patterns.EMAIL_ADDRESS.toRegex().matches(emailEditText.text))
        {
            emailInputTextLayout.error=resources.getString(R.string.incorrect_format)
            return false
        }
        return true
    }
}
