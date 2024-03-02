package com.residenciatic18.apileilao.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.residenciatic18.apileilao.entities.Lance;
import com.residenciatic18.apileilao.web.dto.LanceResponseDto;
import com.residenciatic18.apileilao.web.dto.form.LanceForm;

public class LanceMapper {

  public static Lance toLeilao(LanceForm createDto) {
    return new ModelMapper().map(createDto, Lance.class);
  }

  public static LanceResponseDto toDto(Lance lance) {
    PropertyMap<Lance, LanceResponseDto> props = new PropertyMap<Lance, LanceResponseDto>() {
      @Override
      protected void configure() {
        map().setId(source.getId());
        map().setLeilaoId(source.getLeilao().getId());
        map().setConcorrenteId(source.getConcorrente().getId());
        map().setValor(source.getValor());
      }
    };
    ModelMapper mapper = new ModelMapper();
    mapper.addMappings(props);
    return mapper.map(lance, LanceResponseDto.class);
  }

  // public static LanceResponseDto toDto(Lance lance) {
  // ModelMapper mapper = new ModelMapper();
  // mapper.typeMap(Lance.class, LanceResponseDto.class)
  // .addMapping(src -> src.getLeilao().getId(), LanceResponseDto::setLeilaoId)
  // .addMapping(src -> src.getConcorrente().getId(),
  // LanceResponseDto::setConcorrenteId)
  // .addMapping(src -> src.getValor(), LanceResponseDto::setValor);

  // return mapper.map(lance, LanceResponseDto.class);
  // }

  public static List<LanceResponseDto> toListDto(List<Lance> lances) {
    return lances.stream().map(user -> toDto(user)).collect(Collectors.toList());
  }
}
