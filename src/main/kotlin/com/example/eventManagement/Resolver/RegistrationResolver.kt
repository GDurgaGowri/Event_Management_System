package com.example.eventManagement.Resolver

import com.example.eventManagement.Entity.Registration
import com.example.eventManagement.Service.RegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller

@Controller
@Component
class RegistrationResolver @Autowired constructor(
    private val registrationService: RegistrationService
) {

    @QueryMapping
    fun allRegistrations(): List<Registration> {
        return registrationService.getAllRegistrations()
    }

    @QueryMapping
    fun registrationById(@Argument id: Long): Registration? {
        return registrationService.getRegistrationById(id)
            ?: throw NoSuchElementException("Registration with ID $id not found")
    }

    @QueryMapping
    fun registrationsForEvent(@Argument eventId: Long): List<Registration> {
        return registrationService.getRegistrationsForEvent(eventId)
    }

    @MutationMapping
    fun registerUser(
        @Argument eventId: Long,
        @Argument userName: String,
        @Argument userEmail: String
    ): Registration {
        return try {
            registrationService.createRegistration(eventId, userName, userEmail)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message ?: "Error creating registration")
        }
    }

    @MutationMapping
    fun updateRegistration(
        @Argument id: Long,
        @Argument userName: String?,
        @Argument userEmail: String?
    ): Registration? {
        return registrationService.updateRegistration(id, userName, userEmail)
            ?: throw NoSuchElementException("Registration with ID $id not found")
    }

    @MutationMapping
    fun deleteRegistration(@Argument id: Long): String {
        return if (registrationService.deleteRegistration(id)) {
            "Registration with ID $id has been deleted"
        } else {
            "Registration with ID $id not found"
        }
    }
}
