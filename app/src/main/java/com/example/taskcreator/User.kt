package com.example.taskcreator

/**
 * Represents a user of the application.
 *
 * @property name The name of the user.
 * @property picture The path or URL to the user's profile picture.
 */
data class User(
    val name: String,
    val picture: String // Assuming the picture is a URL or a path to a local file
)
