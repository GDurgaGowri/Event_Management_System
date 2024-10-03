package com.example.eventManagement.Controller

import com.example.eventManagement.Entity.Feedback
import com.example.eventManagement.Service.FeedbackService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feedback")
class FeedbackController(private val feedbackService: FeedbackService) {

    // Get all feedbacks
    @GetMapping
    fun getAllFeedbacks(): List<Feedback> = feedbackService.getAllFeedbacks()

    // Get feedback by ID
    @GetMapping("/{id}")
    fun getFeedbackById(@PathVariable id: Long): Feedback? = feedbackService.getFeedbackById(id)

    // Submit feedback
    @PostMapping
    fun submitFeedback(@RequestBody feedback: Feedback): Feedback = feedbackService.submitFeedback(feedback)

    // Update feedback
    @PutMapping("/{id}")
    fun updateFeedback(@PathVariable id: Long, @RequestBody feedback: Feedback): Feedback =
        feedbackService.updateFeedback(id, feedback)

    // Delete feedback by ID
    @DeleteMapping("/{id}")
    fun deleteFeedback(@PathVariable id: Long): String {
        feedbackService.deleteFeedback(id)
        return "Feedback with ID $id has been deleted."
    }

    // Get feedbacks for a specific event
    @GetMapping("/event/{eventId}")
    fun getFeedbackForEvent(@PathVariable eventId: Long): List<Feedback> =
        feedbackService.getFeedbackForEvent(eventId)
}
