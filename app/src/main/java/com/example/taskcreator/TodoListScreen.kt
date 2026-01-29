package com.example.taskcreator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskcreator.ui.theme.TaskCreatorTheme

/**
 * A screen that displays a list of tasks.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param taskViewModel The view model for managing tasks.
 */
@Composable
fun TodoListScreen(modifier: Modifier = Modifier, taskViewModel: TaskViewModel = viewModel()) {
    // Collect the tasks from the view model.
    val tasks by taskViewModel.tasks.collectAsState()

    // Display the tasks in a lazy column.
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(tasks) { task ->
            TodoItem(
                task = task,
                onTaskCompleted = { task, isCompleted -> taskViewModel.onTaskCompleted(task, isCompleted) },
                onTaskDeleted = { task -> taskViewModel.deleteTask(task) }
            )
        }
    }
}

/**
 * A single item in the todo list.
 *
 * @param task The task to be displayed.
 * @param onTaskCompleted A callback to be invoked when the task is marked as completed or not.
 * @param onTaskDeleted A callback to be invoked when the task is deleted.
 */
@Composable
fun TodoItem(
    task: Task,
    onTaskCompleted: (Task, Boolean) -> Unit,
    onTaskDeleted: (Task) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { isChecked -> onTaskCompleted(task, isChecked) }
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = task.name)
                Text(text = task.description)
            }
        }
        IconButton(onClick = { onTaskDeleted(task) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Task")
        }
    }
}

/**
 * A preview of the todo list screen.
 */
@Preview(showBackground = true)
@Composable
fun TodoListScreenPreview() {
    TaskCreatorTheme {
        TodoListScreen()
    }
}
