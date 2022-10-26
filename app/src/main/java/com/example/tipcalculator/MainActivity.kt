package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent() {
            TopContainer()
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        content()
    }
}

//@Preview
@Composable
fun TopContainer(amount: Double = 125.0) {
    Surface(
        color = Color(0xFFDFD2D2),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(CornerSize(12.dp)))
    ) {
        val total = "%.2f".format(amount)
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Total Per Person", style = TextStyle(fontSize = 25.sp))
            Text(
                text = "$$total",
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            )
        }
    }
}

@Preview
@Composable
fun MainContainer() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        shape = RoundedCornerShape(CornerSize(15.dp)),
        border = BorderStroke(1.dp, color = Color.DarkGray)
    ) {
          Column() {
              Text(text = "hello again nigga ...")
              Text(text = "hello again nigga ...")
              Text(text = "hello again nigga ...")
              // todo : make components.kt to create input field
          }
    }
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
//        TopContainer()
    }
}