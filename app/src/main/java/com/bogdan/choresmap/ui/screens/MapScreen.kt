import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val defaultLocation = LatLng(40.7128, -74.0060) // Example location: New York City
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Google Map Composable
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )

        // Home Icon Button overlaid at the bottom center
        IconButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align to the bottom center
                .padding(16.dp) // Add some padding from the screen edges
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Go to Home",
                modifier = Modifier.size(48.dp) // Set size for the icon
            )
        }
    }
}
