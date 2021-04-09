package com.victor.chatapp.coneccaoservidor;

import androidx.databinding.ObservableField;
import com.victor.chatapp.activities.Mensagens;

import java.util.ArrayList;
import java.util.List;

public class ListaMensagens {
    private static final ObservableField<List<Mensagens.Mensagem>> listaMensagens = new ObservableField<>();

    public static ObservableField<List<Mensagens.Mensagem>> getListaMensagens() {
        return listaMensagens;
    }

    public static void setNovaMensagem(String remetente, String texto) {
        List<Mensagens.Mensagem> auxiliar;
        if (getListaMensagens().get() == null) {
            auxiliar = new ArrayList<>();
        } else {
            auxiliar = new ArrayList<>(getListaMensagens().get());
        }
        Mensagens.Mensagem mensagem = new Mensagens.Mensagem(remetente, texto);
        auxiliar.add(mensagem);
        getListaMensagens().set(auxiliar);
    }
}