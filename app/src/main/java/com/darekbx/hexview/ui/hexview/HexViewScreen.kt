package com.darekbx.hexview.ui.hexview

import android.graphics.Typeface
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darekbx.hexview.di.MiniDi
import com.darekbx.hexview.ui.theme.HexViewTheme
import com.darekbx.hexview.ui.theme.LocalColors

@Composable
fun HexViewScreen(
    hexViewModel: HexViewModel = MiniDi.hexViewModel,
    uri: Uri
) {
    val data = remember { hexViewModel.data }
    val size by remember { hexViewModel.size }

    var rangeStart by remember { mutableStateOf(-1) }
    var rangeEnd by remember { mutableStateOf(-1) }
    var range by remember { mutableStateOf(IntRange(0, 3)) }

    LaunchedEffect(uri) {
        hexViewModel.openFile(uri)
        hexViewModel.read()
    }

    Column(
        modifier = Modifier
            .background(Color(30, 30, 30, 255))
            .padding(4.dp)
    ) {
        HexDataGrid(
            modifier = Modifier
                .fillMaxSize()
                .weight(1F),
            data = data,
            selection = range
        ) { clickedIndex ->
            if (rangeEnd != -1 && rangeStart != -1) {
                rangeStart = -1
                rangeEnd = -1
                range = IntRange.EMPTY
            } else {
                if (rangeStart == -1) {
                    rangeStart = clickedIndex
                } else if (clickedIndex > rangeStart) {
                    rangeEnd = clickedIndex
                    range = IntRange(rangeStart, rangeEnd)
                }
            }
        }
        Divider(Modifier.fillMaxWidth())
        RawDataGrid(
            modifier = Modifier
                .fillMaxSize()
                .weight(1F),
            data = data,
            selection = range
        ) { clickedIndex ->
        }
        Divider(Modifier.fillMaxWidth())
        ActionBar(size) { hexViewModel.read() }
    }
}

@Composable
private fun ActionBar(size: Int, onLoadClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            modifier = Modifier,
            onClick = onLoadClick
        ) {
            Text(text = "Load next chunk")
        }
        MonospacedText(
            text = "${FormatUtils.formatNumber(size)} bytes",
            color = Color.LightGray
        )
    }
}

@Composable
private fun RawDataGrid(
    modifier: Modifier,
    data: List<Byte>,
    selection: IntRange = IntRange.EMPTY,
    onItemClick: (Int) -> Unit = { }
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 8.dp)
    ) {
        itemsIndexed(data) { index, part ->
            val isSelected = selection.contains(index)
            MonospacedText(
                modifier = Modifier
                    .background(backgroundColor(isSelected))
                    .clickable { onItemClick(index) },
                text = String(byteArrayOf(part)),
                color = foregroundColor(isSelected)
            )
        }
    }
}

@Composable
private fun HexDataGrid(
    modifier: Modifier,
    data: List<Byte>,
    selection: IntRange = IntRange.EMPTY,
    onItemClick: (Int) -> Unit = { }
) {
    val chunkSize = 24 * 4
    var width by remember { mutableStateOf(chunkSize * 6) }
    Box(
        modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                width = (coordinates.size.width / chunkSize) * chunkSize
            }
    ) {
        with(LocalDensity.current) {
            LazyVerticalGrid(
                modifier = Modifier
                    .width(width.toDp())
                    .offset(x = 4.dp),
                columns = GridCells.Adaptive(minSize = 24.dp)
            ) {
                itemsIndexed(data) { index, part ->
                    val isSelected = selection.contains(index)
                    Box(
                        Modifier
                            .offset(x = (-4).dp)
                            .background(backgroundColor(isSelected))
                            .clickable { onItemClick(index) }
                    ) {
                        MonospacedText(
                            modifier = Modifier.offset(x = 4.dp),
                            text = HexUtils.byteToHex(part),
                            color = foregroundColor(isSelected)
                        )
                    }
                    if (index > 0 && (index + 1) % 4 == 0) {
                        GridDivider()
                    }
                }
            }
        }
    }
}

@Composable
private fun foregroundColor(isSelected: Boolean) =
    if (isSelected) LocalColors.current.orange else Color.LightGray

@Composable
private fun backgroundColor(isSelected: Boolean) =
    if (isSelected) Color.Black else Color.Transparent

@Composable
private fun GridDivider() {
    Box(contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .offset(x = 8.dp)
                .background(color = Color.LightGray)
                .height(18.dp)
                .width(1.dp)
        )
    }
}

@Composable
private fun Data(data: ByteArray) {
    Row(
        Modifier
            .height(IntrinsicSize.Max)
    ) {
        MonospacedText(
            modifier = Modifier,
            text = "${HexUtils.bytesToHex(data)} ",
            color = Color.LightGray
        )
        Box(
            Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(color = Color.LightGray)
        )
    }
}

@Composable
private fun Header(range: LongRange) {
    MonospacedText(
        text = range.joinToString(" ") { it.toString().padStart(2, '0') },
        color = Color.Gray
    )
}

@Composable
private fun Offset(value: Long) {
    MonospacedText(
        text = "$value",
        color = Color.Gray
    )
}

@Composable
private fun MonospacedText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = TextStyle(
            fontSize = 12.sp,
            fontFamily = FontFamily(Typeface.MONOSPACE)
        )
    )
}

@Preview
@Composable
private fun GridPreview() {
    HexViewTheme {
        Surface(color = Color(44, 44, 44, 255)) {
            HexDataGrid(
                Modifier
                    .width(300.dp)
                    .height(200.dp),
                data = listOf(
                    25, 50, 44, 46,
                    21, 31, 22, 36,
                    81, 25, 12, 32,
                    21, 42, 67, 12,
                    81, 25, 12, 32,
                    21, 42, 67, 12,
                    10, 66, 41, 99,
                    123, 100, 16, 1,
                    10, 66, 41, 99,
                    123, 100, 16, 1
                ),
                selection = IntRange(4, 11)
            )
        }
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    HexViewTheme {
        Surface(color = Color(44, 44, 44, 255)) {
            Header(range = LongRange(0, 3))
        }
    }
}

@Preview
@Composable
private fun OffsetPreview() {
    HexViewTheme {
        Surface(color = Color(44, 44, 44, 255)) {
            Offset(value = 2210)
        }
    }
}

@Preview
@Composable
private fun DataPreview() {
    HexViewTheme {
        Surface(color = Color(44, 44, 44, 255)) {
            Data(data = byteArrayOf(25, 50, 44, 46))
        }
    }
}