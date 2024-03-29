package co.uk.e2x.repository

import co.uk.e2x.handler.exception.ProductNotFoundException
import co.uk.e2x.model.Category
import co.uk.e2x.model.Product
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class ProductRepositoryImpl(val restTemplate: RestTemplate) : ProductRepository {

    @Value("\${myconfig.url:}")
    val url: String = ""

    @Value("\${myconfig.apiKey:}")
    val apiKey: String = ""

    override fun getProduct(productId: Int): List<Product>? {

        val responseEntity = restTemplate.exchange(
            url,
            HttpMethod.GET,
            HttpEntity<Any>(HttpHeaders()),
            Category::class.java,
            productId,
            apiKey
        )

        if (responseEntity.body?.products?.size == 0) {
            throw ProductNotFoundException("Product $productId not found")
        }

        return responseEntity.body?.products
    }
}