/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.chatapp.coneccaoservidor;

import java.util.List;


/**
 *
 * @author old_adam
 */
public interface MensagemListener {
    
    void onListaDeUsuariosChegando(List<String> usuarios);
    void onMensagemChegando(String remetente, String texto);
    void onMensagemDeErroChegando(String motivo);
    
}
