package com.victor.chatapp.coneccaoservidor;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Application implements MensagemListener {

    private static final Application instancia = new Application();
    private final Comunicador comunicador;
    private int currentFragmentPosition;


    private Application() {
        currentFragmentPosition = 1;
        comunicador = new Comunicador();
        comunicador.addListener(Interpretador.getInstance());
        Interpretador.getInstance().addObservador(this);
    }

    public static Application getInstance() {
        return instancia;
    }

    /**
     * @param userId
     */
    public void enviarMensagemLogin(String userId) throws JSONException {
        JSONObject login = new JSONObject();
        JSONObject userInfo = new JSONObject();
        userInfo.put("user-id", userId);
        login.put("login", userInfo);
        Usuario.getInstance().setUserId(userId);
        comunicador.enfileraMensagem(login.toString());
    }

    public void enviarMensagemLogout(String userId) throws JSONException {
        JSONObject logout = new JSONObject();
        JSONObject userInfo = new JSONObject();
        userInfo.put("user-id", userId);
        logout.put("logout", userInfo);
        comunicador.enfileraMensagem(logout.toString());
    }

    public void enviarMensagemTexto(String sender, String receiver, String content) throws JSONException {
        JSONObject message = new JSONObject();
        JSONObject messageInfo = new JSONObject();
        messageInfo.put("sender", sender);
        messageInfo.put("receiver", receiver);
        messageInfo.put("content", content);
        message.put("message", messageInfo);
        comunicador.enfileraMensagem(message.toString());
    }


    @Override
    public void onListaDeUsuariosChegando(List<String> usuarios) {
        Log.d("LISTA", "onListaDeUsuariosChegando: " + usuarios.toString());
        ListaUsuarios.getListaDeUsuarios().set(usuarios);
    }

    @Override
    public void onMensagemChegando(String remetente, String texto) {
        ListaMensagens.setNovaMensagem(remetente, texto);
    }

    @Override
    public void onMensagemDeErroChegando(String motivo) {

    }

    public int getCurrentFragmentPosition() {
        return currentFragmentPosition;
    }

    public void setCurrentFragmentPosition(int currentFragmentPosition) {
        this.currentFragmentPosition = currentFragmentPosition;
    }
}
