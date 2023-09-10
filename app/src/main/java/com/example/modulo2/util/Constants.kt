package com.example.modulo2.util

import com.example.modulo2.ui.clock.ItemSpinner

object Constants {
    const val DATABASE_CLOCK_TABLE = "ClocksTable"
    const val DATABASE_USER_TABLE = "UserTable"
    const val DATABASE_NAME = "Cloks"

    var CLOCK_ITEMS = listOf(
        ItemSpinner("https://www.swatchgroup.com/sites/default/files/styles/logo/public/brands/500-500-tissot-new.png", "Tissot", "1"),
        ItemSpinner("https://content.rolex.com/v7/dam/navigation/watches-navigation/rolex-watches-navigation-collection-m124300-0001_2010jva_001.jpg", "Rolex", "2"),
        ItemSpinner("https://bijouterielimpach.lu/media/catalog/category/logo-casio.jpg", "Casio", "3")
    )
}