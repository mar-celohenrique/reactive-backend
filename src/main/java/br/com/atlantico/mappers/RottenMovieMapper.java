package br.com.atlantico.mappers;

import br.com.atlantico.dtos.MovieDTO;
import br.com.atlantico.dtos.RottenMovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RottenMovieMapper extends BaseMapper<MovieDTO, RottenMovieDTO> {

    @Mapping(target = "poster",
            expression = "java(java.util.Optional.ofNullable(entity.getPosters()).isPresent() ? entity.getPosters().getOriginal() : \"\")")
    @Override
    MovieDTO mapFromEntity(RottenMovieDTO entity);

}
