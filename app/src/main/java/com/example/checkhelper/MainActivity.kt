package com.example.checkhelper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkhelper.ui.theme.CheckHelperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckHelperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
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
    val thousand: Int = (number / 1000)
    return intArrayOf(thousand, hundred, decad, unit)
}


fun numberToLettersThousands(number: Int?): String {
    if (number == null) {
        return ""
    }
    if ((number < 0) or (number > 999999)) {
        return "Not implemented"
    }
    if (number == 0) {
        return "zéro" // treat as special case
    }
    val decomposed: IntArray = decompose(number)
    val thousand: Int = decomposed[0]
    val hundred: Int = decomposed[1]
    val decad: Int = decomposed[2]
    val unit: Int = decomposed[3]
    val thousands = decompose(thousand)
    val t5 = thousands[1]
    val t4 = thousands[2]
    val t3 = thousands[3]

    val partB: String = numberToLettersHundreds(hundred, decad, unit)
    var prefixA: String = ""
    var prefixB: String = ""
    if (thousand == 0) {
        prefixB = partB
    } else if (thousand == 1) {
        if (partB.isEmpty()) {
            prefixB = "mille"
        } else {
            prefixB = "mille-" + partB
        }
    } else { //>1000
        if (partB.isEmpty()) {
            prefixB = "-mille"
        } else {
            prefixB = "-mille-" + partB
        }
        prefixA = numberToLettersHundreds(t5, t4, t3)
    }
    return prefixA + prefixB

}


fun numberToLettersHundreds(hundred: Int, decad: Int, unit: Int): String {
    if ((hundred < 0) or (hundred > 9) or (decad < 0) or (decad > 9) or (unit < 0) or (unit > 9)) {
        return "Not implemented"
    }
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


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun Answer() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        val textState =
            rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("13")) }
        val context = LocalContext.current
        val intent = remember {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.academie-francaise.fr/sites/academie-francaise.fr/files/rectifications_1990.pdf")
            )
        }
        SelectionContainer {
            Text(
                numberToLettersThousands(textState.value.text.toIntOrNull()),
                //    style = MaterialTheme.typography.titleLarge
                style = TextStyle(
                    hyphens = Hyphens.Auto, fontSize = 32.sp
                )
            )
        }
        OutlinedTextField(label = { Text(stringResource(R.string.input)) },
            value = textState.value,
            onValueChange = {
                if (it.text.length <= 6) textState.value = it
                            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
            modifier = Modifier.padding(16.dp),
        )
        Text(
            text = stringResource(R.string.footnote), style = MaterialTheme.typography.bodySmall
        )
        Text(text = "(Rectifications de l'orthographe-J.O. du 6-12-1990)",
            color = Color.Blue,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable {
                context.startActivity(intent)
            })
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CheckHelperTheme {
        Answer()
    }
}