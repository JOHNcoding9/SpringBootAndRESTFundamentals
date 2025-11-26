package com.example.demo.domain.enums;

public enum Perfil {

    ADMIN(1, "ADMIN"),
    CLIENTE(2, "CLIENTE"),  // Cria os Objetos Enum e dentro deles armazena seu respectivo código e descricao
    TECNICO(3, "TECNICO");

    private final int codigo;   // Tanto o código como a descrição de um Enum deve ser uma constante (anotação "final")
    private final String descricao;

    Perfil(int codigo, String descricao) { //Construtor do Enum
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toEnum(Integer cod) { // Converte o códgio do Enum para a String "descricao" quando necessário.
        if (cod == null) {
            return null;
        }

        for (Perfil x : Perfil.values()) {
            if (cod.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Perfil inválido: " + cod);
    }
}

