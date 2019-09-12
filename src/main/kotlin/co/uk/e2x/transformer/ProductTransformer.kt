package co.uk.e2x.transformer


import co.uk.e2x.model.*
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.lang.Float.parseFloat
import java.util.stream.Collectors

@Component
@Slf4j
@Suppress("UNCHECKED_CAST")
class ProductTransformer {
    fun transformProduct(product: Product, priceLabelTypeEnum: LabelTypeEnum?): ProductModel =
        ProductModel(
            product.productId,
            product.title,
            convertColors(product.colorSwatches),
            calculatePrice(product.price.now, product.price.currency.toString()),
            convertPriceLabel(product.price, priceLabelTypeEnum)
        )

    fun calculatePrice(price: Any, currency: String): String {
        val nowPrice: Float? = try {
            if (price is String) {
                parseFloat(price as String?)
            } else {
                val priceNow = price as LinkedHashMap<String, String>
                parseFloat(priceNow["to"])
            }
        } catch (e: Exception) {
            0.0f
        }
        return CurrencyEnum.valueOf(currency).currency.plus(
            when (nowPrice!! < 10) {
                false -> Math.round(nowPrice)
                true -> nowPrice
            }
        )
    }

    val convertColorToHex = hashMapOf(
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
        if (colorSwatch.isNotEmpty()) colorSwatch.stream().map { color ->
            ColorSwatchesModel(color.color, convertColorToHex[color.basicColor]!!, color.skuId)
        }.collect(Collectors.toList())
        else arrayListOf(ColorSwatchesModel("", "", ""))


    fun convertPriceLabel(price: Price, priceLabelTypeEnum: LabelTypeEnum?): String = when (priceLabelTypeEnum) {
        LabelTypeEnum.ShowWasNow
            -> "Was ${price.currency.currency}${"%.2f".format(calculatePrice(price.was.toString()))}" +
                ", now ${price.currency.currency}${"%.2f".format(calculatePrice(getPriceNow(price)))}"
        LabelTypeEnum.ShowWasThenNow
            -> "Was %s%.2f, then %s%.2f, now %s%.2f".format(price.currency.currency,
                calculatePrice(price.was.toString()), price.currency.currency,
                calculatePrice((price.then2 ?: price.then1).toString()), price.currency.currency,
                calculatePrice(price.now.toString())
        )
        LabelTypeEnum.ShowPercDscount
            -> "%s%s off - now %s%.2f".format(
                calculatePercentage(calculatePrice(price.was.toString()),calculatePrice(getPriceNow(price))),
               "%", price.currency.currency, calculatePrice(getPriceNow(price))
        )
        null -> "Was ${price.currency.currency}${"%.2f".format(calculatePrice(price.was.toString()))}" +
                ", now ${price.currency.currency}${"%.2f".format(calculatePrice(getPriceNow(price)))}"
    }

    fun getPriceNow(price: Price): String {
        return when {
            price.now is String -> price.now
            else -> {
                val priceNow = price.now as LinkedHashMap<String, String>
                priceNow["to"]!!
            }
        }
    }

    fun calculatePrice(price: String): Float {
        if(price.isBlank()) return 0.00f
        return try {
            price.toFloat()
        } catch (e: Exception) {
            0.00f
        }
    }


    fun calculatePercentage(val1: Float?, val2: Float?): String {
        if(val1!!.equals(0.00f)) return "0"
        return try {
            (val2!!.div(val1) * 100).toString()
        } catch (e: Exception) {
            "0"
        }
    }
}

