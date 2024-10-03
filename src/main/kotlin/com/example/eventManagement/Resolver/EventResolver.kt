package com.example.eventManagement.Resolver

import com.example.eventManagement.Entity.Event
import com.example.eventManagement.Entity.Location
import com.example.eventManagement.Entity.Organizer
import com.example.eventManagement.Service.EventService
import com.example.eventManagement.Service.LocationService
import com.example.eventManagement.Service.OrganizerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import java.time.LocalDateTime
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

@Controller
@Component
class EventResolver @Autowired constructor(
    private val eventService: EventService,
    private val organizerService: OrganizerService,
    private val locationService: LocationService
) {
    @QueryMapping
    fun allEvents(): List<Event> {
        return eventService.getAllEvents()
    }

    @QueryMapping
    fun eventById(@Argument id: Long): Event? {
        return eventService.getEventById(id)
    }

    @MutationMapping
    fun createEvent(
        @Argument name: String,
        @Argument date: String,
        @Argument location: Location,   // Accept LocationInput
        @Argument organizer: Organizer   // Accept OrganizerInput
    ): Event {
        val location = Location(
            id = location.id,
            name = location.name,
            address = location.address
        )

        val organizerEntity = Organizer(
            id = organizer.id,
            name = organizer.name,
            contactInfo = organizer.contactInfo
        )

        val event = Event(
            name = name,
            date = LocalDateTime.parse(date),
            location = location,
            organizer = organizerEntity
        )

        return try {
            eventService.createEvent(event)
        } catch (e: NoSuchElementException) {
            throw IllegalArgumentException(e.message ?: "Error creating event")
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message ?: "Error creating event")
        }
    }


    @MutationMapping
    fun updateEvent(
        @Argument id: Long,
        @Argument name: String?,
        @Argument date: String?,
        @Argument location: Location?,  // Accept LocationInput
        @Argument organizer: Organizer?   // Accept OrganizerInput
    ): Event {
        val event = eventService.getEventById(id) ?: throw NoSuchElementException("Event with ID $id not found")

        val updatedEvent = event.copy(
            name = name ?: event.name,
            date = date?.let { LocalDateTime.parse(it) } ?: event.date,
            location = location?.let {
                Location(id = it.id, name = it.name, address = it.address)
            } ?: event.location,
            organizer = organizer?.let {
                Organizer(id = it.id, name = it.name, contactInfo = it.contactInfo)
            } ?: event.organizer
        )

        return try {
            eventService.updateEvent(id, updatedEvent)
        } catch (e: NoSuchElementException) {
            throw IllegalArgumentException(e.message ?: "Error updating event")
        }
    }


    @MutationMapping
    fun deleteEvent(@Argument id: Long): String {
        return try {
            eventService.deleteEvent(id)
            "Event with ID $id has been deleted"
        } catch (e: NoSuchElementException) {
            e.message ?: "Error deleting event"
        }
    }




}
