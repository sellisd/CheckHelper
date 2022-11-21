package com.example.checkhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
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

// rules to do
//  plural for cent and vingt in some cases
//  numbers larger than 1000
//
val units = arrayOf("zero", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf")
val tensA = arrayOf("dix", "onze", "douze", "treize", "quatorze", "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf")
val tensB = arrayOf("","","vingt", "trente", "quarante", "cinquante", "soixante", "soixante", "quatre-vingt", "quatre-vingt")
val higherOrder = arrayOf("cent", "mille", "million", "milliard", "billion")



fun dashOrNothing(word:String):String{
    if(word.isEmpty()){
        return("")
    }else if (word == "un"){
        return("-et-" + word)
    }
    else{
        return("-" + word)
    }
}

fun numberToLettersHundreds(number:Int?):String{
    if(number == null){
        return("")
    }
    when(number){
        0 -> return("zéro")
        in 1..99 -> return(numberToLetters(number))
        in 100..199 -> return(higherOrder[0] + dashOrNothing(numberToLetters(number-100*(number/100))))
        in 200..999 -> return(units[number/100] + dashOrNothing(higherOrder[0] + dashOrNothing(numberToLetters(number-100*(number/100)))))
    }
    return("Not implemented")
}

fun numberToLetters(number:Int):String{
    if((number<0) or (number >99)){
        return "Not implemented"
    }
    val decad:Int = number/10
    val unit:Int = number%10
    var firstPart = ""
    var connector = ""
    var secondPart = ""
    if(decad == 0){
        firstPart = units[unit]
    }else if(decad == 1){
        firstPart = tensA[unit]
    }else if (decad in arrayOf(2,3,4,5,6,7,8,9)){
        firstPart = tensB[decad]
    }
    if ((unit != 0) and (decad in arrayOf(2,3,4,5,6,8))){
        secondPart = units[unit]
    }else if(decad in arrayOf(7,9)){
        secondPart = tensA[unit]
    }
    if(secondPart == ""){
        connector = ""
    }else if ((unit == 1) and (decad in arrayOf(2,3,4,5,6,7))){
        connector = "-et-"
    }else{
        connector = "-"
    }
    return (firstPart + connector + secondPart)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Answer() {
    Column(Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
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