package br.com.alura.model;

import jdk.jfr.Category;

public enum TipoArtista {

    SOLO("Solo"),
    DUPLA("Dupla"),
    BANDA("Banda");

    private String tipo;

    TipoArtista(String tipo){
        this.tipo = tipo;
    }

    public static TipoArtista fromString(String text){
        for(TipoArtista tipoArtista : TipoArtista.values()){
            if(tipoArtista.tipo.equalsIgnoreCase(text)){
                return tipoArtista;
            }
        }
        throw new IllegalArgumentException("Nenhum tipo de Artista encontrado para a String fornecida");
    }

}
