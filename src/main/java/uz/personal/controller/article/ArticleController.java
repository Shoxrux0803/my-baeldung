package uz.personal.controller.article;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.personal.controller.ApiController;
import uz.personal.criteria.article.ArticleCriteria;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.ArticleCreateDto;
import uz.personal.dto.article.ArticleDto;
import uz.personal.dto.article.ArticleUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.article.IArticleService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Article controller", tags = "auth-article-controller")
@RestController
public class ArticleController extends ApiController<IArticleService> {

    public ArticleController(IArticleService service) {
        super(service);
    }

    @ApiOperation(value = "Get Single Article ")
    @RequestMapping(value = API_PATH + V_1 + "/article/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<ArticleDto>> getArticle(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

//    @ApiOperation(value = "Get List Article")
//    @RequestMapping(value = API_PATH + V_1 + "/article/get/all", method = RequestMethod.GET)
//    public ResponseEntity<DataDto<List<ArticleDto>>> getAllArticles(@Valid ArticleCriteria criteria) {
//        return service.getAll(criteria);
//    }

    @ApiOperation(value = "Article Create")
    @RequestMapping(value = API_PATH + V_1 + "/article/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createArticle(@RequestBody ArticleCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Article Update")
    @RequestMapping(value = API_PATH + V_1 + "/article/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<ArticleDto>> updateArticle(@RequestBody ArticleUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Article Delete")
    @RequestMapping(value = API_PATH + V_1 + "/article/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteArticle(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }
}
