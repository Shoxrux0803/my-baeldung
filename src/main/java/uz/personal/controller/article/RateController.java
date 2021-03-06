package uz.personal.controller.article;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.personal.controller.ApiController;
import uz.personal.criteria.article.RateCriteria;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.RateCreateDto;
import uz.personal.dto.article.RateDto;
import uz.personal.dto.article.RateUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.article.IRateService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Api(value = "Rate controller", tags = "rate-controller")
@RestController
public class RateController extends ApiController<IRateService> {

    public RateController(IRateService service) {
        super(service);
    }

    @ApiOperation(value = "Get Single rate ")
    @RequestMapping(value = API_PATH + V_1 + "/rate/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<RateDto>> getRate(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @Transactional
    @ApiOperation(value = "Get List rate")
    @RequestMapping(value = API_PATH + V_1 + "/rate/get/all", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<RateDto>>> getAllRate(@Valid RateCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Rate Create")
    @RequestMapping(value = API_PATH + V_1 + "/rate/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createRate(@RequestBody RateCreateDto dto) {
        return service.create(dto);
    }

    @Transactional
    @ApiOperation(value = "Rate Update")
    @RequestMapping(value = API_PATH + V_1 + "/rate/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<RateDto>> updateRate(@RequestBody RateUpdateDto dto) {
        return service.update(dto);
    }

    @Transactional
    @ApiOperation(value = "Rate Delete")
    @RequestMapping(value = API_PATH + V_1 + "/rate/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteRAte(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

    @ApiOperation(value = "Rate Delete All By Article Id")
    @RequestMapping(value = API_PATH + V_1 + "/rate/deleteAllByArticleID/{articleId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteAllByArticleId(@PathVariable(value = "articleId") Long articleId) {
        return service.deleteAllByArticleId(articleId);
    }

    @Transactional
    @ApiOperation(value = "Rate By Article Id")
    @RequestMapping(value = API_PATH + V_1 + "/rate/getRateByArticleId/{articleId}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<Double>> avgRate(@PathVariable("articleId") Long dto) {
        return service.avgRate(dto);
    }


}
