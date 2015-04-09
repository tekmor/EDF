package org.btssio.edf_dabre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class modificationClientsActivity extends Activity implements View.OnClickListener {

    private String id;
    private Clients client;
    private DbAdapter dbClients;

    private TextView infoIdentifiant;
    private TextView infoIdentite;
    private TextView infoTelephone;
    private TextView infoAdresse;
    private TextView infoCompteur;
    private TextView infoAncienReleve;
    private TextView infoDateAncienReleve;

    private EditText infoReleve;
    private EditText infoDateReleve;
    private EditText infoSituation;

    private Button buttonEnregistrer;
    private Button buttonAnnuler;
    private Button buttonGeolocalisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_clients);
        id = this.getIntent().getExtras().getString("id");

        dbClients = new DbAdapter(this);

        dbClients.open();

        client = dbClients.getClientWithId(id);

        infoIdentifiant = (TextView) findViewById(R.id.infoIdentifiant);
        infoIdentite = (TextView) findViewById(R.id.infoIdentite);
        infoTelephone = (TextView) findViewById(R.id.infoTelephone);
        infoAdresse = (TextView) findViewById(R.id.infoAdresse);
        infoCompteur = (TextView) findViewById(R.id.infoCompteur);
        infoAncienReleve = (TextView) findViewById(R.id.infoAncienReleve);
        infoDateAncienReleve = (TextView) findViewById(R.id.infoDateAncienReleve);

        infoIdentifiant.setText(client.getIdentifiant());
        infoIdentite.setText(client.getNom() + " " + client.getPrenom());
        infoTelephone.setText(client.getTelephone());
        infoAdresse.setText(client.getAdresse() + " " + client.getCodePostal() + ", " + client.getVille());
        infoCompteur.setText(client.getIdCompteur());
        infoAncienReleve.setText(String.valueOf(client.getAncienReleve()));
        infoDateAncienReleve.setText(String.valueOf(client.getDateAncienReleve()));

        infoReleve = (EditText) findViewById(R.id.infoReleve);
        infoDateReleve = (EditText) findViewById(R.id.infoDateReleve);
        infoSituation = (EditText) findViewById(R.id.infoSituation);

        buttonEnregistrer = (Button) findViewById(R.id.buttonEnregistrer);
        buttonEnregistrer.setOnClickListener(this);
        buttonAnnuler = (Button) findViewById(R.id.buttonAnnuler);
        buttonAnnuler.setOnClickListener(this);
        buttonGeolocalisation = (Button) findViewById(R.id.buttonGeolocalisation);
        buttonGeolocalisation.setOnClickListener(this);
    }

    private void save() {
        if (!infoReleve.getText().toString().isEmpty() && !infoDateReleve.getText().toString().isEmpty() && !infoSituation.getText().toString().isEmpty()) {
            try {
                double dernierReleve = Double.parseDouble(infoReleve.getText().toString());
                String dateDernierReleve = infoDateReleve.getText().toString();
                int situation = Integer.parseInt(infoSituation.getText().toString());

                if (situation != 0 && dernierReleve >= client.getAncienReleve()) {
                    client.setDernierReleve(dernierReleve);
                    client.setDateDernierReleve(dateDernierReleve);
                    client.setSituation(situation);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Erreur de saise", Toast.LENGTH_SHORT).show();
            }
        }

        dbClients.updateClient(client.getIdentifiant(), client);
        Toast.makeText(this, "Client sauvegardé", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonEnregistrer:
                save();
                break;
            case R.id.buttonAnnuler:
                finish();
                break;
            case R.id.buttonGeolocalisation:
                Intent intentGeolocalisation = new Intent(this, GeolocalisationActivity.class);
                intentGeolocalisation.putExtra("id", client.getIdentifiant());
                startActivity(intentGeolocalisation);
                break;
        }
    }
}
