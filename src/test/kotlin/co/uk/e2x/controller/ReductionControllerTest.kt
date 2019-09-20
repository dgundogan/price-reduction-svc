package co.uk.e2x.controller

import co.uk.e2x.handler.exception.ProductNotFoundException
import co.uk.e2x.model.ColorSwatchesModel
import co.uk.e2x.model.ProductResponseModel
import co.uk.e2x.service.ReductionService
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.springframework.http.HttpStatus


class ReductionControllerTest {

    private val reductionService = mockk<ReductionService>(relaxed = true)

    private val reductionController = ReductionController(reductionService)

    @Test(expected = ProductNotFoundException::class)
    fun `Given incorrect categoryId , it returns ProductNotFoundException`() {

        every {
            reductionService.getReducedProducts(100, null)
        } returns emptyList()

        reductionController.getDiscountedProducts(100, null)
    }

    @Test
    fun `Given correct categoryId , it returns OK and body`() {

        val expected = listOf(ProductResponseModel("100", "Title",
            listOf(ColorSwatchesModel("Red", "0000", "0000")), "10.00", "GBP"))

        every {
            reductionService.getReducedProducts(100, null)
        } returns expected

        val response = reductionController.getDiscountedProducts(100, null)

        Assert.assertEquals(HttpStatus.OK, response.statusCode)
        Assert.assertEquals(expected, response.body)
    }
}