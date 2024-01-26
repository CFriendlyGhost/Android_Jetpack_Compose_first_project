package com.example.appdrawer.about

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appdrawer.app.ui.components.appbar.AppBar


@Composable
fun TextScreen(
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
            NameText()
        }
    }
}

@Preview
@Composable
fun NameText() {
    var nameValue by remember { mutableStateOf(TextFieldValue("")) }
    var invitationValue by remember { mutableStateOf(TextFieldValue("")) }
    var repository: Repository
    val localContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nameValue,
            label = { Text(text = "Enter Your Name") },
            onValueChange = {
                nameValue = it
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = invitationValue,
            label = { Text(text = "Enter invitation text") },
            onValueChange = {
                invitationValue = it
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        ElevatedButton(onClick = {
            if(nameValue.text.isNotEmpty() && invitationValue.text.isNotEmpty()){
                repository = Repository.getInstance()
                repository.clearData()
                repository.addTextData(nameValue.text, invitationValue.text)
            }
            else{
                Toast.makeText(
                    localContext,
                    "You need to type invitation text and name!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        ) {
            Text("Set text")
        }
    }
}