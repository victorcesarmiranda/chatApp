package com.victor.chatapp.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import com.victor.chatapp.R;
import com.victor.chatapp.coneccaoservidor.Application;
import com.victor.chatapp.coneccaoservidor.ListaUsuarios;

import java.util.List;

public class Contatos extends Fragment {

    List<String> lista;
    MyAdapter myAdapter;

    public Contatos () {
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
        ListView listaContato = getActivity().findViewById(R.id.listaContatos);
        myAdapter = new MyAdapter();
        listaContato.setAdapter(myAdapter);

        listaContato.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Application.getInstance().setCurrentFragmentPosition(2);
            }
        });
        ListaUsuarios.getListaDeUsuarios().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(final Observable sender, int propertyId) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        lista = ((ObservableField<List<String>>)sender).get();
                        myAdapter.notifyDataSetChanged();
                    }
                };
                getActivity().runOnUiThread(r);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(lista != null) return lista.size();
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if(lista != null) return lista.get(i);
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup container) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.linha_usuario, container, false);
            }
            if(lista != null) {
                String contato = lista.get(position);

                ((TextView) view.findViewById(R.id.txtHorario)).setText("- ");
                ((TextView) view.findViewById(R.id.txtUserName)).setText(contato);
            }

            return view;
        }
    }
}