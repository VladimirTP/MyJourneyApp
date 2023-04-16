package com.example.myjourneyapp.ui.main.fragments.location

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.myjourneyapp.R
import com.google.android.gms.location.*
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class LocationFragment : Fragment() {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }
    private val mapView by lazy {
        requireView().findViewById<MapView>(R.id.mapview)
    }

    private lateinit var imageProvider: ImageProvider

    private val callbacks = mutableListOf<LocationCallback>()

    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    val point = Point(location.latitude, location.longitude)
                    mapView.map.move(
                        CameraPosition(
                            point,
                            15.0f,
                            0.0f,
                            0.0f
                        ),
                        Animation(Animation.Type.SMOOTH, 0F),
                        null
                    )
                    mapView.map.mapObjects.addPlacemark(
                        point,
                        imageProvider
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION,
                false
            ) || permissions.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                false
            ) -> {
                startLocationUpdates()
            }
            else -> {}
        }
    }

    private val tapListener = MapObjectTapListener { obj, _ ->
        if (obj is PlacemarkMapObject) {
            val bottomSheetDialog = BottomSheetDialog()
            bottomSheetDialog.show(childFragmentManager, "")
            true
        } else false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MapKitFactory.setApiKey("7c6e6531-24b5-4317-b523-f5815829bf04")
        MapKitFactory.initialize(requireContext())
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<ImageButton>(R.id.button_find_location)

        button.setOnClickListener {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            callbacks.add(locationCallback)
        }

        val cafeLocationPoint = Point(53.925342, 27.508184)
        val markerBitmap =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_location_marker)?.toBitmap()
        imageProvider = ImageProvider.fromBitmap(markerBitmap)

        val cafeMarker = mapView.map.mapObjects.addPlacemark(cafeLocationPoint, imageProvider)
        cafeMarker.addTapListener(tapListener)
    }

    override fun onStop() {
        super.onStop()
        callbacks.clear()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            createLocationRequest(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun createLocationRequest(): LocationRequest {
        val timeInterval = 60000L
        return LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, timeInterval
        ).build()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}