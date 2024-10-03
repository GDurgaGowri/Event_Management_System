package com.example.eventManagement.Service

import com.example.eventManagement.Entity.Event
import com.example.eventManagement.Repository.*
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val locationRepository: LocationRepository,
    private val organizerRepository: OrganizerRepository,
    private val registrationRepository: RegistrationRepository,
    private val feedbackRepository: FeedbackRepository

) {
    fun getAllEvents(): List<Event> {
        val events = eventRepository.findAll()
        if (events.isEmpty()) {
            throw NoSuchElementException("No events found")
        }
        return events
    }

    fun getEventById(id: Long): Event {
        return eventRepository.findById(id).orElseThrow {
            NoSuchElementException("Event with ID $id not found")
        }
    }

    fun createEvent(event: Event): Event {

        require(event.name.isNotBlank()) { "Event name cannot be blank." }
        require(event.date.isAfter(LocalDateTime.now())) { "Event date must be in the future." }

        // Check if Organizer exists
        if (!organizerRepository.existsById(event.organizer.id)) {
            throw NoSuchElementException("Organizer with ID ${event.organizer.id} not found.")
        }

        // Check if Location exists
        if (!locationRepository.existsById(event.location.id)) {
            throw NoSuchElementException("Location with ID ${event.location.id} not found.")
        }

        // Check for duplicate event
        val existingEvent = eventRepository.findByNameAndDateAndLocationAndOrganizer(
            event.name, event.date, event.location, event.organizer
        )
        if (existingEvent != null) {
            throw IllegalArgumentException("An event with the same name, date, location, and organizer already exists.")
        }

        return eventRepository.save(event)
    }


    fun updateEvent(id: Long, updatedEvent: Event): Event {
        if (!eventRepository.existsById(id)) {
            throw NoSuchElementException("Event with ID $id not found")
        }

        // Check if Organizer exists
        if (!organizerRepository.existsById(updatedEvent.organizer.id)) {
            throw NoSuchElementException("Organizer with ID ${updatedEvent.organizer.id} not found")
        }

        // Check if Location exists
        if (!locationRepository.existsById(updatedEvent.location.id)) {
            throw NoSuchElementException("Location with ID ${updatedEvent.location.id} not found")
        }

        // Check for duplicate event
        val existingEvent = eventRepository.findByNameAndDateAndLocationAndOrganizer(
            updatedEvent.name, updatedEvent.date, updatedEvent.location, updatedEvent.organizer
        )
        if (existingEvent != null && existingEvent.id != id) {
            throw IllegalArgumentException("An event with the same name, date, location, and organizer already exists.")
        }

        return eventRepository.save(updatedEvent.copy(id = id))
    }

    fun deleteEvent(id: Long): String {
        // Check if the event exists
        if (!eventRepository.existsById(id)) {
            throw NoSuchElementException("Event with ID $id not found.")
        }

        // Check if there are any registrations or feedback associated with this event
        val registrations = registrationRepository.findByEventId(id)
        val feedbacks = feedbackRepository.findByEventId(id)

        return if (registrations.isEmpty() && feedbacks.isEmpty()) {
            eventRepository.deleteById(id)
            "Event with ID $id has been deleted."
        } else {
            throw Exception("Cannot delete event with existing registrations or feedback.")
        }
    }
}
