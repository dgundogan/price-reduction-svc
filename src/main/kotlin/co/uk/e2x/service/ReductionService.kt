package co.uk.e2x.service

import co.uk.e2x.model.LabelTypeEnum
import co.uk.e2x.model.ProductModel
import co.uk.e2x.repository.ProductRepository
import co.uk.e2x.transformer.ProductTransformer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReductionService {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var productTransformer: ProductTransformer

    fun getReducedProducts(productId: Int, priceLabelTypeEnum: LabelTypeEnum?): List<ProductModel> =
        productRepository.getProduct(productId)
            .map { product ->
                productTransformer.transformProduct(product, priceLabelTypeEnum)
            }
            .sortedByDescending { it.priceLabel }
}