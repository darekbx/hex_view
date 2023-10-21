package com.darekbx.hexview.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darekbx.hexview.ui.theme.LocalColors

@Preview
@Composable
fun NoPermissionGrantedView(onGrantPermissionsClick: () -> Unit = { }) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Required permissions have not been granted!",
                color = LocalColors.current.red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = onGrantPermissionsClick) {
                Text(text = "Grant permissions")
            }
        }
    }
}