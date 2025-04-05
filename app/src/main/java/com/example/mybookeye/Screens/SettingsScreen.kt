import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mybookeye.Controller.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {  // Use your custom NavController
    val context = navController.context
    val prefs = remember { context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE) }
    var darkMode by remember { mutableStateOf(prefs.getBoolean("darkMode", false)) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {
            Text("Dark Mode")
            Switch(
                checked = darkMode,
                onCheckedChange = {
                    darkMode = it
                    prefs.edit().putBoolean("darkMode", it).apply()
                }
            )
        }
    }
}