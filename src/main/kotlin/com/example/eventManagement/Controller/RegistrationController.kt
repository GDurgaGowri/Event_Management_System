package com.example.eventManagement.Controller

import com.example.eventManagement.Entity.Registration
import com.example.eventManagement.Service.RegistrationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/registration")
class RegistrationController(private val registrationService: RegistrationService) {

    @GetMapping
    fun getAllRegistrations(): ResponseEntity<List<Registration>> {
        return try {
            val registrations = registrationService.getAllRegistrations()
            ResponseEntity.ok(registrations)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{id}")
    fun getRegistrationById(@PathVariable id: Long): ResponseEntity<Registration> {
        val registration = registrationService.getRegistrationById(id)
        return if (registration != null) {
            ResponseEntity.ok(registration)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/event/{eventId}")
    fun getRegistrationsForEvent(@PathVariable eventId: Long): ResponseEntity<List<Registration>> {
        val registrations = registrationService.getRegistrationsForEvent(eventId)
        return if (registrations.isNotEmpty()) {
            ResponseEntity.ok(registrations)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createRegistration(
        @RequestParam("eventId") eventId: Long,
        @RequestParam("userName") userName: String,
        @RequestParam("userEmail") userEmail: String
    ): ResponseEntity<Registration> {
        return try {
            val registration = registrationService.createRegistration(eventId, userName, userEmail)
            ResponseEntity.ok(registration)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(null)  // Return 400 Bad Request with the error message
        } catch (e: Exception) {
            ResponseEntity.notFound().build()  // Return 404 Not Found if the event doesn't exist
        }
    }

    @PutMapping("/{id}")
    fun updateRegistration(
        @PathVariable id: Long,
        @RequestParam("userName", required = false) userName: String?,
        @RequestParam("userEmail", required = false) userEmail: String?
    ): ResponseEntity<Registration> {
        return try {
            val updatedRegistration = registrationService.updateRegistration(id, userName, userEmail)
            if (updatedRegistration != null) {
                ResponseEntity.ok(updatedRegistration)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(null)  // Return 400 Bad Request if there is an issue with the request
        }
    }

    @DeleteMapping("/{id}")
    fun deleteRegistration(@PathVariable id: Long): ResponseEntity<String> {
        return try {
            registrationService.deleteRegistration(id)
            ResponseEntity.ok("Registration with ID $id has been deleted")
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()  // Return 404 Not Found if the registration does not exist
        }
    }
}
