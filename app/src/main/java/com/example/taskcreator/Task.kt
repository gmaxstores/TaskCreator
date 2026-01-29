package com.example.taskcreator

import java.util.Date

/**
 * Represents a single task in the application.
 *
 * @property id The unique identifier of the task.
 * @property name The name or title of the task.
 * @property description A detailed description of the task.
 * @property dateTime The date and time when the task is due.
 * @property isCompleted A flag indicating whether the task has been completed.
 */
data class Task(
    val id: Int,
    val name: String,
    val description: String,
    val dateTime: Date,
    val isCompleted: Boolean = false
)
