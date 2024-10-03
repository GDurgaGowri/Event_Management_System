package com.example.eventManagement.Resolver

import com.example.eventManagement.Entity.Feedback
import com.example.eventManagement.Service.EventService
import com.example.eventManagement.Service.FeedbackService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller

@Controller
@Component
class FeedbackResolver @Autowired constructor(
    private val feedbackService: FeedbackService,
    private val eventService: EventService
) {

    @QueryMapping
    fun allFeedback(): List<Feedback> {
        val feedbackList = feedbackService.getAllFeedbacks()
        return if (feedbackList.isEmpty()) {
            throw NoSuchElementException("No feedbacks available.")
        } else {
            feedbackList
        }
    }

    @QueryMapping
    fun feedbackById(@Argument id: Long): Feedback {
        return feedbackService.getFeedbackById(id) ?: throw NoSuchElementException("Feedback with ID $id not found")
    }

    @MutationMapping
    fun submitFeedback(
        @Argument eventId: Long,
        @Argument comments: String,
        @Argument rating: Int
    ): Feedback {
        if (rating < 1 || rating > 5) {
            throw IllegalArgumentException("Rating must be between 1 and 5.")
        }

        val event = eventService.getEventById(eventId) ?: throw NoSuchElementException("Event with ID $eventId not found")

        val feedback = Feedback(
            event = event,
            comments = comments,
            rating = rating
        )
        return feedbackService.submitFeedback(feedback)
    }

    @MutationMapping
    fun updateFeedback(
        @Argument id: Long,
        @Argument comments: String?,
        @Argument rating: Int?
    ): Feedback {
        val existingFeedback = feedbackService.getFeedbackById(id) ?: throw NoSuchElementException("Feedback with ID $id not found")

        val updatedFeedback = existingFeedback.copy(
            comments = comments ?: existingFeedback.comments,
            rating = rating ?: existingFeedback.rating
        )

        return feedbackService.updateFeedback(id, updatedFeedback)
    }

    @MutationMapping
    fun deleteFeedback(@Argument id: Long): String {
        if (!feedbackService.getFeedbackById(id).let { it != null }) {
            throw NoSuchElementException("Feedback with ID $id not found")
        }

        feedbackService.deleteFeedback(id)
        return "Feedback with ID $id has been deleted"
    }

    @QueryMapping
    fun feedbackForEvent(@Argument eventId: Long): List<Feedback> {
        val feedbackList = feedbackService.getFeedbackForEvent(eventId)
        return if (feedbackList.isEmpty()) {
            throw NoSuchElementException("No feedbacks available for event ID $eventId.")
        } else {
            feedbackList
        }
    }
}
