package com.darekbx.hexview.ui.hexview

object FormatUtils {

    fun formatNumber(number: Int): String =
        buildString {
            "$number"
                .reversed()
                .forEachIndexed { index, char ->
                    append(char)
                    if (index > 0 && (index + 1) % 3 == 0) {
                        append(' ')
                    }
                }
        }.reversed().trim()

}