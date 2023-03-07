package br.senai.sp.jandira.bmicalc

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bmicalc.calcs.bmiCalculate
import br.senai.sp.jandira.bmicalc.calcs.getBmiClassification
import br.senai.sp.jandira.bmicalc.calcs.getBmiClassificationColors
import br.senai.sp.jandira.bmicalc.model.Client
import br.senai.sp.jandira.bmicalc.model.Product
import br.senai.sp.jandira.bmicalc.ui.theme.BMICalcTheme
import java.time.LocalDate
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val p = Product()
//        p.id = 100
//        p.name = "Mouse"
//        p.price = 230.0
//
//        var x = p.addName()
//        var y = p.listProducts()
//
//        val c = Client(
//            id = 100,
//            name = "Pedro",
//            birthDay = LocalDate.of(1999, 5, 13)
//        )

        setContent {
            BMICalcTheme {
                CalculatorScreen()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorScreen() {

    var weightState = rememberSaveable {
        mutableStateOf("")
    }

    var heightState = rememberSaveable {
        mutableStateOf("")
    }

    var bmiState = rememberSaveable {
        mutableStateOf("")
    }

    var bmiClassificationState = rememberSaveable {
        mutableStateOf("")
    }

    var context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
        ) {
            //HEADER
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Image(
                    painter = painterResource(id = R.drawable.bmi),
                    contentDescription = "",
                    modifier = Modifier.size(120.dp)
                )
                Text(
                    text = stringResource(id = R.string.tittle),
                    fontSize = 30.sp,
                    color = Color.Blue,
                    letterSpacing = 8.sp
                )
            }
            //FORM
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.weight_label),
                    modifier = Modifier.padding(bottom = 8.dp)

                )
                OutlinedTextField(
                    value = weightState.value,
                    onValueChange = {
                        weightState.value = it
                        Log.i("ds2t", it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text(
                    text = stringResource(id = R.string.height_label),
                    modifier = Modifier.padding(
                        bottom = 8.dp, top = 16.dp
                    )

                )
                OutlinedTextField(
                    value = heightState.value,
                    onValueChange = {
                        heightState.value = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        var bmi = bmiCalculate(
                            weight = weightState.value.toDouble(),
                            height = heightState.value.toDouble()
                        )
                        bmiState.value = bmi.toString()
                        bmiClassificationState.value =
                            getBmiClassification(bmi, context)
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(Color(34, 175, 65))
                ) {
                    Text(
                        text = stringResource(id = R.string.button_text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
            //FOOTER
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = if (bmiState.value.isEmpty()) Color(
                        59,
                        150,
                        243,
                        255
                    ) else getBmiClassificationColors(bmiState.value.toDouble()),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.your_score),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = String.format("%.2f", if(bmiState.value.isEmpty()) 0.0 else bmiState.value.toDouble()),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = bmiClassificationState.value,
                            fontSize = 18.sp,
                            color = Color.White
                        )

                        Row() {
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = stringResource(id = R.string.reset))
                            }
                            Spacer(modifier = Modifier.width(48.dp))
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = stringResource(id = R.string.share))
                            }

                        }
                    }
                }
            }
        }
    }
}