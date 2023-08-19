package ru.smwed.composematerialdialogdemos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.smwed.composematerialdialogdemos.demos.BasicDialogDemo
import ru.smwed.composematerialdialogdemos.demos.BasicListDialogDemo
import ru.smwed.composematerialdialogdemos.demos.ColorDialogDemo
import ru.smwed.composematerialdialogdemos.demos.DateTimeDialogDemo
import ru.smwed.composematerialdialogdemos.demos.MultiSelectionDemo
import ru.smwed.composematerialdialogdemos.demos.SingleSelectionDemo
import ru.smwed.composematerialdialogdemos.ui.ComposeMaterialDialogsTheme

/**
 * @brief MainActivity with material dialog samples
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMaterialDialogsTheme {
                DialogDemos()
            }
        }
    }
}

data class DialogSectionData(val title: String, val content: @Composable () -> Unit)

val sections = listOf(
    DialogSectionData("Basic Dialogs") { BasicDialogDemo() },
    DialogSectionData("Basic List Dialogs") { BasicListDialogDemo() },
    DialogSectionData("Single Selection List Dialogs") { SingleSelectionDemo() },
    DialogSectionData("Multi-Selection List Dialogs") { MultiSelectionDemo() },
    DialogSectionData("Date and Time Picker Dialogs") { DateTimeDialogDemo() },
    DialogSectionData("Color Picker Dialogs") { ColorDialogDemo() }
)

/**
 * @brief Collection of Material Dialog Demos
 */
@Composable
fun DialogDemos() {
    val navController = rememberNavController()

    NavHost(navController = navController, "Home") {
        composable("Home") {
            LazyColumn {
                items(sections) {
                    TextButton(
                        onClick = { navController.navigate(it.title) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(MaterialTheme.colors.primaryVariant)
                    ) {
                        Text(
                            it.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center),
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        }

        sections.forEach { dialogSection ->
            composable(dialogSection.title) {
                Column {
                    dialogSection.content()
                }
            }
        }
    }
}
