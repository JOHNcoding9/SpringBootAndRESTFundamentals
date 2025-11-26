package com.example.demo.services;

import com.example.demo.domain.entities.Chamado;
import com.example.demo.domain.entities.Cliente;
import com.example.demo.domain.entities.Tecnico;
import com.example.demo.domain.enums.Prioridade;
import com.example.demo.domain.enums.Status;
import com.example.demo.dto.ChamadoRequestDTO;
import com.example.demo.dto.ChamadoResponseDTO;
import com.example.demo.repositories.ChamadoRepository;
import com.example.demo.repositories.ClienteRepository;
import com.example.demo.repositories.TecnicoRepository;
import com.example.demo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChamadoService {

    @Autowired   // Injeta o repositório gerenciado pelo Spring
    private ChamadoRepository chamadoRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

     // Busca segura com tratamento para recurso inexistente.(Get por ID)
    public ChamadoResponseDTO findById(Integer id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado! ID: " + id));

        return toResponseDTO(chamado); // Conversão entidade → DTO de resposta
    }

    // Busca segura com tratamento para lista vazia. (GET ALL)
    public List<ChamadoResponseDTO> findAll() {
        return chamadoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

// Criação de novo recurso. (POST)
    public ChamadoResponseDTO create(ChamadoRequestDTO dto) {
        Chamado chamado = fromRequestDTO(dto);  // Converte DTO → entidade
        Chamado salvo = chamadoRepository.save(chamado);
        return toResponseDTO(salvo); // Conversão entidade → DTO de resposta
    }

    // Atualização de  recurso existente (UPDATE)
    public ChamadoResponseDTO update(Integer id, ChamadoRequestDTO dto) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado! ID: " + id));

        Tecnico tecnico = tecnicoRepository.findById(dto.getTecnicoId())
                .orElseThrow(() -> new ObjectNotFoundException("Técnico não encontrado! ID: " + dto.getTecnicoId()));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! ID: " + dto.getClienteId()));
        
 // Atualiza valores do recurso
        chamado.setPrioridade(Prioridade.toEnum(dto.getPrioridade()));
        chamado.setStatus(Status.toEnum(dto.getStatus()));
        chamado.setTitulo(dto.getTitulo());
        chamado.setObservacoes(dto.getObservacoes());
        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setDataFechamento(dto.getDataFechamento());


        Chamado atualizado = chamadoRepository.save(chamado);

        return toResponseDTO(atualizado);
    }

 // Exclusão de recurso existente (DELETE)
    public void delete(Integer id) {
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado! ID: " + id));

        chamadoRepository.delete(chamado);
    }

   // =======================================================
    // Métodos auxiliares de conversão (DTO ↔ Entidade)
    // =======================================================

 // Converte DTO de requisição para a entidade Chamado
    private Chamado fromRequestDTO(ChamadoRequestDTO dto) {
        // Busca entidades relacionadas obrigatórias
        Tecnico tecnico = tecnicoRepository.findById(dto.getTecnicoId())
                .orElseThrow(() -> new ObjectNotFoundException("Técnico não encontrado! ID: " + dto.getTecnicoId()));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! ID: " + dto.getClienteId()));

        Chamado chamado = new Chamado();
        chamado.setPrioridade(Prioridade.toEnum(dto.getPrioridade()));
        chamado.setStatus(Status.toEnum(dto.getStatus()));
        chamado.setTitulo(dto.getTitulo());
        chamado.setObservacoes(dto.getObservacoes());
        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setDataFechamento(dto.getDataFechamento());

        return chamado;
    }

    private ChamadoResponseDTO toResponseDTO(Chamado chamado) {
        ChamadoResponseDTO dto = new ChamadoResponseDTO();
        dto.setId(chamado.getId());
        dto.setDataAbertura(chamado.getDataAbertura());
        dto.setDataFechamento(chamado.getDataFechamento());
        dto.setPrioridade(chamado.getPrioridade().name());
        dto.setStatus(chamado.getStatus().name());
        dto.setTitulo(chamado.getTitulo());
        dto.setObservacoes(chamado.getObservacoes());
        dto.setTecnicoNome(chamado.getTecnico().getNome());
        dto.setClienteNome(chamado.getCliente().getNome());

        return dto;
    }
}

