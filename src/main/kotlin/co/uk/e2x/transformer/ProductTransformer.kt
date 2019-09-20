package co.uk.e2x.transformer

import co.uk.e2x.model.LabelTypeEnum
import co.uk.e2x.model.Product
import co.uk.e2x.model.ProductResponseModel
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component

@Component
@Slf4j
class ProductTransformer {
    fun transformProduct(product: Product, priceLabelTypeEnum: LabelTypeEnum?): ProductResponseModel =
        ProductResponseModel(
            product.productId,
            product.title,
            ColorTransformer.convertColors(product.colorSwatches),
            PriceTransformer.calculatePrice(product.price.now, product.price.currency.toString()),
            PriceTransformer.convertPriceLabel(product.price, priceLabelTypeEnum)
        )
}