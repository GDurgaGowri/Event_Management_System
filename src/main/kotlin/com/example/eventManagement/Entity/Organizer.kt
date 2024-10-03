package com.example.eventManagement.Entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "organizer")
data class Organizer(
    @Id
    val id: Long, 

    @field:NotNull
    @Column(nullable = false)
    val name: String,

    @Column(name = "contact_info")
    val contactInfo: String? = null,

    @OneToMany(mappedBy = "organizer", cascade = [CascadeType.ALL], orphanRemoval = true)
    val events: List<Event> = emptyList()
)
