package co.uk.e2x.controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReductionControllerIntegrationTest {


    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `Given incorrect category Id, it returns Bad Request`() {

        mockMvc.perform(get("/categories/{categoryId}/reductions", "100")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `Given correct category Id and labelType is empty, it returns Success response`() {

        mockMvc.perform(get("/categories/{categoryId}/reductions", "600001506")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].productId").value("3467432"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Boden Rosamund Posy Stripe Dress"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].colorSwatches[0].color").value("Navy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].colorSwatches[0].rgbColor").value("0000FF"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].colorSwatches[0].skuid").value("237334043"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].nowPrice").value("£63"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].priceLabel").value("Was £90.00, now £63.00"))
    }

    @Test
    fun `Given correct category Id and labelType is ShowWasNow, it returns Success response`() {

        mockMvc.perform(get("/categories/{categoryId}/reductions", "600001506")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .param("labelType","ShowWasNow"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].productId").value("3467432"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Boden Rosamund Posy Stripe Dress"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].colorSwatches[0].color").value("Navy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].colorSwatches[0].rgbColor").value("0000FF"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].colorSwatches[0].skuid").value("237334043"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].nowPrice").value("£63"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].priceLabel").value("Was £90.00, now £63.00"))
    }

    @Test
    fun `Given correct category Id and labelType is ShowWasThenNow, it returns Success response`() {

        mockMvc.perform(get("/categories/{categoryId}/reductions", "600001506")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .param("labelType","ShowWasThenNow"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].productId").value("3467432"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Boden Rosamund Posy Stripe Dress"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].colorSwatches[0].color").value("Navy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].colorSwatches[0].rgbColor").value("0000FF"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].colorSwatches[0].skuid").value("237334043"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].nowPrice").value("£63"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].priceLabel").value("Was £90.00, then £0.00, now £63.00"))
    }

    @Test
    fun `Given correct category Id and labelType is ShowPercDscount, it returns Success response`() {

        mockMvc.perform(get("/categories/{categoryId}/reductions", "600001506")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .param("labelType","ShowPercDscount"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value("3341058"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Jolie Moi Bonded Lace Prom Dress"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].colorSwatches[0].color").value("Dark Grey"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].colorSwatches[0].rgbColor").value("808080"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].colorSwatches[0].skuid").value("237169362"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].nowPrice").value("£68"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].priceLabel").value("80% off - now £68.00"))
    }
}