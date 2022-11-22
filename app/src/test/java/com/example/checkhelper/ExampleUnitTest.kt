package com.example.checkhelper

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class UnitTests{
    @Test
    fun numberToLetters(){
        assertEquals("Not implemented", numberToLetters(-1,0))
        assertEquals("", numberToLetters(0,0)) // zero treated as special case in caller function
        assertEquals("un", numberToLetters(0,1))
        assertEquals("deux", numberToLetters(0,2))
        assertEquals("dix", numberToLetters(1,0))
        assertEquals("onze", numberToLetters(1,1))
        assertEquals("douze", numberToLetters(1,2))
        assertEquals("treize", numberToLetters(1,3))
        assertEquals("quatorze", numberToLetters(1,4))
        assertEquals("quinze", numberToLetters(1,5))
        assertEquals("dix-sept", numberToLetters(1,7))
        assertEquals("dix-neuf", numberToLetters(1,9))
        assertEquals("vingt", numberToLetters(2,0))
        assertEquals("vingt-et-un", numberToLetters(2,1))
        assertEquals("vingt-deux", numberToLetters(2,2))
        assertEquals("trente", numberToLetters(3,0))
        assertEquals("trente-et-un", numberToLetters(3,1))
        assertEquals("trente-deux", numberToLetters(3,2))
        assertEquals("quarante", numberToLetters(4,0))
        assertEquals("cinquante", numberToLetters(5,0))
        assertEquals("soixante", numberToLetters(6,0))
        assertEquals("soixante-et-un", numberToLetters(6,1))
        assertEquals("soixante-huit", numberToLetters(6,8))
        assertEquals("soixante-neuf", numberToLetters(6,9))
        assertEquals("soixante-dix", numberToLetters(7,0))
        assertEquals("soixante-et-onze", numberToLetters(7,1))
        assertEquals("soixante-douze", numberToLetters(7,2))
        assertEquals("soixante-dix-neuf", numberToLetters(7,9))
        assertEquals("quatre-vingts", numberToLetters(8,0))
        assertEquals("quatre-vingt-un", numberToLetters(8,1))
        assertEquals("quatre-vingt-deux", numberToLetters(8,2))
        assertEquals("quatre-vingt-neuf", numberToLetters(8,9))
        assertEquals("quatre-vingt-dix", numberToLetters(9,0))
        assertEquals("quatre-vingt-onze", numberToLetters(9,1))
        assertEquals("quatre-vingt-dix-neuf", numberToLetters(9,9))
        assertEquals("Not implemented", numberToLetters(10,0))
    }
    @Test
    fun numberToLettersHundreds() {
        assertEquals("Not implemented", numberToLettersHundreds(-1))
        assertEquals("zéro", numberToLettersHundreds(0))
        assertEquals("cent", numberToLettersHundreds(100))
        assertEquals("cent-un", numberToLettersHundreds(101))
        assertEquals("deux-cent-vingt", numberToLettersHundreds(220))
        assertEquals("trois-cents", numberToLettersHundreds(300))
        assertEquals("sept-cents", numberToLettersHundreds(700))
        assertEquals("Not implemented", numberToLettersHundreds(1000))
    }
}