package org.btssio.edf_dabre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class AfficheListeClientsActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ClientsAdapter clientsAdapter;
    private List<Clients> listClients;
    private Intent modificationClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_liste_clients);

        DbAdapter dbClients = new DbAdapter(this);
        dbClients.open();
        listClients = dbClients.getListClients();
        dbClients.close();

        listView = (ListView) findViewById(R.id.listViewClients);
        clientsAdapter = new ClientsAdapter(this, listClients);
        listView.setAdapter(clientsAdapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        modificationClients = new Intent(this, modificationClientsActivity.class);
        modificationClients.putExtra("id", listClients.get(position).getIdentifiant());
        this.startActivityForResult(modificationClients, 10);
    }
}
