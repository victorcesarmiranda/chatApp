package com.victor.chatapp.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import com.victor.chatapp.R;
import com.victor.chatapp.coneccaoservidor.Application;
import com.victor.chatapp.coneccaoservidor.ListaUsuarios;
import com.victor.chatapp.coneccaoservidor.Usuario;
import org.json.JSONException;

import java.util.List;
import java.util.Objects;

public class Contatos extends Fragment {

    List<String> lista;
    ContatoAdapter contatoAdapter;

    public Contatos() {
        super();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contatos_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final ListView listaContato = Objects.requireNonNull(getActivity()).findViewById(R.id.listaContatos);
        contatoAdapter = new ContatoAdapter();
        listaContato.setAdapter(contatoAdapter);

        listaContato.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Application.getInstance().setCurrentFragmentPosition(2);
                Usuario.getInstance().setCurrentContact(contatoAdapter.getItem(position).toString());
            }
        });
        Button botaoLogout = Objects.requireNonNull(getActivity()).findViewById(R.id.botaoLogout);
        botaoLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Application.getInstance().enviarMensagemLogout(Usuario.getInstance().getUserId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Application.getInstance().setCurrentFragmentPosition(0);
            }
        });
        ListaUsuarios.getListaDeUsuarios().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(final Observable sender, int propertyId) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        lista = ((ObservableField<List<String>>) sender).get();
                        contatoAdapter.notifyDataSetChanged();
                    }
                };
                Objects.requireNonNull(getActivity()).runOnUiThread(r);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class ContatoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (lista != null) return lista.size();
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (lista != null) return lista.get(i);
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup container) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.linha_usuario, container, false);
            }
            if (lista != null) {
                String contato = lista.get(position);

                ((TextView) view.findViewById(R.id.marcador)).setText("- ");
                ((TextView) view.findViewById(R.id.txtUserName)).setText(contato);
            }

            return view;
        }
    }
}