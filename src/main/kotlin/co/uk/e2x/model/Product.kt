package co.uk.e2x.model

data class Category(val products: List<Product>)

data class Product(val productId: String, val title: String, val price: Price, val colorSwatches: List<ColorSwatch>)

data class ColorSwatch(val basicColor: String, val skuId: String, val color: String)

data class Price( val was: Float?, val then1: Float?, val then2: Float?, val now: Any, val currency: CurrencyEnum)

data class ColorSwatchesModel(val color: String, val rgbColor: String, val skuid: String)