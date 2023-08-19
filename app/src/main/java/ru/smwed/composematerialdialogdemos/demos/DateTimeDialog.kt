package ru.smwed.composematerialdialogdemos.demos

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import ru.smwed.composematerialdialogdemos.DialogAndShowButton
import ru.smwed.composematerialdialogs.MaterialDialogButtons
import ru.smwed.composematerialdialogs.datetime.date.DatePickerDefaults
import ru.smwed.composematerialdialogs.datetime.date.datepicker
import ru.smwed.composematerialdialogs.datetime.time.TimePickerColors
import ru.smwed.composematerialdialogs.datetime.time.TimePickerDefaults
import ru.smwed.composematerialdialogs.datetime.time.timepicker
import java.time.DayOfWeek
import java.time.LocalTime

/**
 * @brief Date and Time Picker Demos
 */
@Composable
fun DateTimeDialogDemo() {
    val purple = remember { Color(0xFF3700B3) }

    val colors: TimePickerColors = if (isSystemInDarkTheme()) {
        TimePickerDefaults.colors(
            activeBackgroundColor = purple.copy(0.3f),
            activeTextColor = Color.White,
            selectorColor = purple,
            inactiveBackgroundColor = Color(0xFF292929)
        )
    } else {
        TimePickerDefaults.colors(
            inactiveBackgroundColor = Color.LightGray,
            activeBackgroundColor = purple.copy(0.1f),
            activeTextColor = purple,
            selectorColor = purple
        )
    }

    val context = LocalContext.current

    DialogAndShowButton(
        buttonText = "Time Picker Dialog",
        buttons = { defaultDateTimeDialogButtons() }
    ) {
        timepicker(colors = colors) {
            println(it.toString())
            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
        }
    }

    DialogAndShowButton(
        buttonText = "Time Picker Dialog With Min/Max",
        buttons = { defaultDateTimeDialogButtons() }
    ) {
        timepicker(
            colors = colors,
            timeRange = LocalTime.of(9, 35)..LocalTime.of(21, 13),
            is24HourClock = false
        ) {
            println(it.toString())
            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
        }
    }

    DialogAndShowButton(
        buttonText = "Time Picker Dialog 24H",
        buttons = { defaultDateTimeDialogButtons() }
    ) {
        timepicker(colors = colors, is24HourClock = true) {
            println(it.toString())
            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
        }
    }

    DialogAndShowButton(
        buttonText = "Time Picker Dialog 24H With Min/Max",
        buttons = { defaultDateTimeDialogButtons() }
    ) {
        timepicker(
            colors = colors,
            timeRange = LocalTime.of(9, 35)..LocalTime.of(21, 13),
            is24HourClock = true
        ) {
            println(it.toString())
            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
        }
    }

    DialogAndShowButton(
        buttonText = "Date Picker Dialog",
        buttons = { defaultDateTimeDialogButtons() }
    ) {
        datepicker(colors = DatePickerDefaults.colors(headerBackgroundColor = Color.Red)) {
            println(it.toString())
        }
    }

    DialogAndShowButton(
        buttonText = "Date Picker Dialog with date restrictions",
        buttons = { defaultDateTimeDialogButtons() }
    ) {
        datepicker(allowedDateValidator = {
            it.dayOfWeek !== DayOfWeek.SATURDAY && it.dayOfWeek !== DayOfWeek.SUNDAY
        }) {
            println(it.toString())
        }
    }
}

@Composable
private fun MaterialDialogButtons.defaultDateTimeDialogButtons() {
    positiveButton("Ok")
    negativeButton("Cancel")
}
