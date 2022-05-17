package com.bahadirmemis.couriertrackingstudycase.gen.service;

import com.bahadirmemis.couriertrackingstudycase.gen.entity.BaseAdditionalFields;
import com.bahadirmemis.couriertrackingstudycase.gen.entity.BaseEntity;
import com.bahadirmemis.couriertrackingstudycase.gen.enums.GenErrorMessage;
import com.bahadirmemis.couriertrackingstudycase.gen.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E, Long>> {

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 10;

    private final D dao;

    public List<E> findAll(){
        return dao.findAll();
    }

    public Optional<E> findById(Long id){
        return dao.findById(id);
    }

    public E save(E entity){

        setAdditionalFields(entity);
        entity = dao.save(entity);

        return entity;
    }

    private void setAdditionalFields(E entity) {

        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

        if (baseAdditionalFields == null){
            baseAdditionalFields = new BaseAdditionalFields();
            entity.setBaseAdditionalFields(baseAdditionalFields);
        }

        baseAdditionalFields.setUpdateDate(new Date());

        if (entity.getId() == null){
            /**
             * User who registered or updated user information could be saved here.
             * I did not write because no authentication mechanism is used.
             *
             * baseAdditionalFields.setCreatedBy(userId);
             */

        }

        /** baseAdditionalFields.setUpdatedBy(userId); */
    }

    public void delete(E entity){
        dao.delete(entity);
    }

    public E getByIdWithControl(Long id) {

        Optional<E> entityOptional = findById(id);

        E entity;
        if (entityOptional.isPresent()){
            entity = entityOptional.get();
        } else {
            throw new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);
        }

        return entity;
    }

    public boolean existsById(Long id){
        return dao.existsById(id);
    }

    public D getDao() {
        return dao;
    }

    protected PageRequest getPageRequest(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        Integer page = getPage(pageOptional);
        Integer size = getSize(sizeOptional);

        PageRequest pageRequest = PageRequest.of(page, size);
        return pageRequest;
    }

    protected Integer getSize(Optional<Integer> sizeOptional) {

        Integer size = DEFAULT_SIZE;
        if (sizeOptional.isPresent()){
            size = sizeOptional.get();
        }
        return size;
    }

    protected Integer getPage(Optional<Integer> pageOptional) {

        Integer page = DEFAULT_PAGE;
        if (pageOptional.isPresent()){
            page = pageOptional.get();
        }
        return page;
    }
}
