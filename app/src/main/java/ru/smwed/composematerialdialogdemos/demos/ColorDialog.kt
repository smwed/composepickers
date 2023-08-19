package ru.smwed.composematerialdialogdemos.demos

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.smwed.composematerialdialogdemos.DialogAndShowButton
import ru.smwed.composematerialdialogs.MaterialDialogButtons
import ru.smwed.composematerialdialogs.color.ARGBPickerState
import ru.smwed.composematerialdialogs.color.ColorPalette
import ru.smwed.composematerialdialogs.color.colorChooser
import ru.smwed.composematerialdialogs.title

/**
 * @brief Color Picker Demos
 */
@Composable
fun ColorDialogDemo() {
    var waitForPositiveButton by remember { mutableStateOf(false) }

    Row(Modifier.padding(8.dp)) {
        Switch(
            checked = waitForPositiveButton,
            onCheckedChange = { waitForPositiveButton = it }
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = "Wait for positive button",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }

    DialogAndShowButton(
        buttonText = "Color Picker Dialog",
        buttons = { defaultColorDialogButtons() }
    ) {
        title("Select a Color")
        colorChooser(colors = ColorPalette.Primary, waitForPositiveButton = waitForPositiveButton) {
            println(it)
        }
    }

    DialogAndShowButton(
        buttonText = "Color Picker Dialog With Sub Colors",
        buttons = { defaultColorDialogButtons() }
    ) {
        title("Select a Sub Color")
        colorChooser(
            colors = ColorPalette.Primary,
            subColors = ColorPalette.PrimarySub,
            waitForPositiveButton = waitForPositiveButton
        ) {
            println(it)
        }
    }

    DialogAndShowButton(
        buttonText = "Color Picker Dialog With Initial Selection",
        buttons = { defaultColorDialogButtons() }
    ) {
        title("Select a Sub Color")
        colorChooser(
            colors = ColorPalette.Primary,
            subColors = ColorPalette.PrimarySub,
            waitForPositiveButton = waitForPositiveButton,
            initialSelection = 5
        ) {
            println(it)
        }
    }

    DialogAndShowButton(
        buttonText = "Color Picker Dialog With RGB Selector",
        buttons = { defaultColorDialogButtons() }
    ) {
        title("Custom RGB")
        colorChooser(
            colors = ColorPalette.Primary,
            subColors = ColorPalette.PrimarySub,
            argbPickerState = ARGBPickerState.WithoutAlphaSelector,
            waitForPositiveButton = waitForPositiveButton
        ) {
            println(it)
        }
    }

    DialogAndShowButton(
        buttonText = "Color Picker Dialog With ARGB Selector",
        buttons = { defaultColorDialogButtons() }
    ) {
        title("Custom ARGB")
        colorChooser(
            colors = ColorPalette.Primary,
            subColors = ColorPalette.PrimarySub,
            argbPickerState = ARGBPickerState.WithAlphaSelector,
            waitForPositiveButton = waitForPositiveButton
        ) {
            println(it)
        }
    }
}

@Composable
private fun MaterialDialogButtons.defaultColorDialogButtons() {
    positiveButton("Select")
    negativeButton("Cancel")
}
