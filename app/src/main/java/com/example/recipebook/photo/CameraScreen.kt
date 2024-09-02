package com.example.recipebook.photo

import android.net.Uri
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.recipebook.utils.CameraFileUtils.takePicture
import java.util.concurrent.Executors

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    // Executor for background tasks, specifically for taking pictures in this context
    val executor = remember { Executors.newSingleThreadExecutor() }
    // State to hold the URI of the captured image. Initially null, updated after image capture
    val capturedImageUri = remember { mutableStateOf<Uri?>(null) }

    // Camera controller tied to the lifecycle of this composable
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            bindToLifecycle(lifecycleOwner) // Binds the camera to the lifecycle of the lifecycleOwner
        }
    }

    Box {
        // PreviewView for the camera feed. Configured to fill the screen and display the camera output
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PreviewView(ctx).apply {
                    scaleType = PreviewView.ScaleType.FILL_START
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    controller = cameraController // Attach the lifecycle-aware camera controller.
                }
            },
            onRelease = {
                // Called when the PreviewView is removed from the composable hierarchy
                cameraController.unbind() // Unbinds the camera to free up resources
            }
        )

        // Button that triggers the image capture process
        Button(
            onClick = {
                // Calls a utility function to take a picture, handling success and error scenarios
                takePicture(cameraController, context, executor, { uri ->
                    capturedImageUri.value = uri // Update state with the URI of the captured image on success
                }, { exception ->
                    // Error handling logic for image capture failures
                })
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(text = "Take Picture")
        }

    }
}