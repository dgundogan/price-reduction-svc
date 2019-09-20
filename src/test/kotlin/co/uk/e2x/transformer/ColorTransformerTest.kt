package co.uk.e2x.transformer

import co.uk.e2x.model.ColorSwatch
import co.uk.e2x.model.ColorSwatchesModel
import org.junit.Assert.assertEquals
import org.junit.Test


class ColorTransformerTest {

    @Test
    fun `Given basic color that exists in Hex List, then returns the hex code of basic color with other info`() {
        val expected: List<ColorSwatchesModel> = listOf(
            ColorSwatchesModel("Wine", "FF0000", "237494589")
        )

        val colorList: List<ColorSwatch> = listOf(
            ColorSwatch("Red", "237494589", "Wine")
        )

        val actual = ColorTransformer.convertColors(colorList)

        assertEquals(expected, actual)
    }

    @Test
    fun `Given basic color that does not exist in Hex List, then returns the empty with other info`() {
        val expected: List<ColorSwatchesModel> = listOf(
            ColorSwatchesModel("Wine", "", "237494589")
        )

        val colorList: List<ColorSwatch> = listOf(
            ColorSwatch("Red1", "237494589", "Wine")
        )

        val actual = ColorTransformer.convertColors(colorList)

        assertEquals(expected, actual)
    }
}