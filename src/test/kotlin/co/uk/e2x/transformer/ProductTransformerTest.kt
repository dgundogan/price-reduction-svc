package co.uk.e2x.transformer

import co.uk.e2x.model.*
import org.junit.Assert
import org.junit.Test


class ProductTransformerTest {

    private val productTransformer = ProductTransformer()

    @Test
    fun `Given product and priceLabelTypeEnum is empty, it returns ProductResponseModel `() {

        val expected = ProductResponseModel(
            "3487329", "Oasis Grecian Midi Dress",
            listOf(
                ColorSwatchesModel("Navy", "0000FF", "237445349"),
                ColorSwatchesModel("Khaki", "008000", "237445371")
            ), "£30", "Was £0.00, now £30.40"
        )

        val actual = productTransformer.transformProduct(
            Product(
                "3487329", "Oasis Grecian Midi Dress",
                Price(null, null, null, "30.40", CurrencyEnum.GBP),
                listOf(
                    ColorSwatch("Blue", "237445349", "Navy"),
                    ColorSwatch("Green", "237445371", "Khaki")
                )
            ), null
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `Given product and priceLabelTypeEnum is ShowWasNow, it returns ProductResponseModel `() {

        val expected = ProductResponseModel(
            "3487329", "Oasis Grecian Midi Dress",
            listOf(
                ColorSwatchesModel("Navy", "0000FF", "237445349"),
                ColorSwatchesModel("Khaki", "008000", "237445371")
            ), "£30", "Was £0.00, now £30.40"
        )

        val actual = productTransformer.transformProduct(
            Product(
                "3487329", "Oasis Grecian Midi Dress",
                Price(null, null, null, "30.40", CurrencyEnum.GBP),
                listOf(
                    ColorSwatch("Blue", "237445349", "Navy"),
                    ColorSwatch("Green", "237445371", "Khaki")
                )
            ), LabelTypeEnum.ShowWasNow
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `Given product and priceLabelTypeEnum is ShowPercDscount, it returns ProductResponseModel `() {

        val expected = ProductResponseModel(
            "3487329", "Oasis Grecian Midi Dress",
            listOf(
                ColorSwatchesModel("Navy", "0000FF", "237445349"),
                ColorSwatchesModel("Khaki", "008000", "237445371")
            ), "£30", "0% off - now £30.40"
        )

        val actual = productTransformer.transformProduct(
            Product(
                "3487329", "Oasis Grecian Midi Dress",
                Price(null, null, null, "30.40", CurrencyEnum.GBP),
                listOf(
                    ColorSwatch("Blue", "237445349", "Navy"),
                    ColorSwatch("Green", "237445371", "Khaki")
                )
            ), LabelTypeEnum.ShowPercDscount
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `Given product and priceLabelTypeEnum is ShowWasThenNow, it returns ProductResponseModel `() {

        val expected = ProductResponseModel(
            "3487329", "Oasis Grecian Midi Dress",
            listOf(
                ColorSwatchesModel("Navy", "0000FF", "237445349"),
                ColorSwatchesModel("Khaki", "008000", "237445371")
            ), "£30", "Was £0.00, then £0.00, now £30.40"
        )

        val actual = productTransformer.transformProduct(
            Product(
                "3487329", "Oasis Grecian Midi Dress",
                Price(null, null, null, "30.40", CurrencyEnum.GBP),
                listOf(
                    ColorSwatch("Blue", "237445349", "Navy"),
                    ColorSwatch("Green", "237445371", "Khaki")
                )
            ), LabelTypeEnum.ShowWasThenNow
        )

        Assert.assertEquals(expected, actual)
    }
}