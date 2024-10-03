package com.example.eventManagement.Service

import com.example.eventManagement.Entity.Location
import com.example.eventManagement.Repository.EventRepository
import com.example.eventManagement.Repository.LocationRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class LocationService(private val locationRepository: LocationRepository,private val eventRepository: EventRepository) {

    fun getAllLocations(): List<Location> {
        val locations = locationRepository.findAll()
        if (locations.isEmpty()) {
            throw NoSuchElementException("No locations found")
        }
        return locations
    }

    fun getLocationById(id: Long): Location? {
        return locationRepository.findById(id).orElseThrow {
            NoSuchElementException("Location with ID $id not found")
        }
    }

    fun createLocation(location: Location): Location {
        // Check for existing location
        if (locationRepository.existsByNameAndAddress(location.name, location.address)) {
            throw IllegalArgumentException("A location with the same name and address already exists.")
        }
        return locationRepository.save(location)
    }

    fun updateLocation(id: Long, updatedLocation: Location): Location {
        if (!locationRepository.existsById(id)) {
            throw NoSuchElementException("Location with ID $id not found")
        }

        // Check for existing location with the same name and address
        val existingLocation = locationRepository.findByNameAndAddress(updatedLocation.name, updatedLocation.address)
        if (existingLocation != null && existingLocation.id != id) {
            throw IllegalArgumentException("A location with the same name and address already exists.")
        }

        return locationRepository.save(updatedLocation.copy(id = id)) // Ensure the id is preserved
    }

    fun deleteLocation(id: Long): Boolean {
        // Check if the location exists
        if (!locationRepository.existsById(id)) {
            throw NoSuchElementException("Location with ID $id not found.")
        }

        // Check if there are any events associated with this location

        val events = eventRepository.findByLocationId(id)
        return if (events.isEmpty()) {
            locationRepository.deleteById(id)
            true
        } else {
            throw Exception("Cannot delete location with existing events.")
        }
    }
}
