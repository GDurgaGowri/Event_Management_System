package com.example.eventManagement.Controller

import com.example.eventManagement.Entity.Organizer
import com.example.eventManagement.Service.OrganizerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/organizer")
class OrganizerController(private val organizerService: OrganizerService) {

    @GetMapping
    fun getAllOrganizers(): ResponseEntity<List<Organizer>> {
        return ResponseEntity.ok(organizerService.getAllOrganizers())
    }

    @GetMapping("/{id}")
    fun getOrganizerById(@PathVariable id: Long): ResponseEntity<Organizer> {
        val organizer = organizerService.getOrganizerById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(organizer)
    }

    @PostMapping
    fun createOrganizer(@RequestBody organizer: Organizer): ResponseEntity<Organizer> {
        val createdOrganizer = organizerService.createOrganizer(organizer)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrganizer)
    }

    @PutMapping("/{id}")
    fun updateOrganizer(@PathVariable id: Long, @RequestBody organizer: Organizer): ResponseEntity<Organizer> {
        val updatedOrganizer = organizerService.updateOrganizer(organizer) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(updatedOrganizer)
    }

    @DeleteMapping("/{id}")
    fun deleteOrganizer(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            organizerService.deleteOrganizer(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}
