package com.example.eventManagement.Entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "location")
data class Location(
    @Id
    val id: Long,  // Removed @GeneratedValue

    @field:NotNull
    @Column(name = "name", nullable = false)
    val name: String,

    @field:NotNull
    @Column(name = "address") // Add this column
    val address: String? = null, // Make it nullable or non-null as per your use case

    @field:NotNull
    @OneToMany(mappedBy = "location", cascade = [CascadeType.ALL], orphanRemoval = true)
    val events: List<Event> = emptyList()
)
