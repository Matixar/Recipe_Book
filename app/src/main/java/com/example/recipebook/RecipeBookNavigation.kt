package com.example.recipebook

import androidx.navigation.NavHostController
import com.example.recipebook.RecipeBookDestinationArgs.NAME
import com.example.recipebook.RecipeBookDestinationArgs.RECIPE_ID
import com.example.recipebook.RecipeBookScreens.AI_SCREEN
import com.example.recipebook.RecipeBookScreens.CAMERA_SCREEN
import com.example.recipebook.RecipeBookScreens.HOME_SCREEN
import com.example.recipebook.RecipeBookScreens.MANUAL_INPUT_SCREEN
import com.example.recipebook.RecipeBookScreens.RECIPE_DETAILS_SCREEN

private object RecipeBookScreens {
    const val HOME_SCREEN = "home"
    const val RECIPE_DETAILS_SCREEN = "recipeDetails"
    const val AI_SCREEN = "ai"
    const val MANUAL_INPUT_SCREEN = "manualInput"
    const val CAMERA_SCREEN = "camera"
}

object RecipeBookDestinationArgs {
    const val RECIPE_ID = "recipeId"
    const val NAME = "name"
}

object RecipeBookDestination {
    const val RECIPE_DETAILS = "$RECIPE_DETAILS_SCREEN/{$RECIPE_ID}"
    const val HOME = HOME_SCREEN
    const val AI = AI_SCREEN
    const val MANUAL_INPUT = MANUAL_INPUT_SCREEN
    const val SCRAPE_MANUAL_INPUT = "$MANUAL_INPUT_SCREEN?$NAME={$NAME}"
    const val CAMERA = CAMERA_SCREEN

}

class RecipeBookNavigationActions(private val navController: NavHostController) {
    fun navigateToRecipeDetails(recipeId: Int) {
        navController.navigate("$RECIPE_DETAILS_SCREEN/$recipeId")
    }
    fun navigateToAI() {
        navController.navigate(AI_SCREEN)
    }
    fun navigateToManualInput() {
        navController.navigate(MANUAL_INPUT_SCREEN)
        }
    fun navigateToHome() {
        navController.navigate(HOME_SCREEN)
    }
    fun navigateBack() {
        navController.popBackStack()
    }
    fun navigateToManualInput(name: String) {
        navController.navigate("$MANUAL_INPUT_SCREEN/$name").let {
            "$it?$NAME=$name"
        }
    }
    fun navigateToCamera() {
        navController.navigate(CAMERA_SCREEN)
    }

}