package com.example.taskcreator

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.taskcreator.ui.theme.TaskCreatorTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * A screen for creating a new task.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param taskViewModel The view model for managing tasks.
 * @param navController The navigation controller used to navigate back to the previous screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodoScreen(
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel = viewModel(),
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create a new Todo", modifier = Modifier.padding(bottom = 16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = { showDatePicker = true }) {
            Text(text = selectedDate?.let { formatDate(it) } ?: "Select Date and Time")
        }

        val datePickerState = rememberDatePickerState()
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        datePickerState.selectedDateMillis?.let {
                            val calendar = Calendar.getInstance()
                            calendar.timeInMillis = it
                            showTimePicker(context, calendar) { hour, minute ->
                                calendar.set(Calendar.HOUR_OF_DAY, hour)
                                calendar.set(Calendar.MINUTE, minute)
                                selectedDate = calendar.time
                            }
                        }
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Button(
            onClick = {
                selectedDate?.let {
                    taskViewModel.addTask(name, description, it)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.padding(top = 16.dp),
            enabled = selectedDate != null
        ) {
            Text(text = "Save Task")
        }
    }
}

/**
 * Shows a time picker dialog.
 *
 * @param context The context.
 * @param calendar The calendar instance to initialize the picker with.
 * @param onTimeSet The callback to be invoked when the time is set.
 */
private fun showTimePicker(context: android.content.Context, calendar: Calendar, onTimeSet: (Int, Int) -> Unit) {
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute -> onTimeSet(hourOfDay, minute) },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )
    timePickerDialog.show()
}

/**
 * Formats a given date into a string.
 *
 * @param date The date to be formatted.
 * @return The formatted date string.
 */
private fun formatDate(date: Date): String {
    val sdf = SimpleDateFormat("EEE, MMM d, yyyy 'at' h:mm a", Locale.getDefault())
    return sdf.format(date)
}

/**
 * A preview of the create todo screen.
 */
@Preview(showBackground = true)
@Composable
fun CreateTodoScreenPreview() {
    TaskCreatorTheme {
        // The preview of the create todo screen is disabled because it requires a NavController.
        // CreateTodoScreen()
    }
}
