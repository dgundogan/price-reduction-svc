package co.uk.e2x.transformer

import co.uk.e2x.model.CurrencyEnum
import co.uk.e2x.model.LabelTypeEnum
import co.uk.e2x.model.Price

@Suppress("UNCHECKED_CAST")
class PriceTransformer {
    companion object {
        fun calculatePrice(price: Any, currency: String): String {
            val nowPrice: Float = try {
                if (price is String) {
                    price.toFloat()
                } else {
                    val priceNow = price as LinkedHashMap<String, String>
                    priceNow["to"].toString().toFloat()
                }
            } catch (e: Exception) {
                0.0f
            }
            return CurrencyEnum.valueOf(currency).currency.plus(
                when (nowPrice < 10) {
                    false -> nowPrice.let { Math.round(it) }
                    true -> nowPrice
                }
            )
        }

        fun convertPriceLabel(price: Price, priceLabelTypeEnum: LabelTypeEnum?): String = when (priceLabelTypeEnum) {
            LabelTypeEnum.ShowWasNow
            -> "Was ${getCurrency(price)}${"%.2f".format(calculatePrice(price.was.toString()))}" +
                    ", now ${getCurrency(price)}${"%.2f".format(calculatePrice(getPriceNow(price)))}"
            LabelTypeEnum.ShowWasThenNow
            -> "Was %s%.2f, then %s%.2f, now %s%.2f".format(
                getCurrency(price),
                calculatePrice(price.was.toString()), getCurrency(price),
                calculatePrice((price.then2 ?: price.then1).toString()), getCurrency(price),
                calculatePrice(price.now.toString())
            )
            LabelTypeEnum.ShowPercDscount
            -> "%s%s off - now %s%.2f".format(
                calculatePercentage(calculatePrice(price.was.toString()), calculatePrice(getPriceNow(price))),
                "%", getCurrency(price), calculatePrice(getPriceNow(price))
            )
            null -> "Was ${getCurrency(price)}${"%.2f".format(calculatePrice(price.was.toString()))}" +
                    ", now ${getCurrency(price)}${"%.2f".format(calculatePrice(getPriceNow(price)))}"
        }

        private fun getCurrency(price: Price): String {
            return price.currency.currency
        }

        private fun getPriceNow(price: Price): String {
            return when {
                price.now is String -> price.now
                else -> {
                    val priceNow = price.now as LinkedHashMap<String, String>
                    priceNow["to"].toString()
                }
            }
        }

        private fun calculatePrice(price: String): Float {
            if (price.isBlank()) return 0.00f
            return try {
                price.toFloat()
            } catch (e: Exception) {
                0.00f
            }
        }

        private fun calculatePercentage(val1: Float, val2: Float): String {
            if (val1.equals(0.00f)) return "0"
            return try {
                 Math.round((val2.div(val1) * 100)).toString()
            } catch (e: Exception) {
                "0"
            }
        }
    }
}