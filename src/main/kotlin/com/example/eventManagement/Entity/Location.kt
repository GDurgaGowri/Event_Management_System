package com.example.eventManagement.Entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "location")
data class Location(
    @Id
    val id: Long,  

    @field:NotNull
    @Column(name = "name", nullable = false)
    val name: String,

    @field:NotNull
    @Column(name = "address") 
    val address: String? = null, 

    @field:NotNull
    @OneToMany(mappedBy = "location", cascade = [CascadeType.ALL], orphanRemoval = true)
    val events: List<Event> = emptyList()
)
