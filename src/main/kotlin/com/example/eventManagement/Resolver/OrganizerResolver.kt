package com.example.eventManagement.Resolver

import com.example.eventManagement.Entity.Organizer
import com.example.eventManagement.Service.OrganizerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller

@Component
@Controller
class OrganizerResolver @Autowired constructor(
    private val organizerService: OrganizerService
) {
    @QueryMapping
    fun allOrganizers(): List<Organizer> {
        return try {
            val organizers = organizerService.getAllOrganizers()
            if (organizers.isEmpty()) {
                throw NoSuchElementException("No organizers found")
            }
            organizers
        } catch (e: Exception) {
            e.message?.let { throw NoSuchElementException(it) } ?: throw NoSuchElementException("Error retrieving organizers")
        }
    }

    @QueryMapping
    fun organizerById(@Argument id: Long): Organizer? {
        return organizerService.getOrganizerById(id) ?: throw NoSuchElementException("Organizer with ID $id not found")
    }

    @MutationMapping
    fun createOrganizer(
        @Argument id: Long,
        @Argument name: String,
        @Argument contactInfo: String?
    ): Organizer {
        // Check for duplicate organizer based on name and contact info
        val existingOrganizer = organizerService.findByNameAndContactInfo(name, contactInfo)
        if (existingOrganizer != null) {
            throw IllegalArgumentException("An organizer with the same name and contact info already exists.")
        }

        val organizer = Organizer(
            id = id,  // Let the database generate the ID
            name = name,
            contactInfo = contactInfo
        )

        return try {
            organizerService.createOrganizer(organizer)
        } catch (e: Exception) {
            throw RuntimeException(e.message ?: "Error creating organizer.")
        }
    }

    @MutationMapping
    fun updateOrganizer(
        @Argument id: Long,
        @Argument name: String?,
        @Argument contactInfo: String?
    ): Organizer {
        val organizer = organizerService.getOrganizerById(id)
            ?: throw NoSuchElementException("Organizer with ID $id not found")

        val updatedOrganizer = organizer.copy(
            name = name ?: organizer.name,
            contactInfo = contactInfo ?: organizer.contactInfo
        )

        return try {
            organizerService.updateOrganizer(updatedOrganizer)
        } catch (e: Exception) {
            throw RuntimeException(e.message ?: "Error updating organizer.")
        }
    }

    @MutationMapping
    fun deleteOrganizer(@Argument id: Long): String {
        return try {
            if (organizerService.deleteOrganizer(id)) {
                "Organizer with ID $id has been deleted."
            } else {
                "Organizer with ID $id not found."
            }
        } catch (e: Exception) {
            e.message ?: "Error deleting organizer."
        }
    }
}
