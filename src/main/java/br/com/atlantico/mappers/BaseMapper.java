package br.com.atlantico.mappers;

import java.util.List;

public interface BaseMapper<T, S> {

    S mapFromDTO(T dto);

    List<S> mapFromDTOList(List<T> dtos);

    T mapFromEntity(S entity);

    List<T> mapFromEntityList(List<S> entities);

}
