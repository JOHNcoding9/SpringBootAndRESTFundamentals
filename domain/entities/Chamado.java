package com.example.demo.domain.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import com.example.demo.domain.enums.Prioridade;
import com.example.demo.domain.enums.Status;

@Entity
@Table(name = "chamado")
public class Chamado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // define como um ID autogerado pelo banco
    private Integer id;

    private LocalDate dataAbertura = LocalDate.now();
    private LocalDate dataFechamento;

    private Integer prioridade;
    private Integer status;
    private String titulo;
    private String observacoes;

    @ManyToOne  // Tipo de relacionamento N:1  (muitos para um). No caso, muitos chamados para um técnico.
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    @ManyToOne  // Tipo de relacionamento N:1  (muitos para um). No caso, muitos cahamdos para um cliente.
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;



    // Seção de configuração de Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDate getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDate dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

// Converte o código numérico salvo no banco para o enum Prioridade. (Salvar Ordinais numéricos é mais leve para o banco, o truque é usar eles para serem armazenados e quando necessário, convertê-los para String/descrição do valor do enum)
    public Prioridade getPrioridade() {    
        return Prioridade.toEnum(prioridade);
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade.getCodigo();
    }

// Converte o código numérico salvo no banco para o enum Status. (Salvar Ordinais numéricos é mais leve para o banco, o truque é usar eles para serem armazenados e quando necessário, convertê-los para String/descrição do valor do enum)
    public Status getStatus() {
        return Status.toEnum(status);
    }

    public void setStatus(Status status) {
        this.status = status.getCodigo();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

