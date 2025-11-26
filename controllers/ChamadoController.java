package com.example.demo.controllers;

import com.example.demo.dto.ChamadoRequestDTO;
import com.example.demo.dto.ChamadoResponseDTO;
import com.example.demo.services.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/chamados") // Define a rota do recusro como "/chamados"
public class ChamadoController {

    private final ChamadoService chamadoService; //Puxa o Serviço do reucurso para a camada Controller

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    // Retorna a resposta adequada ao realizar POST com sucesso
    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> create(@Valid @RequestBody ChamadoRequestDTO dto) {
        ChamadoResponseDTO response = chamadoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

      // Retorna a resposta adequada ao realizar GET com sucesso
    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> findById(@PathVariable("id") Integer id) {
        ChamadoResponseDTO response = chamadoService.findById(id);
        return ResponseEntity.ok(response);
    }

   // Retorna a resposta adequada ao realizarGET ALL com sucesso
    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> findAll() {
        List<ChamadoResponseDTO> chamados = chamadoService.findAll();
        return ResponseEntity.ok(chamados);
    }
    
   // Retorna a resposta adequada ao realizar PUT com sucesso
    @PutMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> update(
            @PathVariable("id") Integer id,
            @Valid @RequestBody ChamadoRequestDTO dto) {
        ChamadoResponseDTO response = chamadoService.update(id, dto);
        return ResponseEntity.ok(response);
    }

   // Retorna a resposta adequada ao realizar DELETE com sucesso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        chamadoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

