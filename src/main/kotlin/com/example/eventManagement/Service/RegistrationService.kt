package com.example.eventManagement.Service

import com.example.eventManagement.Entity.Registration
import com.example.eventManagement.Repository.RegistrationRepository
import com.example.eventManagement.Repository.EventRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RegistrationService(
    private val registrationRepository: RegistrationRepository,
    private val eventRepository: EventRepository
) {

    fun getAllRegistrations(): List<Registration> {
        return registrationRepository.findAll().ifEmpty { throw NoSuchElementException("No registrations found") }
    }

    fun getRegistrationById(id: Long): Registration? {
        return registrationRepository.findById(id).orElseThrow {
            NoSuchElementException("Registration with ID $id not found")
        }
    }

    fun getRegistrationsForEvent(eventId: Long): List<Registration> {
        return registrationRepository.findByEventId(eventId)
    }

    fun createRegistration(eventId: Long, userName: String, userEmail: String): Registration {
        val event = eventRepository.findById(eventId).orElseThrow { Exception("Event not found") }

        // Check for existing registration
        if (registrationRepository.existsByEventAndUserNameAndUserEmail(event, userName, userEmail)) {
            throw IllegalArgumentException("User with name $userName and email $userEmail is already registered for this event.")
        }

        val registration = Registration(event = event, userName = userName, userEmail = userEmail)
        return registrationRepository.save(registration)
    }

    fun updateRegistration(id: Long, userName: String?, userEmail: String?): Registration? {
        val registration = registrationRepository.findById(id).orElse(null) ?: return null

        // Check for existing registration with same name and email
        if ((userName != null || userEmail != null) && registrationRepository.existsByEventAndUserNameAndUserEmail(
                registration.event, userName ?: registration.userName, userEmail ?: registration.userEmail)) {
            throw IllegalArgumentException("User with the same name and email is already registered for this event.")
        }

        val updatedRegistration = registration.copy(
            userName = userName ?: registration.userName,
            userEmail = userEmail ?: registration.userEmail
        )
        return registrationRepository.save(updatedRegistration)
    }

    fun deleteRegistration(id: Long): Boolean {
        if (!registrationRepository.existsById(id)) {
            throw NoSuchElementException("Registration with ID $id not found")
        }
        registrationRepository.deleteById(id)
        return true
    }
}
