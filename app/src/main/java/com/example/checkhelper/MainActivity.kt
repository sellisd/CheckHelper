package com.example.checkhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.checkhelper.ui.theme.CheckHelperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckHelperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Answer()
                }
            }
        }
    }
}

val units = arrayOf("", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf")
val tensA = arrayOf(
    "dix",
    "onze",
    "douze",
    "treize",
    "quatorze",
    "quinze",
    "seize",
    "dix-sept",
    "dix-huit",
    "dix-neuf"
)
val tensB = arrayOf(
    "",
    "",
    "vingt",
    "trente",
    "quarante",
    "cinquante",
    "soixante",
    "soixante",
    "quatre-vingt",
    "quatre-vingt"
)


fun decompose(number: Int): IntArray {
    val unit: Int = number % 10
    val decad: Int = (number / 10) % 10
    val hundred: Int = (number / 100) % 10
    return intArrayOf(hundred, decad, unit)
}

fun numberToLettersHundreds(number: Int?): String {
    if (number == null) {
        return ""
    }
    if ((number < 0) or (number > 999)) {
        return "Not implemented"
    }
    if (number == 0) {
        return "zéro" // treat as special case
    }
    val decomposed: IntArray = decompose(number)
    val hundred: Int = decomposed[0]
    val decad: Int = decomposed[1]
    val unit: Int = decomposed[2]
    var prefixA = ""
    var prefixB = ""
    if (hundred == 0) {
        prefixA = ""
        prefixB = ""
    } else if ((hundred == 1) and (decad == 0) and (unit == 0)) {
        prefixB = "cent"
    } else if ((hundred in arrayOf(2, 3, 4, 5, 6, 7, 8, 9)) and (decad == 0) and (unit == 0)) {
        prefixB = "cents"
    } else {
        prefixB = "cent-"
    }
    if (hundred in arrayOf(2, 3, 4, 5, 6, 7, 8, 9)) {
        prefixA = units[hundred] + "-"
    }
    return (prefixA + prefixB + numberToLetters(decad, unit))
}

fun numberToLetters(decad: Int, unit: Int): String {
    if ((decad < 0) or (decad > 9) or (unit < 0) or (unit > 9)) {
        return "Not implemented"
    }
    var firstPart = ""
    var connector = ""
    var secondPart = ""

    if (decad == 0) {
        firstPart = units[unit]
    } else if (decad == 1) {
        firstPart = tensA[unit]
    } else if (decad in arrayOf(2, 3, 4, 5, 6, 7, 8, 9)) {
        firstPart = tensB[decad]
    }
    if ((unit != 0) and (decad in arrayOf(2, 3, 4, 5, 6, 8))) {
        secondPart = units[unit]
    } else if (decad in arrayOf(7, 9)) {
        secondPart = tensA[unit]
    }
    if (secondPart == "") {
        connector = ""
    } else if ((unit == 1) and (decad in arrayOf(2, 3, 4, 5, 6, 7))) {
        connector = "-et-"
    } else {
        connector = "-"
    }
    if ((decad == 8) and (unit == 0)) {
        secondPart += "s"
    }
    return (firstPart + connector + secondPart)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Answer() {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val textState = remember { mutableStateOf(TextFieldValue("13")) }
        Text("Enter Number:")
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )
        Text("Nombre en lettres:")
        Text(textState.value.text + ": " + numberToLettersHundreds(textState.value.text.toIntOrNull()))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CheckHelperTheme {
        Answer()
    }
}