import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.mybookeye.Controller.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    bookId: String,
    navController: NavController
) {  // Use your custom NavController
    Scaffold(
        topBar = { TopAppBar(title = { Text("Book Detail") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Book ID: $bookId")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://openlibrary.org$bookId".toUri())
                navController.context.startActivity(intent)
            }) {
                Text("Open in Browser")
            }
        }
    }
}