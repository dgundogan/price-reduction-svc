package co.uk.e2x.model

data class ProductModel(
    val productId: String,
    val title: String,
    val colorSwatches: List<ColorSwatchesModel>,
    val nowPrice: String,
    val priceLabel: String
)