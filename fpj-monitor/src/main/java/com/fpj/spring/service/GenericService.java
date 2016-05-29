package com.fpj.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fpj.client.dtos.IIdentifiableDto;
import com.fpj.spring.entities.IdentifiableEntity;
import com.fpj.spring.exception.NotFoundException;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.SortInfoBean;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

public abstract class GenericService<DTO_TYPE extends IIdentifiableDto, LIST_RESULT extends PagingLoadResultBean<DTO_TYPE>, ENTITY extends IdentifiableEntity> implements IGenericService<DTO_TYPE, LIST_RESULT, ENTITY> {

	/* (non-Javadoc)
	 * @see com.fpj.spring.service.IGenericService#list(com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean)
	 */
	@Override
	@Transactional
	public LIST_RESULT list(FilterPagingLoadConfigBean loadConfig) {
		PageRequest page;
		
		Page<ENTITY> allEntities;
		if (loadConfig!=null && loadConfig.getSortInfo() != null && loadConfig.getSortInfo().size() >0){
			List<Order> orders = new ArrayList<Order>();
			for (SortInfoBean sortInfo : loadConfig.getSortInfo()) {
				Order order = new Order(sortInfo.getSortDir()== SortDir.ASC? Direction.ASC: Direction.DESC, sortInfo.getSortField());
				orders.add(order);
			}
			Sort sort = new Sort(orders); 
			page = new PageRequest(loadConfig.getOffset()/loadConfig.getLimit(), loadConfig.getLimit(), sort);
			allEntities = getRepository().findAll(page);
			
		}else{
			page = new PageRequest(loadConfig.getOffset()/loadConfig.getLimit(), loadConfig.getLimit());
			allEntities = getRepository().findAll(page);
		}
		LIST_RESULT result = createResult();
		result.setOffset(page.getOffset());
		result.setTotalLength((int)allEntities.getTotalElements());
		result.setData(getListOfDtos(allEntities.getContent()));
		return result;
	}

	
	/* (non-Javadoc)
	 * @see com.fpj.spring.service.IGenericService#update(DTO_TYPE)
	 */
	@Override
	@Transactional
	public void update(DTO_TYPE dto) throws NotFoundException{
		ENTITY entity = getRepository().findOne(dto.getId());
		if (entity == null) {
			throw new NotFoundException(); 
		}
		
		fillEntity(entity, dto);
	}
	
	protected abstract void fillEntity(ENTITY entity, DTO_TYPE dto);

	protected abstract ENTITY createEntity();


	private List<DTO_TYPE> getListOfDtos(List<ENTITY> content) {
		List<DTO_TYPE> result = new ArrayList<DTO_TYPE>();
		for (ENTITY entity : content) {
			DTO_TYPE dto = getDto(entity);
			result.add(dto);
		}
		return result;
	}

	protected abstract DTO_TYPE getDto(ENTITY entity);

	protected abstract LIST_RESULT createResult();

	protected abstract JpaRepository<ENTITY, Integer> getRepository();


	/* (non-Javadoc)
	 * @see com.fpj.spring.service.IGenericService#createEmpty()
	 */
	@Override
	public DTO_TYPE createEmpty() {
		ENTITY entity = createEntity();
		getRepository().saveAndFlush(entity);
		return getDto(entity);
	}


	/* (non-Javadoc)
	 * @see com.fpj.spring.service.IGenericService#list()
	 */
	@Override
	@Transactional
	public LIST_RESULT list() {
		List<ENTITY> allEntities = getRepository().findAll();
		LIST_RESULT result = createResult();
		result.setData(getListOfDtos(allEntities));
		return result;
	}


	/* (non-Javadoc)
	 * @see com.fpj.spring.service.IGenericService#delete(java.lang.Integer)
	 */
	@Override
	public void delete(Integer id) {
		getRepository().delete(id);
	}

}
