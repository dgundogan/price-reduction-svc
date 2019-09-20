package co.uk.e2x.repository

import co.uk.e2x.model.*
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.RestTemplate

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRepositoryImplTest {


    private lateinit var productRepository: ProductRepository

    @Test
    fun `Given correct ProductId, it returns success`() {

        val mockProducts = listOf(
            Product(
                "100", "100", Price(null, null, null, "100", CurrencyEnum.EUR)
                , listOf(ColorSwatch("", "", ""))
            )
        )
        val mockCategory = Category(mockProducts)

        val restTemplate = mockk<RestTemplate>(relaxed = true)
        every {
            restTemplate.exchange(
                "https://test/v1/categories/{productId}/products?key={keyValue}",
                HttpMethod.GET,
                HttpEntity<Any>(HttpHeaders()),
                Category::class.java,
                600001506,
                "2ALHCAAs6ikGRBoy6eTHA58RaG097Fma"
            )
        } returns ResponseEntity.ok(mockCategory)
        productRepository = ProductRepositoryImpl(restTemplate)


        ReflectionTestUtils.setField(
            productRepository,
            "url",
            "https://test/v1/categories/{productId}/products?key={keyValue}"
        )
        ReflectionTestUtils.setField(productRepository, "apiKey", "2ALHCAAs6ikGRBoy6eTHA58RaG097Fma")


        val result = productRepository.getProduct(600001506)

        Assert.assertNotNull(result)
        Assert.assertEquals(result, mockProducts)
    }
}