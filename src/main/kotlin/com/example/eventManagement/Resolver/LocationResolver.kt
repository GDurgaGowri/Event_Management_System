package com.example.eventManagement.Resolver

import com.example.eventManagement.Entity.Location
import com.example.eventManagement.Service.LocationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller

@Component
@Controller
class LocationResolver @Autowired constructor(
    private val locationService: LocationService
) {
    @QueryMapping
    fun allLocations(): List<Location> {
        return locationService.getAllLocations()
    }

    @QueryMapping
    fun locationById(@Argument id: Long): Location? {
        return locationService.getLocationById(id) ?: throw NoSuchElementException("Location with ID $id not found")
    }

    @MutationMapping
    fun createLocation(
        @Argument id: Long,
        @Argument name: String,
        @Argument address: String?
    ): Location {
        val location = Location(id = id, name = name, address = address)
        return try {
            locationService.createLocation(location)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message ?: "Error creating location")
        }
    }

    @MutationMapping
    fun updateLocation(
        @Argument id: Long,
        @Argument name: String?,
        @Argument address: String?
    ): Location? {
        val existingLocation = locationService.getLocationById(id) ?: throw NoSuchElementException("Location with ID $id not found")
        val updatedLocation = existingLocation.copy(
            name = name ?: existingLocation.name,
            address = address ?: existingLocation.address
        )
        return try {
            locationService.updateLocation(id, updatedLocation)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message ?: "Error updating location")
        }
    }

    @MutationMapping
    fun deleteLocation(@Argument id: Long): String {
        return if (locationService.deleteLocation(id)) {
            "Location with ID $id has been deleted"
        } else {
            "Location with ID $id not found"
        }
    }
}
