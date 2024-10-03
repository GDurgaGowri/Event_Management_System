package com.example.eventManagement.Entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "registrations")
data class Registration(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    val event: Event,

    @Column(name = "user_name", nullable = false)
    val userName: String,

    @Column(name = "user_email", nullable = false)
    val userEmail: String,

    @Column(name = "registration_date", nullable = false)
    val registrationDate: LocalDate = LocalDate.now()
)
