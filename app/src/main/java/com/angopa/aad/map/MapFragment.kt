package com.angopa.aad.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.angopa.aad.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 *  Created by Andres Gonzalez on 02/05/2020.
 */
class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap

    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_map, container)

        (activity?.supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?)?.let {
            it.getMapAsync(this)
        }

        fab = layout.findViewById(R.id.fab) as FloatingActionButton

        return layout.rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        moveToVenue()
    }

    override fun onDestroy() {
        super.onDestroy()
        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.map)
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.remove(fragment!!)?.commit()
    }

    private fun moveToVenue() {
        val cameraPosition = CameraPosition.Builder()
            .target(MAP_DEFAULT_VENUE_TARGET)
            .zoom(MAP_DEFAULT_CAMERA_ZOOM)
            .bearing(MAP_DEFAULT_CAMERA_BEARING)
            .build()

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    companion object {
        val MAP_DEFAULT_VENUE_TARGET = LatLng(32.776011, -96.809095)
        private const val MAP_DEFAULT_CAMERA_ZOOM = 17.33f
        const val MAP_DEFAULT_CAMERA_BEARING = 318.3248f

    }
}