package com.example.taskcreator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

/**
 * The view model for managing tasks.
 */
class TaskViewModel : ViewModel() {

    // A private mutable state flow to hold the list of tasks.
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    // A public state flow to expose the list of tasks to the UI.
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // A counter to generate unique task IDs.
    private var nextId = 1

    /**
     * Adds a new task to the list.
     *
     * @param name The name of the task.
     * @param description The description of the task.
     * @param dateTime The date and time of the task.
     */
    fun addTask(name: String, description: String, dateTime: Date) {
        val newTask = Task(nextId++, name, description, dateTime)
        _tasks.update { it + newTask }
    }

    /**
     * Updates the completion status of a task.
     *
     * @param task The task to be updated.
     * @param isCompleted The new completion status of the task.
     */
    fun onTaskCompleted(task: Task, isCompleted: Boolean) {
        _tasks.update {
            it.map {
                if (it.id == task.id) {
                    it.copy(isCompleted = isCompleted)
                } else {
                    it
                }
            }
        }
    }

    /**
     * Deletes a task from the list.
     *
     * @param task The task to be deleted.
     */
    fun deleteTask(task: Task) {
        _tasks.update { it.filter { it.id != task.id } }
    }
}
