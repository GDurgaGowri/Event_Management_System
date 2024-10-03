package com.example.eventManagement.Service

import com.example.eventManagement.Entity.User
import com.example.eventManagement.Repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {

    fun getAllUsers(): List<User> {
        val users =  userRepository.findAll()
        if (users.isEmpty()) {
            throw NoSuchElementException("No events found")
        }
        return users
    }

    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    @Transactional
    fun createUser(user: User): User {
         return userRepository.save(user)
    }

    @Transactional
    fun updateUser(id: Long, name: String?, password: String?, role: String?): User? {
        val existingUser = userRepository.findById(id).orElse(null) ?: return null

        val updatedUser = existingUser.copy(
            name = name ?: existingUser.name,
            password = password ?: existingUser.password,
            role = role ?: existingUser.role
        )

        return userRepository.save(updatedUser)
    }


    @Transactional
    fun deleteUser(id: Long): Boolean {
        val user = userRepository.findById(id).orElse(null) ?: return false
        userRepository.delete(user)
        return true
    }

}
