package co.uk.e2x.service


import co.uk.e2x.model.*
import co.uk.e2x.repository.ProductRepository
import co.uk.e2x.transformer.ProductTransformer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
class ReductionServiceTest {

    private val categoryId: Int = 500

    @TestConfiguration
    internal class ReductionServicesImplTestContextConfiguration {

        @Bean
        fun reductionService(): ReductionService {
            return ReductionService()
        }

    }

    @Autowired
    lateinit var reductionService: ReductionService

    @MockBean
    val productTransformer: ProductTransformer? = null

    @MockBean
    val productRepository: ProductRepository? = null


    @Test
    fun `Given correct product , it returns Product Response Model`() {

        val product = Product(
            "100", "test", Price(null, null, null, "30.40", CurrencyEnum.EUR),
            listOf(ColorSwatch("", "", ""))
        )
        val mockProducts = listOf(product)

        val mockProductModels =
            ProductResponseModel("100", "test", listOf(ColorSwatchesModel("", "", "")), "£30", "Was £0.00, now £30.40")


        Mockito.`when`(productRepository!!.getProduct(categoryId))
            .thenReturn(mockProducts)

        Mockito.`when`(productTransformer!!.transformProduct(product, null))
            .thenReturn(mockProductModels)

        val productModel = reductionService.getReducedProducts(categoryId, null)
        assertEquals(productModel?.size, 1)
    }
}