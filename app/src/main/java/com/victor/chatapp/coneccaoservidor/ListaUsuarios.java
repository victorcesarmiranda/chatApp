package com.victor.chatapp.coneccaoservidor;

import androidx.databinding.ObservableField;

import java.util.List;

public class ListaUsuarios {
    private static ObservableField<List<String>> listaDeUsuarios = new ObservableField<>();

    public static ObservableField<List<String>> getListaDeUsuarios() {
        return listaDeUsuarios;
    }
}
