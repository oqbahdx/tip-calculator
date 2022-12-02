package com.example.tipcalculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.components.InputField
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import com.example.tipcalculator.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//        TopContainer()
            MainContainer()
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

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContainer() {
    BillForm() { billAmt ->
        Log.d("AMT", billAmt)

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier, onValChange: (String) -> Unit = {}) {


    val totalBillState = remember {
        mutableStateOf("")
    }
    val validateState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val sliderValue = remember {
        mutableStateOf(0f)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(CornerSize(5.dp)),
        border = BorderStroke(1.dp, color = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            InputField(
                valueState = totalBillState,
                labelId = "Enter the bill",
                enabled = true,
                onActions = KeyboardActions() {
                    if (!validateState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                },

                isSingleLine = true
            )
//            if (validateState) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "split",
                        style = TextStyle(fontSize = 22.sp),
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(120.dp))
                    RoundIconButton(imageVector = Icons.Default.Remove, onclick = {
                        Log.d("icon", " minus clicked ")
                    })
                    Text(
                        text = "1",
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = 10.dp, end = 10.dp),
                    )
                    RoundIconButton(imageVector = Icons.Default.Add, onclick = {
                        Log.d("icon", " add clicked ")
                    })


                }
            Row( modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.Start){
                Text(text = "Tip",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(160.dp))
                Text(text = "%33.00",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically))
            }
            Spacer(modifier = Modifier.height(20.dp))
           Column(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
               Text(text = "%33.00",
                   style = TextStyle(fontSize = 20.sp))
               Spacer(modifier = Modifier.height(10.dp))
               Slider(value = sliderValue.value, onValueChange = {newValue ->
                   Log.d("slider", "value : $newValue")
               })
           }
//            } else {
//                Box() {}
//            }
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