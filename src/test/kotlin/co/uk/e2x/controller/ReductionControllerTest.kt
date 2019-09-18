package co.uk.e2x.controller

import co.uk.e2x.model.ColorSwatchesModel
import co.uk.e2x.model.ProductModel
import co.uk.e2x.service.ReductionService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(ReductionController::class)
class ReductionControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var discountService: ReductionService


    @Test
    fun givenEmptyList_whenCallGetReducedProducts_thenReturnOK() {
        doReturn(null).`when`(discountService).getReducedProducts(100, null)

        mockMvc.perform(get("/categories/{categoryId}/reductions", 100))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun givenCategoryId_whenCallGetReducedProducts_thenReturnOK() {
        val mockProducts = listOf(ProductModel("100", "test", listOf(ColorSwatchesModel("", "", "")), "1", ""))

        doReturn(mockProducts).`when`(discountService).getReducedProducts(100, null)

        mockMvc.perform(get("/categories/{categoryId}/reductions", 100))
            .andExpect(status().isOk)
    }
}