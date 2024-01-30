package com.san.englishbender.android.ui.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.san.englishbender.android.ui.common.DialogHeader
import com.san.englishbender.android.ui.common.EBOutlinedButton


@Composable
fun ColorPickerScreen(
    onSave: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val controller = rememberColorPickerController()
    var hexCode by remember { mutableStateOf("") }
    var color: Color by remember { mutableStateOf(Color.Black) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DialogHeader(
            title = "Color Picker",
            onClick = onBack
        )

        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                color = colorEnvelope.color
                hexCode = colorEnvelope.hexCode
            }
        )

        Spacer(Modifier.height(12.dp))

        Row(Modifier.border(1.dp, Color.DarkGray, RoundedCornerShape(6.dp))) {
            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp),
                controller = controller,
            )
        }

        Text(text = "#$hexCode", fontSize = 14.sp)
        Box(
            Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color)
        ) {}

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            EBOutlinedButton(
                text = "Add",
                onClick = {
                    onSave("#$hexCode")
                }
            )
        }
    }
}