package co.uk.e2x.transformer


import co.uk.e2x.model.*
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.lang.Float.parseFloat
import java.util.stream.Collectors

@Component
@Slf4j
class ProductTransformer {
    fun transformProduct(product: Product, priceLabelTypeEnum: LabelTypeEnum?): ProductModel =
        ProductModel(product.productId,product.title,convertColors(product.colorSwatches),
            calculatePrice(product.price.now.toString(),product.price.currency.toString()),
            convertPriceLabel(product.price, priceLabelTypeEnum))

    fun calculatePrice(price: String, currency: String): String{
        val nowPrice: Float? = try { parseFloat(price as String?) } catch (e: Exception) { 0.0f }
        return CurrencyEnum.valueOf(currency).currency.plus(when(nowPrice!! < 10){
            false  -> Math.round(nowPrice)
            true   -> nowPrice })
    }

    val convertColorToHex = hashMapOf(  "Black" to "000000",
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
        "Multi" to "XXXXXX")

    fun convertColors(colorSwatch: List<ColorSwatch>): List<ColorSwatchesModel> =
        if(colorSwatch.isNotEmpty()) colorSwatch.stream().map { color ->
            ColorSwatchesModel(color.color, convertColorToHex[color.basicColor]!!, color.skuId)
        }.collect(Collectors.toList()) else arrayListOf(ColorSwatchesModel("","",""))


    fun convertPriceLabel(price: Price, priceLabelTypeEnum: LabelTypeEnum?):String = when(priceLabelTypeEnum){
        LabelTypeEnum.ShowWasNow ->"Was ${price.currency.currency}${"%.2f".format(calculatePrice(price.was.toString()))}" +
                ", now ${price.currency.currency}${"%.2f".format(calculatePrice(price.now.toString()))}"
        LabelTypeEnum.ShowWasThenNow -> "Was %s%.2f, then %s%.2f, now %s%.2f".format(price.currency.currency,
            calculatePrice(price.was.toString()), price.currency.currency,
            calculatePrice((price.then2 ?: price.then1).toString())
            , price.currency.currency, calculatePrice(price.now.toString()))
        LabelTypeEnum.ShowPercDscount -> "%s%s off - now %s%.2f".format(
            calculatePercentage(calculatePrice(price.was.toString()),calculatePrice(price.now.toString())),
            "%", price.currency.currency,calculatePrice(price.now.toString()))
        null -> "Was ${price.currency.currency}${"%.2f".format(calculatePrice(price.was.toString()))}" +
                ", now ${price.currency.currency}${"%.2f".format(calculatePrice(price.now.toString()))}"
    }


    fun calculatePrice(price: String): Float{
        var result = 0.00f
        try {
            result = price.toFloat()
        }catch (e: Exception){

        }
        return result
    }


    fun calculatePercentage(val1: Float?, val2: Float?): String{
        println(val1.toString() + "::"+ val2.toString())
        var result = "0"
        try {
            result = (val2!!.div(val1!!) * 100).toString()
        }catch (e: Exception){

        }
        return result
    }
}

