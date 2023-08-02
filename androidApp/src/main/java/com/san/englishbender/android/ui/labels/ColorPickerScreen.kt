package com.san.englishbender.android.ui.labels

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.san.englishbender.android.ui.common.BaseDialogContent



@Composable
fun ColorPickerScreen(
    onSave: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    BaseDialogContent(height = 500.dp) {
        val controller = rememberColorPickerController()
        var hexCode by remember { mutableStateOf("") }
        var color: Color by remember { mutableStateOf(Color.Black) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

            Spacer(Modifier.height(20.dp))

            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(35.dp),
                controller = controller,
                borderSize = 10.dp,
                borderColor = Color.DarkGray
            )

            Text(text = "#$hexCode", fontSize = 14.sp, color = color)
            Box(
                Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color)) {}

            Row(
                Modifier.fillMaxWidth().padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { onBack() },
                    colors = outlinedButtonColors(
                        contentColor = Color.Black,
                    )
                ) {
                    Text("Back", color = Color.Black)
                }
                OutlinedButton(onClick = { onSave() }) {
                    Text("Save", color = Color.Black)
                }
            }
        }
    }
}