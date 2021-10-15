package uz.personal.controller.article;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.personal.controller.ApiController;
import uz.personal.criteria.article.LinkCriteria;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.LinkCreateDto;
import uz.personal.dto.article.LinkDto;
import uz.personal.dto.article.LinkUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.article.ILinkService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Link controller", tags = "auth-link-controller")
@RestController
public class LinkController extends ApiController<ILinkService> {

    public LinkController(ILinkService service) {
        super(service);
    }

    @ApiOperation(value = "Get Single Link ")
    @RequestMapping(value = API_PATH + V_1 + "/link/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<LinkDto>> getLink(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Get List Link")
    @RequestMapping(value = API_PATH + V_1 + "/link/get/all", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<LinkDto>>> getAllLinks(@Valid LinkCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Link Create")
    @RequestMapping(value = API_PATH + V_1 + "/link/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createLink(@RequestBody LinkCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Link Update")
    @RequestMapping(value = API_PATH + V_1 + "/link/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<LinkDto>> updateLink(@RequestBody LinkUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Link Delete")
    @RequestMapping(value = API_PATH + V_1 + "/link/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteLink(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

    @ApiOperation(value = "Link Delete All By Article Id")
    @RequestMapping(value = API_PATH + V_1 + "/link/deleteAll/{articleId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteAll(@PathVariable(value = "articleId") Long articleId) {
        return service.deleteAllByArticleId(articleId);
    }
}
