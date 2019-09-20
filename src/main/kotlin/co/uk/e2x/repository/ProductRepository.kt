package co.uk.e2x.repository

import co.uk.e2x.model.Product

interface ProductRepository {
    fun getProduct(productId: Int): List<Product>?
}