package com.example.eventManagement.Resolver

import com.example.eventManagement.Entity.User
import com.example.eventManagement.Service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller

@Controller
@Component
class UserResolver @Autowired constructor(
    private val userService: UserService
) {

    @QueryMapping
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }

    @QueryMapping
    fun getUser(@Argument id: Long): User? {
        return userService.getUserById(id)
            ?: throw NoSuchElementException("User with ID $id not found")
    }

    @MutationMapping
    fun createUser(
        @Argument name: String,
        @Argument password: String,
        @Argument role: String
    ): User {
        return try {
            userService.createUser(User(name = name, password = password, role = role))
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(e.message ?: "Error creating user")
        }
    }

    @MutationMapping
    fun updateUser(
        @Argument id: Long,
        @Argument name: String?,
        @Argument password: String?,
        @Argument role: String?
    ): User? {
        return userService.updateUser(id, name, password, role)
            ?: throw NoSuchElementException("User with ID $id not found")
    }

    @MutationMapping
    fun deleteUser(@Argument id: Long): String {
        return if (userService.deleteUser(id)) {
            "User with ID $id has been deleted"
        } else {
            "User with ID $id not found"
        }
    }
}
