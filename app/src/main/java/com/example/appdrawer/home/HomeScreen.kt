package com.example.appdrawer.home

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdrawer.about.Repository
import com.example.appdrawer.app.ui.components.appbar.AppBar
import com.example.appdrawer.settings.AddDAta

@Composable
fun HomeScreen(
    drawerState: DrawerState,
) {
    Scaffold(
        topBar = { AppBar(drawerState = drawerState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            setHomeScreenElements()
        }
    }
}

@Composable
fun setHomeScreenElements() {
    val repository: Repository = Repository.getInstance()
    val texts = repository.getDataList()
    val image = repository.getImageId()
    Text(texts[1],
        style = TextStyle(
            fontSize = 40.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            letterSpacing = 2.sp,
            shadow = Shadow(color = Color.Gray),
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier.padding(all = Dp(20.0F)))
    Card(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        image?.let { painterResource(id = it) }?.let {
            Image(
                painter = it,
                contentDescription = "photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
    Text(texts[0],
        style = TextStyle(
            fontSize = 32.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            letterSpacing = 2.sp,
            shadow = Shadow(color = Color.Gray),
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier.padding(all = Dp(20.0F)))
}



