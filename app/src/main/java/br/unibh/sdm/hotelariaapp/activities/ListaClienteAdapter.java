package br.unibh.sdm.hotelariaapp.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.unibh.sdm.hotelariaapp.entidades.Cliente;

public class ListaClienteAdapter extends BaseAdapter {
    private final List<Cliente> lista;
    private final Context context;

    public ListaClienteAdapter(Context context, List<Cliente> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        Cliente objeto = lista.get(position);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(objeto.getNome());
        return view;
    }

}
