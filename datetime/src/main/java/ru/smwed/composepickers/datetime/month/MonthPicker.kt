package ru.smwed.composepickers.datetime.month

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.smwed.composepickers.MaterialDialogScope
import ru.smwed.composepickers.datetime.R
import ru.smwed.composepickers.datetime.date.DatePickerColors
import ru.smwed.composepickers.datetime.date.DatePickerDefaults
import ru.smwed.composepickers.datetime.date.DatePickerState
import ru.smwed.composepickers.datetime.util.isSmallDevice
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * @brief A date picker body layout
 *
 * @param initialDate date to be shown to the user when the dialog is first shown.
 * Defaults to the current date if this is not set
 * @param today date to be circled when the dialog is shown.
 * Defaults to the current date if this is not set
 * @param yearRange the range of years the user should be allowed to pick from
 * @param waitForPositiveButton if true the [onDateChange] callback will only be called when the
 * positive button is pressed, otherwise it will be called on every input change
 * @param onDateChange callback with a LocalDateTime object when the user completes their input
 * @param allowedDateValidator when this returns true the date will be selectable otherwise it won't be
 */
@Composable
fun MaterialDialogScope.monthpicker(
    initialDate: LocalDate = LocalDate.now(),
    today: LocalDate = LocalDate.now(),
    title: String = "SELECT MONTH",
    colors: DatePickerColors = DatePickerDefaults.colors(),
    yearRange: IntRange = IntRange(1900, 2100),
    waitForPositiveButton: Boolean = true,
    allowedDateValidator: (LocalDate) -> Boolean = { true },
    locale: Locale = Locale.getDefault(),
    onDateChange: (LocalDate) -> Unit = {}
) {
    val monthPickerState = remember {
        DatePickerState(initialDate, today, colors, yearRange, dialogState.dialogBackgroundColor!!)
    }

    MonthPickerImpl(title = title, state = monthPickerState, allowedDateValidator, locale)

    if (waitForPositiveButton) {
        DialogCallback { onDateChange(monthPickerState.selected) }
    } else {
        DisposableEffect(monthPickerState.selected) {
            onDateChange(monthPickerState.selected)
            onDispose { }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun MonthPickerImpl(
    title: String,
    state: DatePickerState,
    allowedDateValidator: (LocalDate) -> Boolean,
    locale: Locale
) {

    Column(Modifier.fillMaxWidth()) {
        CalendarHeader(title, state)
        val viewDate = remember(state.selected) { LocalDate.of(state.selected.year, state.selected.month, 1) }

        Column {
            CalendarViewHeader(viewDate, state)

            Box(modifier = Modifier.height(336.dp)) {
                androidx.compose.animation.AnimatedVisibility(
                    state.yearPickerShowing,
                    modifier = Modifier
                        .zIndex(0.7f)
                        .clipToBounds(),
                    enter = slideInVertically(initialOffsetY = { -it }),
                    exit = slideOutVertically(targetOffsetY = { -it })
                ) {
                    YearPicker(viewDate, state)
                }

                MonthPicker(viewDate, state, allowedDateValidator, locale)
            }
        }
    }
}

@Composable
private fun YearPicker(
    viewDate: LocalDate,
    state: DatePickerState,
) {
    val gridState = rememberLazyGridState(viewDate.year - state.yearRange.first)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = gridState,
        modifier = Modifier.background(state.dialogBackground)
    ) {
        itemsIndexed(state.yearRange.toList()) { _, yearNum ->
            val selected = remember { yearNum == viewDate.year }
            YearPickerItem(year = yearNum, selected = selected, colors = state.colors) {
                state.selected = state.selected.withYear(yearNum)
                state.yearPickerShowing = false
            }
        }
    }
}

@Composable
private fun YearPickerItem(
    year: Int,
    selected: Boolean,
    colors: DatePickerColors,
    onClick: () -> Unit
) {
    Box(Modifier.size(88.dp, 52.dp), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(72.dp, 36.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colors.dateBackgroundColor(selected).value)
                .clickable(
                    onClick = onClick,
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                year.toString(),
                style = TextStyle(
                    color = colors.dateTextColor(selected).value,
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
private fun CalendarViewHeader(
    viewDate: LocalDate,
    state: DatePickerState
) {

    val arrowDropUp = painterResource(id = R.drawable.baseline_arrow_drop_up_24)
    val arrowDropDown = painterResource(id = R.drawable.baseline_arrow_drop_down_24)

    Box(
        Modifier
            .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
            .height(24.dp)
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxHeight()
                .align(Alignment.CenterStart)
                .clickable(onClick = { state.yearPickerShowing = !state.yearPickerShowing })
        ) {
            Text(
                text = "${viewDate.year}",
                modifier = Modifier
                    .paddingFromBaseline(top = 16.dp)
                    .wrapContentSize(Alignment.Center),
                style = TextStyle(fontSize = 14.sp, fontWeight = W600),
                color = state.colors.calendarHeaderTextColor
            )

            Spacer(Modifier.width(4.dp))
            Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                Icon(
                    if (state.yearPickerShowing) arrowDropUp else arrowDropDown,
                    contentDescription = "Year Selector",
                    tint = state.colors.calendarHeaderTextColor
                )
            }
        }

    }
}

@Composable
private fun CalendarHeader(title: String, state: DatePickerState) {

    Box(
        Modifier
            .background(state.colors.headerBackgroundColor)
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(start = 24.dp, end = 24.dp)) {
            Text(
                text = title,
                modifier = Modifier.paddingFromBaseline(top = if (isSmallDevice()) 24.dp else 32.dp),
                color = state.colors.headerTextColor,
                style = TextStyle(fontSize = 12.sp)
            )

            Spacer(Modifier.height(if (isSmallDevice()) 8.dp else 16.dp))
        }
    }
}


@Composable
private fun MonthPicker(
    viewDate: LocalDate,
    state: DatePickerState,
    allowedDateValidator: (LocalDate) -> Boolean,
    locale: Locale
) {
    val gridState = rememberLazyGridState(viewDate.monthValue)
    val months = remember {
        val l = mutableListOf<String>()

        if (locale.language == "ru") {
            l.add("январь")
            l.add("февраль")
            l.add("март")
            l.add("апрель")
            l.add("май")
            l.add("июнь")
            l.add("июль")
            l.add("август")
            l.add("сентябрь")
            l.add("октябрь")
            l.add("ноябрь")
            l.add("декабрь")
        } else {
            for (montNum in 1..12) l.add(viewDate.withMonth(montNum).format(DateTimeFormatter.ofPattern("MMMM", locale)))
        }
        l.toList()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = gridState,
        modifier = Modifier.background(state.dialogBackground)
    ) {
        itemsIndexed(months) { index, monthName ->

            val monthNum = index + 1

            val selected = remember(state.selected) { monthNum == state.selected.monthValue }
            val enabled = allowedDateValidator(viewDate.withMonth(monthNum))
            val isToday = remember(state.today) { monthNum == state.today.monthValue }

            MonthPickerItem(
                monthName = monthName,
                selected = selected,
                isToday = isToday,
                enabled = enabled,
                colors = state.colors,
                onClick = { state.selected = state.selected.withMonth(monthNum) }
            )
        }
    }
}

@Composable
private fun MonthPickerItem(
    monthName: String,
    selected: Boolean,
    isToday: Boolean,
    enabled: Boolean,
    colors: DatePickerColors,
    onClick: () -> Unit
) {
    Box(Modifier.size(88.dp, 52.dp), contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(80.dp, 36.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colors.dateBackgroundColor(selected).value)
                .border(BorderStroke(1.dp, colors.dateBorderColor(isToday = isToday).value), CircleShape)
                .clickable(
                    onClick = { if (enabled) onClick() },
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .alpha(if (enabled) ContentAlpha.high else ContentAlpha.disabled),
                text = monthName,
                style = TextStyle(
                    color = colors.dateTextColor(selected).value,
                    fontSize = 18.sp
                )
            )
        }
    }
}