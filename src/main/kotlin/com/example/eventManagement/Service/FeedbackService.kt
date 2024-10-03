package com.example.eventManagement.Service

import com.example.eventManagement.Entity.Feedback
import com.example.eventManagement.Repository.FeedbackRepository
import org.springframework.stereotype.Service

@Service
class FeedbackService(private val feedbackRepository: FeedbackRepository) {

    // Get all feedbacks
    fun getAllFeedbacks(): List<Feedback> {
        val feedback = feedbackRepository.findAll()
        if (feedback.isEmpty()) {
            throw NoSuchElementException("No events found")
        }
        return feedback
        }

    // Get feedback by ID
    fun getFeedbackById(id: Long): Feedback? = feedbackRepository.findById(id).orElseThrow {
        NoSuchElementException("Feedback with ID $id not found")
    }

    // Submit feedback
    fun submitFeedback(feedback: Feedback): Feedback = feedbackRepository.save(feedback)

    // Update feedback
    fun updateFeedback(id: Long, feedback: Feedback): Feedback {
        if (feedbackRepository.existsById(id)) {
            return feedbackRepository.save(feedback.copy(id = id))
        }
        throw NoSuchElementException("Feedback not found")
    }

    // Delete feedback
    fun deleteFeedback(id: Long) {
        if (!feedbackRepository.existsById(id)) {
            throw NoSuchElementException("Feedback with ID $id not found")
        }
        feedbackRepository.deleteById(id)
    }

    // Get feedback for a specific event
    fun getFeedbackForEvent(eventId: Long): List<Feedback> = feedbackRepository.findByEventId(eventId)
}
