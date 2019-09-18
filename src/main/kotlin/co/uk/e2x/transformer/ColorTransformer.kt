package co.uk.e2x.transformer

import co.uk.e2x.model.ColorSwatch
import co.uk.e2x.model.ColorSwatchesModel

class ColorTransformer {
    companion object {
        private val convertColorToHex = hashMapOf(
            "Black" to "000000",
            "White" to "FFFFFF",
            "Red" to "FF0000",
            "Blue" to "0000FF",
            "Yellow" to "FFFF00",
            "Magenta" to "FF00FF",
            "Silver" to "C0C0C0",
            "Grey" to "808080",
            "Green" to "008000",
            "Purple" to "800080",
            "Orange" to "FFA500",
            "Pink" to "FFC0CB",
            "Multi" to "XXXXXX"
        )

        fun convertColors(colorSwatch: List<ColorSwatch>): List<ColorSwatchesModel> =
            colorSwatch.map { color ->
                ColorSwatchesModel(color.color, convertColorToHex[color.basicColor] ?: "", color.skuId)
            }
    }
}