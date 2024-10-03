package com.example.eventManagement.Repository

import com.example.eventManagement.Entity.Registration
import com.example.eventManagement.Entity.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegistrationRepository : JpaRepository<Registration, Long> {
    fun findByEventId(eventId: Long): List<Registration>
    fun existsByEventAndUserNameAndUserEmail(event: Event, userName: String, userEmail: String): Boolean
}
