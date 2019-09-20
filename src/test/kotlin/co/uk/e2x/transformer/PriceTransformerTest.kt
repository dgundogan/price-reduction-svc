package co.uk.e2x.transformer

import co.uk.e2x.model.CurrencyEnum
import co.uk.e2x.model.LabelTypeEnum
import co.uk.e2x.model.Price
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Assert
import org.junit.Test

class PriceTransformerTest {

    private val json = jacksonObjectMapper()

    @Test
    fun `Given price is not correct format, it returns 0`() {

        val expected = "£0.0"

        val result = PriceTransformer.calculatePrice("wrong", "GBP")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given price is less then £10, it returns a decimal value`() {

        val expected = "£1.75"

        val result = PriceTransformer.calculatePrice("1.75", "GBP")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given price is greater then £10, it returns an integer value`() {

        val expected = "£10"

        val result = PriceTransformer.calculatePrice("10.02", "GBP")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given price is an object and price is less then £10, it returns a decimal value`() {

        val expected = "£1.75"

        val jsonStr: LinkedHashMap<String, String> = json.readValue("""{"from": "2.00", "to": "1.75" }""")

        val result = PriceTransformer.calculatePrice(jsonStr, "GBP")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given price is an object and price is greater then £10, it returns an integer value`() {

        val expected = "£11"

        val jsonStr: LinkedHashMap<String, String> = json.readValue("""{"from": "15.00", "to": "11.05" }""")

        val result = PriceTransformer.calculatePrice(jsonStr, "GBP")

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given priceLabel is empty, it returns a result message as priceLabel is ShowWasNow`() {
        val expected = "Was £0.00, now £59.00"

        val result = PriceTransformer.convertPriceLabel(Price(null, null, null, "59.00", CurrencyEnum.GBP), null)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given priceLabel is ShowWasNow, it returns a result message - Was £x,xx, now £y,yy -`() {
        val expected = "Was £100.00, now £59.00"

        val result = PriceTransformer.convertPriceLabel(
            Price(100.00f, null, null, "59.00", CurrencyEnum.GBP),
            LabelTypeEnum.ShowWasNow
        )

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given priceLabel is ShowWasNow and Now is an object, it returns a result message - Was £x,xx, now £y,yy -`() {
        val expected = "Was £10.00, now £1.75"

        val jsonStr: LinkedHashMap<String, String> = json.readValue("""{"from": "2.00", "to": "1.75" }""")

        val result = PriceTransformer.convertPriceLabel(
            Price(10.00f, null, null, jsonStr , CurrencyEnum.GBP),
            LabelTypeEnum.ShowWasNow
        )

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given priceLabel is ShowWasThenNow, it returns a result message - Was £x,xx, then £y,yy, now £z,zz -`() {
        val expected = "Was £100.00, then £80.00, now £59.00"

        val result = PriceTransformer.convertPriceLabel(
            Price(100.00f, 80.00f, null, "59.00", CurrencyEnum.GBP),
            LabelTypeEnum.ShowWasThenNow
        )

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given priceLabel is ShowWasThenNow and then2 is not empty, it returns a result message with then2 - Was £x,xx, then £y,yy, now £z,zz -`() {
        val expected = "Was £100.00, then £65.00, now £59.00"

        val result = PriceTransformer.convertPriceLabel(
            Price(100.00f, 80.00f, 65.00f, "59.00", CurrencyEnum.GBP),
            LabelTypeEnum.ShowWasThenNow
        )

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given priceLabel is ShowWasThenNow and then1 is empty, it returns a result message - Was £x,xx, then £y,yy, now £z,zz -`() {
        val expected = "Was £100.00, then £0.00, now £59.00"

        val result = PriceTransformer.convertPriceLabel(
            Price(100.00f, null, null, "59.00", CurrencyEnum.GBP),
            LabelTypeEnum.ShowWasThenNow
        )

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given priceLabel is ShowPercDscount, it returns a result message - x% off - now £y,yy -`() {
        val expected = "59% off - now £59.00"

        val result = PriceTransformer.convertPriceLabel(
            Price(100.00f, null, null, "59.00", CurrencyEnum.GBP),
            LabelTypeEnum.ShowPercDscount
        )

        Assert.assertEquals(expected, result)
    }
}