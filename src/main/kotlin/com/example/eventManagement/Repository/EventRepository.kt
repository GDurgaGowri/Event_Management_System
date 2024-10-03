package com.example.eventManagement.Repository

import com.example.eventManagement.Entity.Event
import com.example.eventManagement.Entity.Location
import com.example.eventManagement.Entity.Organizer
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface EventRepository : JpaRepository<Event, Long> {
    fun findByNameAndDateAndLocationAndOrganizer(
        name: String,
        date: LocalDateTime,
        location: Location,
        organizer: Organizer
    ): Event?
    fun findByOrganizerId(organizerId: Long): List<Event>
    fun findByLocationId(locationId: Long): List<Event>
}
