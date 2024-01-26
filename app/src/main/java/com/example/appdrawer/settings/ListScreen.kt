package com.example.appdrawer.settings
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appdrawer.app.ui.components.appbar.AppBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource


@Composable
fun SettingsScreen(
    drawerState: DrawerState,
) {
    Scaffold(
        topBar = {
            AppBar(
                drawerState = drawerState,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SetUp()
        }
    }
}

@Composable
fun SetUp() {
    val context = LocalContext.current
    var dataList by remember { mutableStateOf(emptyList<DataEntity>()) }

    DisposableEffect(Unit) {
        dataList = DataRepo.getInstance(context).getDataList()
        onDispose { }
    }

    MyLazyColumn(dataList, context) { item ->
        DataRepo.getInstance(context).deleteData(item)
        dataList = DataRepo.getInstance(context).getDataList()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyLazyColumn(myData: List<DataEntity>, context: Context, onDelete: (DataEntity) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            itemsIndexed(myData) { _, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                        .combinedClickable(
                            onClick = {
                                val intent = Intent(context, Details::class.java)
                                intent.putExtra("itemId", item.id)
                                context.startActivity(intent)
                            },
                            onLongClick = { onDelete(item) },
                        )
                ) {
                    item.dataImage?.let { painterResource(id = it) }?.let {
                        Image(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier
                                .width(80.dp)
                                .height(80.dp)
                        )
                    }
                    item.dataTitle?.let {
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = it,
                            color = Color.Black
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxWidth()
                        .height(3.dp)
                )
            }
        }
        NavigateToAddData(modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun NavigateToAddData(modifier: Modifier) {
    val context = LocalContext.current
    val fabSize = 80.dp

    FloatingActionButton(
        onClick = {
            val intent = Intent(context, AddDAta::class.java)
            context.startActivity(intent)
        },
        modifier = modifier
            .padding(16.dp)
            .size(fabSize)
            .background(Color.Green, shape = CircleShape)
            .clip(CircleShape)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
}
