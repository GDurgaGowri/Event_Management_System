package com.example.eventManagement.Controller

import com.example.eventManagement.Entity.Location
import com.example.eventManagement.Service.LocationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/location")
class LocationController(private val locationService: LocationService) {

    @GetMapping
    fun getAllLocations(): List<Location> = locationService.getAllLocations()

    @GetMapping("/{id}")
    fun getLocationById(@PathVariable id: Long): Location? = locationService.getLocationById(id)

    @PostMapping
    fun createLocation(@RequestBody location: Location): Location = locationService.createLocation(location)

    @PutMapping("/{id}")
    fun updateLocation(@PathVariable id: Long, @RequestBody location: Location): Location? =
        locationService.updateLocation(id, location)

    @DeleteMapping("/{id}")
    fun deleteLocation(@PathVariable id: Long) = locationService.deleteLocation(id)
}
