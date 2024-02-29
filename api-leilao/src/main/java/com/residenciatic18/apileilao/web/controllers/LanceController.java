package com.residenciatic18.apileilao.web.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.residenciatic18.apileilao.entities.Lance;
import com.residenciatic18.apileilao.services.LanceService;
import com.residenciatic18.apileilao.web.dto.LanceResponseDto;
import com.residenciatic18.apileilao.web.dto.form.LanceForm;
import com.residenciatic18.apileilao.web.dto.mapper.LanceMapper;

@RestController
@RequestMapping("/lance")
public class LanceController {

  @Autowired
  private LanceService lanceService;

  @PostMapping("/create")
  public ResponseEntity<LanceResponseDto> create(@RequestBody LanceForm createDto) {
    Lance obj = lanceService.salvar(LanceMapper.toLeilao(createDto));
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
    return ResponseEntity.created(uri).body(LanceMapper.toDto(obj));
  }

  @GetMapping("/{id}")
  public ResponseEntity<List<LanceResponseDto>> getById(@PathVariable Long id) {
    try {
      List<Lance> lances = lanceService.buscarTodos(id);

      if (lances.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

      return ResponseEntity.ok(LanceMapper.toListDto(lances));

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<List<LanceResponseDto>> buscarTodos() {
    return ResponseEntity.ok(LanceMapper.toListDto(lanceService.buscarTodos(null)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<LanceResponseDto> update(@PathVariable Long id, @RequestBody LanceForm createDto) {
    try {
      return ResponseEntity.ok(LanceMapper.toDto(lanceService.update(id, createDto)));

    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {

    if (lanceService.isExisteId(id)) {

      try {
        lanceService.delete(id);
        return ResponseEntity.ok().build();

      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}