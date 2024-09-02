package com.example.recipebook.model

data class Recipe(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val image: String
)
