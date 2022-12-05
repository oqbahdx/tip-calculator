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
import com.example.tipcalculator.util.calculateAmount
import com.example.tipcalculator.util.calculateTotalPerPerson
import com.example.tipcalculator.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           Column {
//               TopContainer()
               BillForm()
           }
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
        color = Color.Blue,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(all = 10.dp)
            .clip(shape = RoundedCornerShape(CornerSize(12.dp)))
    ) {
        val total = "%.2f".format(amount)
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Total Per Person", style = TextStyle(fontSize = 25.sp, color = Color.White))
            Text(
                text = "$$total",
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.ExtraBold,color = Color.White)
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
    val splitState = remember {
        mutableStateOf(1)
    }
    val percentage = (sliderValue.value * 100).toInt()
    val tipAmount = remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(CornerSize(5.dp)),
        border = BorderStroke(1.dp, color = Color.DarkGray)
    ) {

        Column(modifier = Modifier.padding(12.dp)) {
            TopContainer(amount = totalPerPersonState.value)
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
            if (validateState) {
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
                    if(splitState.value >1)
                    splitState.value --
                    totalPerPersonState.value = calculateTotalPerPerson(totalBillState.value.toDouble(),splitState.value,percentage)
                })
                Text(
                    text = "${splitState.value}",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(start = 10.dp, end = 10.dp),
                )
                RoundIconButton(imageVector = Icons.Default.Add, onclick = {
                    Log.d("icon", " add clicked ")
                    splitState.value ++
                    totalPerPersonState.value = calculateTotalPerPerson(totalBillState.value.toDouble(),splitState.value,percentage)
                })


            }
            Row(
                modifier = Modifier.padding(5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Tip",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(160.dp))
                Text(
                    text = "$ ${tipAmount.value}",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "% $percentage",
                    style = TextStyle(fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Slider(modifier = modifier.padding(start = 10.dp , end = 10.dp),

                    value = sliderValue.value, onValueChange = { newValue ->
                    sliderValue.value = newValue
                        tipAmount.value = calculateAmount(totalBillState.value.toDouble(),percentage)
                        totalPerPersonState.value = calculateTotalPerPerson(totalBillState.value.toDouble(),splitState.value,percentage)
                    Log.d("slider", "value : $newValue")
                }, steps = 10)
            }
            } else {
                Box() {}
            }
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