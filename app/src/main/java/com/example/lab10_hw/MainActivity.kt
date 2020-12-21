package com.example.lab10_hw

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val requestPermission = 1
    override fun onMapReady(googleMap: GoogleMap?) {
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            return
        googleMap?.isMyLocationEnabled = true
        val m1 = MarkerOptions()
        m1.position(LatLng(25.033611, 121.565000))
        m1.title("台北101")
        m1.draggable(true)
        googleMap?.addMarker(m1)

        val m2 = MarkerOptions()
        m2.position(LatLng(25.047924,121.517081))
        m2.title("台北車站")
        m2.draggable(true)
        googleMap?.addMarker(m2)

        val polylineOptions = PolylineOptions()
        polylineOptions.add(LatLng(25.033611,121.565000))
        polylineOptions.add(LatLng(25.032728,121.565137))
        polylineOptions.add(LatLng(25.047924,121.517081))
        polylineOptions.color(Color.BLUE)
        val polyline = googleMap?.addPolyline(polylineOptions)
        polyline?.width = 10F
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.034,121.545),10F))
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            requestPermission ->
                for(result in grantResults){
                    if(result!=PackageManager.PERMISSION_GRANTED)
                        finish()
                    else{
                        val map = supportFragmentManager
                            .findFragmentById(R.id.map) as SupportMapFragment
                        map.getMapAsync(this)
                    }
                }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),requestPermission)
        }else{
            val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            map.getMapAsync(this)
        }

    }
}
