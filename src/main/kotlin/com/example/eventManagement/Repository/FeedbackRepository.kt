package com.example.eventManagement.Repository
import com.example.eventManagement.Entity.Feedback
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedbackRepository : JpaRepository<Feedback, Long> {
    fun findByEventId(eventId: Long): List<Feedback>
}
