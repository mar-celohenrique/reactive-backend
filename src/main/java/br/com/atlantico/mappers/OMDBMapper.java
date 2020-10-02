package br.com.atlantico.mappers;

import br.com.atlantico.dtos.MovieDTO;
import br.com.atlantico.dtos.OMDBMovieDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OMDBMapper extends BaseMapper<MovieDTO, OMDBMovieDTO> {

}
