package com.example.recipebook.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipebook.model.Recipe
import kotlin.text.contains
import kotlin.text.lowercase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToRecipeDetails: (Int) -> Unit,
    navigateToAI: () -> Unit,
    navigateToManualInput: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val recipes = remember { mutableStateListOf<Recipe>() } // Replace with your actual recipe data

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Recipe Book") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CategoryFilter(selectedCategory) { selectedCategory = it }

            SearchBar(searchText) { searchText = it }

            Button(onClick = { /* Handle internet import */ }) {
                Text("Import from Internet")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(onClick = { navigateToAI() }) {
                    Text("Import using AI")
                }

                Button(onClick = { navigateToManualInput() }) {
                    Text("Manual Input")
                }
            }

            RecipeList(recipes, searchText, selectedCategory)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search recipes...") }
    )
}

@Composable
fun CategoryFilter(selectedCategory: String?, onCategorySelect: (String?) -> Unit) {
    val categories = listOf("Breakfast", "Lunch", "Dinner", "Dessert") // Replace with your actual categories
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelect(if (selectedCategory == category) null else category) },
                label = { Text(category) }
            )
        }
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>, searchText: String, selectedCategory: String?) {
    val filteredRecipes = recipes.filter { recipe ->
        recipe.name.lowercase().contains(searchText.lowercase()) &&
                (selectedCategory == null || recipe.category == selectedCategory)
    }

    LazyColumn {
        items(filteredRecipes) { recipe ->
            RecipeCard(recipe)
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    // Design your recipe card layout here
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Text(recipe.name, style = MaterialTheme.typography.headlineSmall)
            // Add more recipe details (image, ingredients, etc.)
        }
    }
}