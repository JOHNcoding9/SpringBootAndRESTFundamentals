package com.example.demo.domain.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tecnico")
public class Tecnico extends Pessoa { // Extende Tecnico da classe molde/SuperClasse

    @OneToMany(mappedBy = "tecnico") // Define relacionamento 1:N . No caso, um técnico para vários chamados.
    private List<Chamado> chamados = new ArrayList<>();

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}

