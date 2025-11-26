package com.example.demo.domain.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.domain.enums.Perfil;

@MappedSuperclass  //Define a classe como uma SuperClasse ( não será uma tabela/entidade, apenas um molde para criar outras com as mesmas características)
public abstract class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // define como um ID autogerado pelo banco
    private Integer id;

    private String nome;

    @Column(unique = true, nullable = false) //Garante valor com unicidade e não null
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    private Integer perfil;

   private LocalDate dataCriacao = LocalDate.now();

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
// Converte o código numérico salvo no banco para o enum Perfil. (Salvar Ordinais numéricos é mais leve para o banco, o truque é usar eles para serem armazenados e quando necessário, convertê-los para String/descrição do valor do enum)
    public Perfil getPerfil() {
        return Perfil.toEnum(perfil);
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil.getCodigo();
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}



