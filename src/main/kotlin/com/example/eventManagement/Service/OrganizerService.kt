package com.example.eventManagement.Service

import com.example.eventManagement.Entity.Organizer
import com.example.eventManagement.Repository.EventRepository
import com.example.eventManagement.Repository.OrganizerRepository
import org.springframework.stereotype.Service

@Service
class OrganizerService(private val organizerRepository: OrganizerRepository,private val eventRepository: EventRepository) {

    fun getAllOrganizers(): List<Organizer> {
        val organizer = organizerRepository.findAll()
        if (organizer.isEmpty()) {
            throw NoSuchElementException("No events found")
        }
        return organizer
    }

    fun getOrganizerById(id: Long): Organizer? {
        return organizerRepository.findById(id).orElseThrow {
            NoSuchElementException("Organizer with ID $id not found")
        }
    }

    fun findByNameAndContactInfo(name: String, contactInfo: String?): Organizer? {
        return organizerRepository.findByNameAndContactInfo(name, contactInfo)
    }

    fun createOrganizer(organizer: Organizer): Organizer {
        return organizerRepository.save(organizer)
    }

    fun updateOrganizer(organizer: Organizer): Organizer {
        if (!organizerRepository.existsById(organizer.id)) {
            throw NoSuchElementException("Organizer with ID ${organizer.id} not found.")
        }
        return organizerRepository.save(organizer)
    }

    fun deleteOrganizer(id: Long): Boolean {
        // Check if the organizer exists
        if (!organizerRepository.existsById(id)) {
            throw NoSuchElementException("Organizer with ID $id not found.")
        }

        // Check if there are any events associated with this organizer
        val events = eventRepository.findByOrganizerId(id)
        return if (events.isEmpty()) {
            organizerRepository.deleteById(id)
            true  // Return true on successful deletion
        } else {
            throw Exception("Cannot delete organizer with existing events.")
        }
    }

}
