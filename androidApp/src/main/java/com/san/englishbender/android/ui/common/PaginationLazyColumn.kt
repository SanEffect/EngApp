package com.san.englishbender.android.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.log

@Composable
fun PaginationLazyColumn(

) {
    var isLoading by remember { mutableStateOf(false) }
    var currentPage by remember { mutableIntStateOf(1) }

    val listState = rememberLazyListState()

    // Моковый список данных (замените его на свой список)
    val items = remember { mutableStateListOf<String>() }

    // Добавьте элементы в список
    for (i in 1..20) {
        items.add("Item $i")
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        content = {
            items(items.size) { index ->

                val item = items[index]

                ItemRow(item)

                if (index == items.size - 3 && !isLoading) {
                    isLoading = true
                    currentPage++
                    // Загрузите следующую порцию данных здесь
                    // и добавьте их в ваш список items
                    // Например:
                    // items.addAll(loadNextPage(currentPage))

                    log(tag = "LazyColumn") { "add next items" }
                    log(tag = "LazyColumn") { "currentPage: $currentPage" }

                    var lastIndex = items.lastIndex
                    for (i in 1..20) {
                        items.add("Item ${lastIndex++}")
                    }

                    isLoading = false
                }
            }
        }
    )
}

@Composable
fun ItemRow(value: String) {
    Row(Modifier.height(80.dp).padding(8.dp).border(1.dp, Color.DarkGray)) {
        Text(value)
    }
}