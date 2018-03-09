package me.rozkmin.livetram

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import me.rozkmin.livetram.mapview.MapActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, MapActivity::class.java))
    }
}
