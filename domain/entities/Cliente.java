package com.example.demo.domain.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente extends Pessoa { // Faz Cliente ser uma extensão da classe molde/superclass.

    @OneToMany(mappedBy = "cliente") // Define relacionamento 1:N . No caso, um cliente para vários chamados.
    private List<Chamado> chamados = new ArrayList<>();

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) { //Construtor
        this.chamados = chamados;
    }
}

