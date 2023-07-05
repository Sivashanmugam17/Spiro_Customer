package com.mauto.chd

import java.util.*

object Locales {

    val French: Locale by lazy { Locale("fr", "") }
    val English: Locale by lazy { Locale("en", "") }
    val Tamil: Locale by lazy { Locale("ta", "") }
    val Urdu: Locale by lazy { Locale("ur", "IN") }

    val RTL: Set<String> by lazy {
        hashSetOf(
                "ar",
                "dv",
                "fa",
                "ha",
                "he",
                "iw",
                "ji",
                "ps",
                "sd",
                "ug",
                "ur",
                "yi"
        )
    }
}