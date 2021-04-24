package com.hclsample.rajesh.xplore.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.hclsample.rajesh.xplore.MainActivity
import com.hclsample.rajesh.xplore.MainActivity.Companion.latLng
import com.hclsample.rajesh.xplore.R
import com.hclsample.rajesh.xplore.ui.adapter.MapResultRvAdapter


class XploreMapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    var destLat: String? = null
    var destLng: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleData()

    }

    private fun handleData() {
        destLat = arguments?.getString(ResultFragment.BUNDLE_KEY_DEST_LAT)
        destLng = arguments?.getString(ResultFragment.BUNDLE_KEY_DEST_LNG)
    }

    private fun setUpViews() {
        val smf =
            childFragmentManager.findFragmentById(R.id.xplore_map) as SupportMapFragment?
        smf!!.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        setUpViews()
    }

    private var mClusterManager: ClusterManager<ClusterItem>? = null

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mClusterManager = ClusterManager<ClusterItem>(activity, googleMap)

        if (destLat.isNullOrEmpty() && destLng.isNullOrEmpty()) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
            val places = MainActivity.KEY_PLACES_RESPONSE
            places?.let {
                for (place in it) {
                    val position = LatLng(
                        place.geometry.locationA.lat.toDouble(),
                        place.geometry.locationA.lng.toDouble()
                    )
                    mMap.addMarker(MarkerOptions().position(position).title(place.name))
                }
            }
        } else { // directions
            val destLatLng = LatLng(destLat!!.toDouble(), destLng!!.toDouble())
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destLatLng, 12f))
            mMap.addMarker(MarkerOptions().position(latLng!!).title(""))
            mMap.addMarker(MarkerOptions().position(destLatLng).title(""))
            mMap.addPolyline(
                PolylineOptions()
                    .clickable(true)
                    .add(latLng)
                    .add(destLatLng)
            )
        }
    }
}