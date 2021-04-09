package com.victor.chatapp.activities;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import com.victor.chatapp.R;
import com.victor.chatapp.coneccaoservidor.Application;
import com.victor.chatapp.coneccaoservidor.ListaMensagens;
import com.victor.chatapp.coneccaoservidor.Usuario;
import org.json.JSONException;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mensagens} factory method to
 * create an instance of this fragment.
 */
public class Mensagens extends Fragment {

    List<Mensagem> lista;
    MensagemAdapter mensagemAdapter;
    Usuario usuario = Usuario.getInstance();

    public Mensagens() {
        super();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mensagens, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TextView) Objects.requireNonNull(getActivity()).findViewById(R.id.nomeDestino)).setText(Usuario.getInstance().getCurrentContact());
    }


    @Override
    public void onStart() {
        super.onStart();
        final ListView listaMensagem = Objects.requireNonNull(getActivity()).findViewById(R.id.listaMensagem);
        mensagemAdapter = new MensagemAdapter();
        listaMensagem.setAdapter(mensagemAdapter);

        Button botaoEnviar = Objects.requireNonNull(getActivity()).findViewById(R.id.botaoEnviar);
        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText campoMensagem = Objects.requireNonNull(getActivity()).findViewById(R.id.edtNovaMensagem);
                final String message = campoMensagem.getText().toString();
                if (!message.isEmpty()) {
                    final Thread r = new Thread() {
                        @Override
                        public void run() {
                            try {
                                Application.getInstance().enviarMensagemTexto(usuario.getUserId(), usuario.getCurrentContact(), message);
                                ListaMensagens.setNovaMensagem(usuario.getUserId(), message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    r.start();
                }
                campoMensagem.setText("");
            }
        });
        ListaMensagens.getListaMensagens().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(final Observable sender, int propertyId) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        lista = ((ObservableField<List<Mensagem>>) sender).get();
                        mensagemAdapter.notifyDataSetChanged();
                    }
                };
                Objects.requireNonNull(getActivity()).runOnUiThread(r);
            }
        });

        Button botaoVoltar = Objects.requireNonNull(getActivity()).findViewById(R.id.botaoVoltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application.getInstance().setCurrentFragmentPosition(1);
            }
        });

    }

    public static class Mensagem {

        String sender, content;

        public Mensagem(String sender, String content) {
            this.sender = sender;
            this.content = content;
        }

    }

    class MensagemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (lista != null) {
                return lista.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (lista != null) {
                return lista.get(i);
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup container) {
            String sender, content;
            Mensagem mensagemAuxiliar = lista.get(position);
            sender = mensagemAuxiliar.sender;
            content = mensagemAuxiliar.content;

            if (view == null) {
                if (sender.equals(usuario.getUserId())) {
                    view = getLayoutInflater().inflate(R.layout.linha_mensagem, container, false);
                } else if (sender.equals(usuario.getCurrentContact())) {
                    view = getLayoutInflater().inflate(R.layout.linha_mensagem_outro, container, false);
                    ((TextView) view.findViewById(R.id.text_gchat_user_other)).setText(sender);
                }
                if (view != null) {
                    ((TextView) view.findViewById(R.id.txtMensagem)).setText(content);
                }
            }


            return view;
        }
    }

}