package com.example.eventManagement.Repository

import com.example.eventManagement.Entity.Organizer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganizerRepository : JpaRepository<Organizer, Long> {
    fun findByNameAndContactInfo(name: String, contactInfo: String?): Organizer?
}
