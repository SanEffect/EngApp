@file:JvmName("AddLabelScreenKt")

package com.san.englishbender.android.ui.labels

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.san.englishbender.android.ui.common.BaseDialogContent
import com.san.englishbender.ui.LabelsViewModel
import database.Label
import io.github.aakira.napier.log


@Composable
fun AddLabelScreen(
    labelsViewModel: LabelsViewModel,
    onColorPicker: () -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var hexCode by remember { mutableStateOf("") }
    var color: Color by remember { mutableStateOf(Color.Black) }

    val controller = rememberColorPickerController()


    BaseDialogContent {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = "Back",
                modifier = Modifier,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
//                    fontWeight = FontWeight.ExtraBold
                )
            )
        }

        // --- Name
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            placeholder = { Text("Label name") },
            onValueChange = { name = it }
        )

        // --- Color presets
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(top = 16.dp)
            .border(1.dp, Color.DarkGray)
        ) {
            OutlinedIconButton(onClick = { onColorPicker() }) {
                Icon(Icons.Filled.Colorize, contentDescription = "Color Picker")
            }
        }

        // --- Button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = {

//                val rnd = Random()
//                val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

                labelsViewModel.saveLabel(
                    Label(
                        id = "",
                        name = name,
                        color = ""
                    )
                )
            },
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color.Gray),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray)
        ) {
            Text(
                text = "Add",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
    }
}