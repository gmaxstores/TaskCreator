package com.example.taskcreator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskcreator.ui.theme.TaskCreatorTheme

/**
 * The main activity of the application, responsible for setting up the UI and navigation.
 */
class MainActivity : ComponentActivity() {

    // Lazily initialize the TaskViewModel.
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskCreatorTheme {
                // Set up the navigation controller.
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Set up the navigation host with all the screens.
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("main") {
                            MainScreen(navController = navController)
                        }
                        composable("create_todo") {
                            CreateTodoScreen(taskViewModel = taskViewModel, navController = navController)
                        }
                        composable("todo_list") {
                            TodoListScreen(taskViewModel = taskViewModel)
                        }
                        composable("profile") {
                            ProfileScreen()
                        }
                    }
                }
            }
        }
    }
}

/**
 * The main screen of the application, which displays the welcome message and navigation buttons.
 *
 * @param navController The navigation controller used to navigate to other screens.
 * @param modifier The modifier to be applied to the layout.
 */
@Composable
fun MainScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Task Creator")
        Button(onClick = { navController.navigate("todo_list") }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Show Todos")
        }
        Button(onClick = { navController.navigate("create_todo") }, modifier = Modifier.padding(top = 8.dp)) {
            Text(text = "Create a Todo")
        }
        Button(onClick = { navController.navigate("profile") }, modifier = Modifier.padding(top = 8.dp)) {
            Text(text = "Profile")
        }
    }
}

/**
 * A preview of the main screen.
 */
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TaskCreatorTheme {
        // The preview of the main screen is disabled because it requires a NavController.
        // MainScreen()
    }
}
