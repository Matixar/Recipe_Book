package com.example.recipebook

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipebook.RecipeBookDestinationArgs.IMAGE
import com.example.recipebook.RecipeBookDestinationArgs.RECIPE_ID
import com.example.recipebook.ai.BakingScreen
import com.example.recipebook.home.HomeScreen
import com.example.recipebook.manual.ManualInputScreen
import com.example.recipebook.photo.CameraScreen
import com.example.recipebook.recipedetails.RecipeDetailsScreen

@Composable
fun RecipeBookNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = RecipeBookDestination.HOME,
    navActions: RecipeBookNavigationActions = remember(navController) {
        RecipeBookNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            RecipeBookDestination.HOME
        ) {
            HomeScreen(
                navigateToRecipeDetails = {navActions.navigateToRecipeDetails(0)},
                navigateToAI = { navActions.navigateToAI() },
                navigateToManualInput = { navActions.navigateToManualInput() }
            )
        }
        composable(
            RecipeBookDestination.RECIPE_DETAILS,
            arguments = listOf(
                navArgument(RECIPE_ID) { type = NavType.IntType }
            )
        ) {
            RecipeDetailsScreen(
                recipeId = it.arguments?.getInt(RECIPE_ID) ?: 0
            )
        }
        composable(
            RecipeBookDestination.AI
        ) {
            BakingScreen(
                openCameraCapture = { navActions.navigateToCamera() }
            )
        }
        composable(
            RecipeBookDestination.MANUAL_INPUT
        ) {
            ManualInputScreen()
        }
        composable(
            RecipeBookDestination.CAMERA
        ) {
            CameraScreen(
                onImageCaptured = { navActions.navigateToAIWithImage(it.toString())}
            )
        }
        composable(
            RecipeBookDestination.AI_WITH_CAMERA,
            arguments = listOf(
                navArgument(IMAGE) { type = NavType.StringType }
            )
        ) {
            BakingScreen(
                openCameraCapture = { navActions.navigateToCamera() },
                uri = Uri.parse(it.arguments?.getString(IMAGE))
            )
        }
    }

}