package br.senai.sp.jandira.bmicalc.calcs

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import br.senai.sp.jandira.bmicalc.R
import kotlin.math.pow

fun bmiCalculate(weight: Double, height: Double): Double {
    return weight / (height / 100).pow(2)
}

fun getBmiClassification(bmi: Double, context: Context): String {

    return if (bmi <= 18.5) {
        context.getString(R.string.under_weight)
    } else if (bmi > 18.5 && bmi < 25.0) {
        context.getString(R.string.normal_weight)
    } else if (bmi >= 25.0 && bmi < 30.0) {
        context.getString(R.string.over_weight)
    } else if (bmi >= 30.0 && bmi < 40.0) {
        context.getString(R.string.obesity)
    } else {
        context.getString(R.string.morbid_obesity)
    }

}

fun getBmiClassificationColors(bmi: Double): Color {

    return if (bmi <= 18.5) {
        Color(255,152,0,255)
    } else if (bmi > 18.5 && bmi < 25.0) {
        Color(76,175,80,255)
    } else if (bmi >= 25.0 && bmi < 30.0) {
        Color(255,152,0,255)
    } else if (bmi >= 30.0 && bmi < 40.0) {
        Color(255,67,54,255)
    } else {
        Color(255,67,54,255)
    }
}