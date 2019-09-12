package co.uk.e2x.repository

import co.uk.e2x.handler.ProductNotFoundException
import co.uk.e2x.model.Category
import co.uk.e2x.model.Product
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class ProductRepositoryImpl(val restTemplate: RestTemplate) : ProductRepository {

    @Value("\${myconfig.url:}")
    var url: String? = null

    @Value("\${myconfig.apiKey:}")
    var apiKey: String? = null

    override fun getProduct(productId: Int): List<Product> {

        val responseEntity: ResponseEntity<Category> = restTemplate.exchange(
            url!!,
            HttpMethod.GET,
            HttpEntity<Any>(HttpHeaders()),
            Category::class.java,
            productId,
            apiKey
        )

        if (responseEntity.body!!.products.isEmpty()) throw ProductNotFoundException("Product $productId not found")

        return responseEntity.body!!.products
    }
}
