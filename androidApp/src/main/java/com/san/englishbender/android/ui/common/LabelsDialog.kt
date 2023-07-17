package com.san.englishbender.android.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.san.englishbender.core.extensions.toColor
import com.san.englishbender.domain.entities.Label


//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelsDialog(
    labels: List<Label>,
    onDismissRequest: () -> Unit,
    onLabelSelected: (labels: List<Label>) -> Unit
) {

//    var selectedLabels: List<RecordLabelUI> by remember { mutableListOf(listOf()) }

    AlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier
                .width(250.dp),
//                .wrapContentWidth()
//                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp),
                    text = "Labels",
                    fontSize = 16.sp
                )

                LazyColumn(
                    modifier = Modifier.heightIn(0.dp, 200.dp)
                ) {
                    items(labels.size) { index ->
                        val label = labels[index]
                        val name = label.name
//                        val color = label.color.toColor
                        val color = Color.White

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                                .background(color, shape = RoundedCornerShape(4.dp))
                                .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                                .clickable {
                                    onLabelSelected(listOf(label))
                                }
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                ),
                                text = name,
                                fontSize = 12.sp
                            )
                            Icon(
                                Icons.Outlined.Edit,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(end = 8.dp)
                                    .clickable { }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        //your onclick code
                    },
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray)
                ) {
                    Text(
                        text = "Create a new label",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }

                TextButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Close",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
