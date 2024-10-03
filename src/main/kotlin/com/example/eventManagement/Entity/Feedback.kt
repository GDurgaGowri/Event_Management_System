package com.example.eventManagement.Entity

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDateTime

@Entity
@Table(name = "feedback")
data class Feedback(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    val event: Event?,

    @Column(nullable = false, name = "comments")
    val comments: String,

    @Min(1)
    @Max(5)
    @Column(nullable = false, name = "rating")
    val rating: Int,
    @Column(nullable = true, name = "feedback_date")
    val feedbackDate: LocalDateTime = LocalDateTime.now()
)
