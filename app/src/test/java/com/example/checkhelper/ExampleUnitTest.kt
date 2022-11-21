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
        assertEquals("Not implemented", numberToLetters(-1))
        assertEquals("un", numberToLetters(1))
        assertEquals("deux", numberToLetters(2))
        assertEquals("dix", numberToLetters(10))
        assertEquals("onze", numberToLetters(11))
        assertEquals("douze", numberToLetters(12))
        assertEquals("treize", numberToLetters(13))
        assertEquals("quatorze", numberToLetters(14))
        assertEquals("quinze", numberToLetters(15))
        assertEquals("dix-sept", numberToLetters(17))
        assertEquals("dix-neuf", numberToLetters(19))
        assertEquals("vingt", numberToLetters(20))
        assertEquals("vingt-et-un", numberToLetters(21))
        assertEquals("vingt-deux", numberToLetters(22))
        assertEquals("trente", numberToLetters(30))
        assertEquals("trente-et-un", numberToLetters(31))
        assertEquals("trente-deux", numberToLetters(32))
        assertEquals("quarante", numberToLetters(40))
        assertEquals("cinquante", numberToLetters(50))
        assertEquals("soixante", numberToLetters(60))
        assertEquals("soixante-et-un", numberToLetters(61))
        assertEquals("soixante-huit", numberToLetters(68))
        assertEquals("soixante-neuf", numberToLetters(69))
        assertEquals("soixante-dix", numberToLetters(70))
        assertEquals("soixante-et-onze", numberToLetters(71))
        assertEquals("soixante-douze", numberToLetters(72))
        assertEquals("soixante-dix-neuf", numberToLetters(79))
        assertEquals("quatre-vingt", numberToLetters(80))
        assertEquals("quatre-vingt-un", numberToLetters(81))
        assertEquals("quatre-vingt-deux", numberToLetters(82))
        assertEquals("quatre-vingt-neuf", numberToLetters(89))
        assertEquals("quatre-vingt-dix", numberToLetters(90))
        assertEquals("quatre-vingt-onze", numberToLetters(91))
        assertEquals("quatre-vingt-dix-neuf", numberToLetters(99))
        assertEquals("Not implemented", numberToLetters(100))
    }
}