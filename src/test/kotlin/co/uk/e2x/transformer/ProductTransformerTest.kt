package co.uk.e2x.transformer


import co.uk.e2x.model.*
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductTransformerTest {

    private val productTransformer = ProductTransformer()


    @Test
    fun givenPriceMoreThan10_whenCallCalculatePrice_thenReturnIntegerPrice() {
        val expected = "£100"

        val actual = productTransformer.calculatePrice("100.00", "GBP")

        assertEquals(expected, actual)
    }

    @Test
    fun givenPriceLessThan10_whenCallCalculatePrice_thenReturnIntegerPrice() {
        val expected = "£9.87"

        val actual = productTransformer.calculatePrice("9.87", "GBP")

        assertEquals(expected, actual)
    }

    @Test
    fun givenColor_whenCallConvertColors_thenHexCode() {
        val expected = listOf(ColorSwatchesModel("Red", "FF0000", "23423"))

        val actual = productTransformer.convertColors(listOf(ColorSwatch("Red", "23423", "Red")))

        assertEquals(expected, actual)
    }

    @Test
    fun giveLabelTypeIsShowWasNow_whenCallConvertPriceLabel_thenReturnCorrectMessage() {

        val expected = "Was £100.00, now £50.00"

        val price = Price(100.00f, null, null, "50.00", CurrencyEnum.GBP)

        val actual = productTransformer.convertPriceLabel(price, LabelTypeEnum.ShowWasNow)

        assertEquals(expected, actual)
    }

    @Test
    fun giveLabelTypeIsShowWasThenNowAndThen2IsNotEmpty_whenCallConvertPriceLabel_thenReturnCorrectMessage() {

        val expected = "Was €9.99, then €7.77, now €5.55"

        val price = Price(9.99f, 9.00f, 7.77f, 5.55f, CurrencyEnum.EUR)

        val actual = productTransformer.convertPriceLabel(price, LabelTypeEnum.ShowWasThenNow)

        assertEquals(expected, actual)
    }

    @Test
    fun giveLabelTypeIsShowWasThenNowAndThen2IsEmpty_whenCallConvertPriceLabel_thenReturnCorrectMessage() {

        val expected = "Was €9.99, then €9.00, now €5.55"

        val price = Price(9.99f, 9.00f, null, 5.55f, CurrencyEnum.EUR)

        val actual = productTransformer.convertPriceLabel(price, LabelTypeEnum.ShowWasThenNow)

        assertEquals(expected, actual)
    }

    @Test
    fun giveLabelTypeIsShowPercDscount_whenCallConvertPriceLabel_thenReturnCorrectMessage() {

        val expected = "200.0% off - now $100.00"

        val price = Price(50.00f, null, null, "100.00", CurrencyEnum.USD)

        val actual = productTransformer.convertPriceLabel(price, LabelTypeEnum.ShowPercDscount)

        assertEquals(expected, actual)
    }

    @Test
    fun giveLabelTypeIsNull_whenCallConvertPriceLabel_thenReturnCorrectMessage() {

        val expected = "Was £100.00, now £50.00"

        val price = Price(100.00f, null, null, "50.00", CurrencyEnum.GBP)

        val actual = productTransformer.convertPriceLabel(price, null)

        assertEquals(expected, actual)
    }

    @Test
    fun transformProduct() {

        val expected = ProductModel(
            "1234", "title", listOf(ColorSwatchesModel("Red", "FF0000", "2342")),
            "£100", "Was £100.00, now £100.00"
        )

        val actual = productTransformer.transformProduct(
            Product(
                "1234", "title",
                Price(100.00f, 98.00f, 60.00f, "100.00", CurrencyEnum.GBP),
                listOf(ColorSwatch("Red", "2342", "Red"))
            ), LabelTypeEnum.ShowWasNow
        )

        assertEquals(expected, actual)
    }
}