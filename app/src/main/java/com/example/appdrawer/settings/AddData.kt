package com.example.appdrawer.settings
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.appdrawer.R
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.example.appdrawer.app.ui.theme.ColorBlueIceberg

class AddDAta : ComponentActivity() {
    private var editedItem: DataEntity? = null
    private var id: Int? = null
    private var rate: Float = 0f
    private var color: Color? = null
    private lateinit var selectedItem: String
    private lateinit var desc: String
    private lateinit var imageTitle: String
    private val categories = listOf("Car", "Motorcycle", "Yacht")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = ColorBlueIceberg),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ){
                val editId = intent.getIntExtra("EDIT_ITEM_ID", -1)

                if(editId != -1){
                    id = editId
                    val dataRepo = DataRepo.getInstance(LocalContext.current)
                    editedItem = dataRepo.getDataById(id!!.toLong())
                }

                makeText()
                colorPicker()
                RateItem()
                DropdownMenuBox()
                saveButton()
            }
        }
    }

    @Composable
    fun makeText() {
        var title by remember { mutableStateOf(TextFieldValue(editedItem?.dataTitle ?: "")) }
        var description by remember { mutableStateOf(TextFieldValue(editedItem?.dataDesc ?: "")) }

        Column(
            modifier = Modifier
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = title,
                label = { Text(text = "Enter title") },
                onValueChange = {
                    title = it
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = description,
                label = { Text(text = "Enter description") },
                onValueChange = {
                    description = it
                }
            )
        }

        imageTitle = title.text
        desc = description.text
    }

    @Composable
    fun RateItem(){
        var myRating by remember { mutableIntStateOf(editedItem?.rating?.toInt() ?: 3) }

        rate = myRating.toFloat()
        RatingBar(
            currentRating = myRating,
            onRatingChanged = { myRating = it
            }
        )
    }

    @Composable
    fun RatingBar(
        maxRating: Int = 5,
        currentRating: Int,
        onRatingChanged: (Int) -> Unit,
        starsColor: Color = Color.Yellow
    ) {
        Row {
            for (i in 1..maxRating) {
                Icon(
                    imageVector = if (i <= currentRating) Icons.Filled.Star
                    else Icons.Filled.StarOutline,
                    contentDescription = null,
                    tint = if (i <= currentRating) starsColor
                    else Color.Unspecified,
                    modifier = Modifier
                        .clickable { onRatingChanged(i) }
                        .padding(4.dp)
                )
            }
        }
    }

    @Composable
    fun colorPicker() {
        val controller = rememberColorPickerController()
        editedItem?.color?.let { Color(it) }?.let { controller.setWheelColor(it) }

        Column(
            modifier = Modifier
                .padding(all = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AlphaTile(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    controller = controller
                )
            }
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp),
                controller = controller,
                onColorChanged = {}
            )
        }
        color = controller.selectedColor.value
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DropdownMenuBox() {
        val context = LocalContext.current
        var expanded by remember { mutableStateOf(false) }
        var selectedText by remember { mutableStateOf(editedItem?.category ?: categories[0]) }

        Box {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .padding(20.dp)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                selectedItem = selectedText
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun saveButton(){
        val context = LocalContext.current

        ElevatedButton(onClick = {
            saveContent(context)
            finish()
        }) {
            Text("Save")
        }
    }

    private fun saveContent(context: Context){
        val dataRepo = DataRepo.getInstance(context = context)
        var image = Pair("Car", R.drawable.images)
        when (selectedItem) {
            "Motorcycle" -> {
                image = Pair("Motorcycle", R.drawable.motor)
            }
            "Yacht" -> {
                image = Pair("Yacht", R.drawable.yacht)
            }
        }
        color?.let {
            DataEntity(
                id,
                dataImage = image.second,
                dataTitle = imageTitle,
                dataDesc = desc,
                category = image.first,
                dataDetailImage = image.second,
                phoneNumber = "123-456-789",
                rating = rate,
                color = it.toArgb())
        }?.let { dataRepo.addData(it) }
    }
}


