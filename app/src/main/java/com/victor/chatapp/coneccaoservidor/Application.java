package com.victor.chatapp.coneccaoservidor;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Application implements MensagemListener {

    private static final Application instancia = new Application();
    public static Application getInstance() {return instancia;}

    private Comunicador comunicador;

    private int currentFragmentPosition;

    private Application() {
        currentFragmentPosition = 1;
        comunicador = new Comunicador();
        comunicador.addListener(Interpretador.getInstance());
        Interpretador.getInstance().addObservador(this);
    }

    /**
     *
     * @param userId
     */
    public void enviarMensagemLogin(String userId) throws JSONException {
        JSONObject login = new JSONObject();
        JSONObject userInfo = new JSONObject();
        userInfo.put("user-id", userId);
        login.put("login", userInfo);
        // { "login": { "user-id":"o.professor" } }
        String header = "{ \"login\": { \"user-id\":\"";
        String tail   = "\" } }";
        String mensagem = header + userId + tail;
        //Atualizando o id do Usuario.
        Usuario.getInstance().setUserId(userId);
       comunicador.enfileraMensagem(login.toString());
    }

    @Override
    public void onListaDeUsuariosChegando(List<String> usuarios) {
        Log.d("LISTA", "onListaDeUsuariosChegando: " + usuarios.toString());
        ListaUsuarios.getListaDeUsuarios().set(usuarios);
    }

    @Override
    public void onMensagemChegando(String remetente, String texto) {

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
