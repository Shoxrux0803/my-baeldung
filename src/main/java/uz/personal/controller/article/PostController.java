package uz.personal.controller.article;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.personal.controller.ApiController;
import uz.personal.criteria.article.PostCriteria;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.PostCreateDto;
import uz.personal.dto.article.PostDto;
import uz.personal.dto.article.PostUpdateDto;
import uz.personal.response.DataDto;
import uz.personal.service.article.IPostService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "post controller", tags = "auth-post-controller")
@RestController
public class PostController extends ApiController<IPostService> {

    public PostController(IPostService service) {
        super(service);
    }

    @ApiOperation(value = "Get Single Post ")
    @RequestMapping(value = API_PATH + V_1 + "/post/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<PostDto>> getPost(@PathVariable(value = "id") Long id) {
        return service.get(id);
    }

    @ApiOperation(value = "Get List Post")
    @RequestMapping(value = API_PATH + V_1 + "/post/get/all", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<PostDto>>> getAllPosts(@Valid PostCriteria criteria) {
        return service.getAll(criteria);
    }

    @ApiOperation(value = "Post Create")
    @RequestMapping(value = API_PATH + V_1 + "/post/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<GenericDto>> createPost(@RequestBody PostCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "Post Update")
    @RequestMapping(value = API_PATH + V_1 + "/post/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<PostDto>> updatePost(@RequestBody PostUpdateDto dto) {
        return service.update(dto);
    }

    @ApiOperation(value = "Post Delete")
    @RequestMapping(value = API_PATH + V_1 + "/post/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deletePost(@PathVariable(value = "id") Long id) {
        return service.delete(id);
    }

    @ApiOperation(value = "Post Delete All By Article Id")
    @RequestMapping(value = API_PATH + V_1 + "/post/deleteAllByArticleId/{articleId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteAllByArticleId(@PathVariable(value = "articleId") Long articleId) {
        return service.deleteAllByArticleId(articleId);
    }
}
