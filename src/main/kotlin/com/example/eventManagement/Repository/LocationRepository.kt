package com.example.eventManagement.Repository

import com.example.eventManagement.Entity.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<Location, Long> {
    fun existsByNameAndAddress(name: String, address: String?): Boolean
    fun findByNameAndAddress(name: String, address: String?): Location?
}
