package com.example.appdrawer.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.appdrawer.R
import com.example.appdrawer.app.ui.components.appbar.AppBar
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@Composable
fun PhotoScreen(
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
            val imageList = listOf(
                R.drawable.fox,
                R.drawable.happy,
                R.drawable.fist
            )
            HorizontalPagerWithIndicators(deals = imageList)
        }
    }
}

@Composable
fun HorizontalPagerWithIndicators(deals: List<Int>) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column{
        HorizontalPager(
            count = deals.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 5.dp),
            itemSpacing = 5.dp) { page ->
            Column {
                DisplayDeal(deals[page]){
                    setImage(deals[page])
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp, bottom = 10.dp)
                ) {
                    HorizontalPagerIndicator(
                        pageCount = deals.size,
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                val currentPage = pagerState.currentPage
                                val totalPages = deals.size
                                val nextPage =
                                    if (currentPage < totalPages - 1) currentPage + 1 else 0
                                coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
                            }
                    )
                }
            }
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }
                    .collect { currentPage ->
                        pagerState.animateScrollToPage(currentPage)
                    }
            }
        }
    }
}

fun setImage(image: Int){
    val repository: Repository = Repository.getInstance()
    repository.addPhotoData(image)
}

@Composable
fun DisplayDeal(image: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "photo",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}


