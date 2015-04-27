package org.btssio.edf_dabre;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ClientsAdapter extends BaseAdapter {

    private List<Clients> listClients;
    private LayoutInflater layoutInflater;

    public ClientsAdapter(Context context, List<Clients> vListClient) {
        layoutInflater = LayoutInflater.from(context);
        listClients = vListClient;
    }

    /**
     * @return la taille de la liste client
     */
    @Override
    public int getCount() {
        return this.listClients.size();
    }

    /**
     * @param position
     * @return un client de la liste
     */
    @Override
    public Object getItem(int position) {
        return listClients.get(position);
    }

    /**
     * @param position
     * @return l'ID du client
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.vueclients, null);
            holder.vuePrenom = (TextView) convertView.findViewById(R.id.vuePrenom);
            holder.vueNom = (TextView) convertView.findViewById(R.id.vueNom);
            holder.vueTelephone = (TextView) convertView.findViewById(R.id.vueTelephone);
            holder.vueAdresse = (TextView) convertView.findViewById(R.id.vueAdresse);
            holder.vueCp = (TextView) convertView.findViewById(R.id.vueCp);
            holder.vueVille = (TextView) convertView.findViewById(R.id.vueVille);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.vuePrenom.setText(listClients.get(position).getPrenom());
        holder.vueNom.setText(listClients.get(position).getNom());
        holder.vueTelephone.setText(listClients.get(position).getTelephone());
        holder.vueAdresse.setText(listClients.get(position).getAdresse());
        holder.vueCp.setText(listClients.get(position).getCodePostal());
        holder.vueVille.setText(listClients.get(position).getVille());

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.rgb(50, 50, 50));
        }

        return convertView;
    }

    private class ViewHolder {
        TextView vueNom;
        TextView vuePrenom;
        TextView vueTelephone;
        TextView vueAdresse;
        TextView vueCp;
        TextView vueVille;
    }
}
