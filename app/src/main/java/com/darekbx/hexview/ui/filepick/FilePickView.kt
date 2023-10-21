package com.darekbx.hexview.ui.filepick

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.darekbx.hexview.R
import com.darekbx.hexview.ui.theme.HexViewTheme

@Composable
fun FilePickView(onFilePicked: (Uri) -> Unit = { }) {
    val filter = arrayOf("*/*")
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                onFilePicked(uri)
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { launcher.launch(filter) },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier
                    .background(Color.White)
                    .size(128.dp)
                    .border(width = 2.dp, Color.Black, shape = CircleShape)
                    .padding(32.dp),
                painter = painterResource(id = R.drawable.ic_open),
                contentDescription = "open",
                tint = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Click to select file", color = Color.Gray)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FilePickViewPreview() {
    HexViewTheme {
        Surface {
            FilePickView()
        }
    }
}