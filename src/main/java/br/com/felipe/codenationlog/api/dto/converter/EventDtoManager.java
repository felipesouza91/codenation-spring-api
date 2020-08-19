package br.com.felipe.codenationlog.api.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.felipe.codenationlog.api.dto.input.EventInput;
import br.com.felipe.codenationlog.api.dto.model.EventDTO;
import br.com.felipe.codenationlog.api.dto.model.EventResumeDTO;
import br.com.felipe.codenationlog.domain.model.Event;
import br.com.felipe.codenationlog.infra.util.DtoAssembler;
import br.com.felipe.codenationlog.infra.util.DtoDisassembler;

@Service
public class EventDtoManager implements DtoAssembler<Event, EventDTO>, DtoDisassembler<Event, EventInput> {

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public Event toDomainObject(EventInput inputObject) {
    return modelMapper.map(inputObject, Event.class);

  }

  @Override
  public EventDTO toModel(Event object) {
    return modelMapper.map(object, EventDTO.class);

  }

  public EventResumeDTO toModelResume(Event object) {
    return modelMapper.map(object, EventResumeDTO.class);

  }

  @Override
  public List<EventDTO> toCollectionModel(List<Event> list) {
    return list.stream().map(this::toModel).collect(Collectors.toList());
  }

  public List<EventResumeDTO> toCollectionModelResume(List<Event> list) {
    return list.stream().map(this::toModelResume).collect(Collectors.toList());
  }

}