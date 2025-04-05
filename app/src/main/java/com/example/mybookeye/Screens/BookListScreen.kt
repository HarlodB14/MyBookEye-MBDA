import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import com.example.mybookeye.R
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mybookeye.Controller.NavController
import com.example.mybookeye.Model.Book
import com.example.mybookeye.Viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(navController: NavController) {  // Use your custom NavController
    val viewModel: BookViewModel = viewModel()
    val books = viewModel.books

    LaunchedEffect(Unit) {
        viewModel.loadBooks()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book List") },
                actions = {
                    IconButton(onClick = { navController.navigateToSettings() }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(books.size) { index ->
                BookRow(books[index]) {
                    navController.navigateToBookDetail(books[index].id)
                }
            }
        }
    }
}

@Composable
fun BookRow(book: Book, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book cover image
            Image(
                painter = rememberAsyncImagePainter(
                    model = book.coverUrl,
                    error = androidx.compose.ui.res.painterResource(R.drawable.ic_book_placeholder) // Add a placeholder
                ),
                contentDescription = "Book cover: ${book.title}",
                modifier = Modifier
                    .size(80.dp, 120.dp)
                    .padding(end = 12.dp)
            )

            // Book details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "by ${book.author}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}