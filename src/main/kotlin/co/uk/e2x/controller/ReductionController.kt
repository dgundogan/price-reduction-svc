package co.uk.e2x.controller

import co.uk.e2x.handler.ProductNotFoundException
import co.uk.e2x.model.LabelTypeEnum
import co.uk.e2x.model.ProductModel
import co.uk.e2x.service.ReductionService
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Slf4j
@RequestMapping("/reduction")
class ReductionController(internal var reductionService: ReductionService) {

    @GetMapping("/reductionProductsByCategoryId/{categoryId}")
    fun getDiscountedProducts(
        @PathVariable("categoryId") categoryId: Int,
        @RequestParam("labelType") labelTypeEnum: LabelTypeEnum?
    )
            : ResponseEntity<List<ProductModel>?> {
        val reductions = reductionService.getReducedProducts(categoryId, labelTypeEnum)

        if (reductions.isEmpty())
            throw ProductNotFoundException("Product '$categoryId' not found")

        return ResponseEntity(reductions, HttpStatus.OK)
    }
}