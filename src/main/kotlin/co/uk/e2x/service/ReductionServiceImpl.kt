package co.uk.e2x.service

import co.uk.e2x.model.LabelTypeEnum
import co.uk.e2x.model.ProductModel
import co.uk.e2x.repository.ProductRepository
import co.uk.e2x.transformer.ProductTransformer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ReductionServiceImpl : ReductionService {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var productTransformer: ProductTransformer

    override fun getReducedProducts(productId: Int, priceLabelTypeEnum: LabelTypeEnum?): List<ProductModel> =
        productRepository.getProduct(productId)
            .stream()
            .map { product ->
                productTransformer.transformProduct(product, priceLabelTypeEnum)
            }
            .collect(Collectors.toList())
            .sortedByDescending { it.priceLabel }
}