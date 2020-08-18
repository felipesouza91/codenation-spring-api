package br.com.felipe.codenationlog.api.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipe.codenationlog.api.dto.input.UserSystemInput;
import br.com.felipe.codenationlog.api.dto.model.UserSystemDTO;
import br.com.felipe.codenationlog.domain.model.UserSystem;
import br.com.felipe.codenationlog.infra.util.DtoAssembler;
import br.com.felipe.codenationlog.infra.util.DtoDisassembler;

@Service
public class UserSystemDtoManager
    implements DtoAssembler<UserSystem, UserSystemDTO>, DtoDisassembler<UserSystem, UserSystemInput> {

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public UserSystem toDomainObject(UserSystemInput inputObject) {
    return modelMapper.map(inputObject, UserSystem.class);
  }

  @Override
  public UserSystemDTO toModel(UserSystem object) {
    return modelMapper.map(object, UserSystemDTO.class);
  }

  @Override
  public List<UserSystemDTO> toCollectionModel(List<UserSystem> list) {
    return list.stream().map(this::toModel).collect(Collectors.toList());
  }

}