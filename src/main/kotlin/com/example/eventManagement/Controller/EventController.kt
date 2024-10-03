package com.example.eventManagement.Controller

import com.example.eventManagement.Entity.Event
import com.example.eventManagement.Service.EventService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/events")
class EventController(private val eventService: EventService) {

    @GetMapping("/endpoints")
    fun listEventEndpoints(): String {
        return """
            <html>
                <body>
                    <h2>Event Management API Endpoints</h2>
                    <ul>
                        <li><a href="/events">Retrieve all events (GET)</a></li>
                        <li><a href="/organizers">Retrieve all organizers (GET)</a></li>
                        <li><a href="/locations">Retrieve all locations (GET)</a></li>
                        <li><a href="/graphiql">GraphiQL interface for testing GraphQL queries</a></li>
                    </ul>
                </body>
            </html>
        """.trimIndent()
    }

    @GetMapping
    fun getAllEvents(): List<Event> = eventService.getAllEvents()

    @GetMapping("/{id}")
    fun getEventById(@PathVariable id: Long): Event = eventService.getEventById(id)

    @PostMapping
    fun createEvent(@RequestBody event: Event): Event = eventService.createEvent(event)

    @PutMapping("/{id}")
    fun updateEvent(@PathVariable id: Long, @RequestBody event: Event): Event =
        eventService.updateEvent(id, event)

    @DeleteMapping("/{id}")
    fun deleteEvent(@PathVariable id: Long) = eventService.deleteEvent(id)
}