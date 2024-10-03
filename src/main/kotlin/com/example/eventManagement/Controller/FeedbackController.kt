package com.example.eventManagement.Controller

import com.example.eventManagement.Entity.Feedback
import com.example.eventManagement.Service.FeedbackService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feedback")
class FeedbackController(private val feedbackService: FeedbackService) {

   
    @GetMapping
    fun getAllFeedbacks(): List<Feedback> = feedbackService.getAllFeedbacks()

    @GetMapping("/{id}")
    fun getFeedbackById(@PathVariable id: Long): Feedback? = feedbackService.getFeedbackById(id)

    
    @PostMapping
    fun submitFeedback(@RequestBody feedback: Feedback): Feedback = feedbackService.submitFeedback(feedback)

    @PutMapping("/{id}")
    fun updateFeedback(@PathVariable id: Long, @RequestBody feedback: Feedback): Feedback =
        feedbackService.updateFeedback(id, feedback)

    @DeleteMapping("/{id}")
    fun deleteFeedback(@PathVariable id: Long): String {
        feedbackService.deleteFeedback(id)
        return "Feedback with ID $id has been deleted."
    }

    @GetMapping("/event/{eventId}")
    fun getFeedbackForEvent(@PathVariable eventId: Long): List<Feedback> =
        feedbackService.getFeedbackForEvent(eventId)
}
