package uz.personal.service.article;

import uz.personal.criteria.article.LinkCriteria;
import uz.personal.domain.article._Link;
import uz.personal.dto.article.LinkCreateDto;
import uz.personal.dto.article.LinkDto;
import uz.personal.dto.article.LinkUpdateDto;
import uz.personal.service.IGenericCrudService;

public interface ILinkService extends IGenericCrudService<_Link, LinkDto, LinkCreateDto, LinkUpdateDto,Long, LinkCriteria> {

}