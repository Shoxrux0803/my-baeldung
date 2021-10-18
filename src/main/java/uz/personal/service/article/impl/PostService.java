package uz.personal.service.article.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.NotNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.personal.criteria.article.PostCriteria;
import uz.personal.domain.article._Article;
import uz.personal.domain.article._Post;
import uz.personal.domain.auth._User;
import uz.personal.dto.GenericDto;
import uz.personal.dto.article.PostCreateDto;
import uz.personal.dto.article.PostDto;
import uz.personal.dto.article.PostUpdateDto;
import uz.personal.dto.article.RateCreateDto;
import uz.personal.enums.ErrorCodes;
import uz.personal.exception.GenericRuntimeException;
import uz.personal.exception.IdRequiredException;
import uz.personal.exception.ValidationException;
import uz.personal.mapper.GenericMapper;
import uz.personal.mapper.article.PostMapper;
import uz.personal.repository.article.IArticleRepository;
import uz.personal.repository.article.IPostRepository;
import uz.personal.repository.auth.IUserRepository;
import uz.personal.repository.setting.IErrorRepository;
import uz.personal.response.DataDto;
import uz.personal.service.GenericCrudService;
import uz.personal.service.article.IPostService;
import uz.personal.utils.BaseUtils;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class PostService extends GenericCrudService<_Post, PostDto, PostCreateDto, PostUpdateDto, PostCriteria, IPostRepository> implements IPostService {

    private final Log logger = LogFactory.getLog(getClass());
    private final GenericMapper genericMapper;
    private final IArticleRepository articleRepository;
    private final PostMapper postMapper;
    private final ObjectMapper objectMapper;
    private final IUserRepository userRepository;
    private final Validator validator;

    public PostService(IPostRepository repository, BaseUtils utils, IErrorRepository errorRepository, GenericMapper genericMapper, IArticleRepository articleRepository, PostMapper postMapper, ObjectMapper objectMapper, IUserRepository userRepository, Validator validator) {
        super(repository, utils, errorRepository);
        this.genericMapper = genericMapper;
        this.articleRepository = articleRepository;
        this.postMapper = postMapper;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<DataDto<PostDto>> get(Long id) {
        _Post post = repository.find(PostCriteria.childBuilder().selfId(id).build());
        validate(post, id);
        PostDto postDto = postMapper.toDto(post);
        return new ResponseEntity<>(new DataDto<>(postDto), HttpStatus.OK);
    }



    @Override
    public ResponseEntity<DataDto<List<PostDto>>> getAll(PostCriteria criteria) {
        Long total = repository.getTotalCount(criteria);
        return new ResponseEntity<>(new DataDto<>(postMapper.toDto(repository.findAll(criteria)), total), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<GenericDto>> create(final PostCreateDto dto) {

        Set<ConstraintViolation<PostCreateDto>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<PostCreateDto> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage()).append(" ");
            }
            throw new ValidationException(sb.toString());
        }

        _Article article = articleRepository.find(dto.getArticleId());
        if (utils.isEmpty(article)) {
            logger.error(String.format("Article with id '%s' not found", dto.getArticleId()));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.USER_NOT_FOUND_ID, utils.toErrorParams("Article", dto.getArticleId())));
        }

        if (!article.getAllowComment()){
            throw new GenericRuntimeException("You cannot write comment to this article!"); // todo commentga ruxsat berish uchun exception
        }

        _User user = userRepository.find(dto.getUserId());
        if (utils.isEmpty(user)) {
            logger.error(String.format("User with id '%s' not found", dto.getUserId()));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.USER_NOT_FOUND_ID, utils.toErrorParams("User", dto.getUserId())));
        }

        _Post post = postMapper.fromCreateDto(dto);

        post.setArticle(article);
        post.setUser(user);

        baseValidation(post);

        repository.save(post);

        return new ResponseEntity<>(new DataDto<>(genericMapper.fromDomain(post)), HttpStatus.CREATED);

    }

    @Override
    @Transactional
    public ResponseEntity<DataDto<PostDto>> update(final PostUpdateDto dto) {

        if (utils.isEmpty(dto.getId())) {
            throw new IdRequiredException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_ID_REQUIRED, "Post"));
        }

        _Post post = repository.find(dto.getId());

        validate(post, dto.getId());

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            objectMapper.updateValue(post, dto);
        } catch (JsonMappingException e) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_IS_NOT_UPDATED, utils.toErrorParams("Post"))); // todo buni validatsiyada ishlash keremi yo objectMapper bilanmi?
        }

//        baseValidation(link);

        repository.update(post);
        repository.save(post);

        return get(dto.getId());

    }

    @Override
    @Transactional
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        _Post post = repository.find(id);
        validate(post, id);
        repository.delete(post);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<DataDto<Boolean>> deleteAllByArticleId(Long articleId) {
        List<_Post> postList = repository.findAll(PostCriteria.childBuilder().articleId(articleId).build());

        postList.forEach(repository::delete);

        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
    public void baseValidation(@NotNull _Post entity) {
        if (utils.isEmpty(entity.getArticle())) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("Article", "Post")));
        }
        if (utils.isEmpty(entity.getContent())) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("post text", "Post")));
        }
        if (utils.isEmpty(entity.getUser())) {
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.OBJECT_GIVEN_FIELD_REQUIRED, utils.toErrorParams("User", "Post")));
        }
    }

    @Override
    public void validate(@NotNull _Post entity, @NotNull Long id) {
        if (utils.isEmpty(entity)) {
            logger.error(String.format("Post with id '%s' not found", id));
            throw new ValidationException(errorRepository.getErrorMessage(ErrorCodes.USER_NOT_FOUND_ID, utils.toErrorParams("Post", id)));
        }
    }


}
