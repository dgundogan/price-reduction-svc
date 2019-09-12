package co.uk.e2x.service

import co.uk.e2x.model.LabelTypeEnum
import co.uk.e2x.model.ProductModel

interface ReductionService {
    fun getReducedProducts(productId: Int, priceLabelTypeEnum: LabelTypeEnum?): List<ProductModel>
}