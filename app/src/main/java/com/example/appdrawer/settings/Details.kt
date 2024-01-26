package com.example.appdrawer.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdrawer.app.ui.theme.ColorBlueIceberg

class Details : ComponentActivity() {
    private var itemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            itemId = intent.getIntExtra("itemId", -1)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ColorBlueIceberg),
            ) {
                setData(itemId.toLong())
                addSwitches()
            }
        }
    }

    @Composable
    fun setData(id: Long){
        val dataRepo = DataRepo.getInstance(context = LocalContext.current)
        val item = dataRepo.getDataById(id)

        Column(
            modifier = Modifier
                .background(ColorBlueIceberg),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (item != null) {
                item.dataTitle?.let {
                    item.dataDesc?.let {
                            it1 -> item.category?.let { it2 -> makeText(it, it1, it2) } }
                }
            }
            if (item != null) {
                item.color?.let { insertPhoto(image = item.dataImage, it) }
            }
            if (item != null) {
                RateItem(item.rating)
            }
        }
    }

    @Composable
    fun makeText(title: String, description: String, category: String) {

        Column(
            modifier = Modifier
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = title,
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    shadow = Shadow(color = Color.Gray),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(all = Dp(15.0F)))
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = description,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(all = Dp(15.0F)))
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Category: $category",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    shadow = Shadow(color = Color.Gray),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(all = Dp(15.0F)))
        }
    }

    @Composable
    fun insertPhoto(image: Int?, color: Int){
        Card(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            CardColors(containerColor = Color(color),
                contentColor = Color(color),
                disabledContainerColor = Color(color),
                disabledContentColor = Color(color))
        ) {
            image?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = "photo",
                    modifier = Modifier
                        .height(230.dp)
                        .width(230.dp)
                )
            }
        }
    }

    @Composable
    fun RateItem(rating: Float?) {
        if (rating != null) {
            RatingBar(
                currentRating = rating.toInt(),
            )
        }
    }

    @Composable
    fun RatingBar(
        maxRating: Int = 5,
        currentRating: Int,
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
                        .padding(4.dp)
                )
            }
        }
    }

    @Preview
    @Composable
    fun addSwitches(){
        val context = LocalContext.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    finish()
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 3.dp, minHeight = 3.dp)
            ) {
                Icon(
                    Icons.Filled.ArrowBackIos,
                    contentDescription = "back",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = {
                    val intent = Intent(context, AddDAta::class.java)
                    intent.putExtra("EDIT_ITEM_ID", itemId)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .defaultMinSize(minWidth = 3.dp, minHeight = 3.dp)
            ) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "edit",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

    }
}