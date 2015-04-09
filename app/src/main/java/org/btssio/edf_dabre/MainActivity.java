package org.btssio.edf_dabre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private ImageButton imgButtonIdentification;
    private ImageButton imgButtonClients;
    private ImageButton imgButtonTransferts;
    private ImageButton imgButtonSauvegarde;
    private Intent intentAfficheListClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDb();

        imgButtonIdentification = (ImageButton) findViewById(R.id.imgButtonIdentification);
        imgButtonClients = (ImageButton) findViewById(R.id.imgButtonClients);
        imgButtonTransferts = (ImageButton) findViewById(R.id.imgButtonTransferts);
        imgButtonSauvegarde = (ImageButton) findViewById(R.id.imgButtonSauvegarde);

        imgButtonIdentification.setOnClickListener(this);
        imgButtonClients.setOnClickListener(this);
        imgButtonTransferts.setOnClickListener(this);
        imgButtonSauvegarde.setOnClickListener(this);
    }

    public void initDb() {
        DbAdapter dbClients = new DbAdapter(this);
        dbClients.open();
        dbClients.removeClientWithID("HS01");
        dbClients.removeClientWithID("MB01");
        dbClients.removeClientWithID("DW01");
        dbClients.insererClient(new Clients("HS01", "Simpson", "Homer", "1 Place Saint Pierre", "72000", "Le Mans", "0243000000", "7876565645", "17/10/1989", 654.3));
        dbClients.insererClient(new Clients("MB01", "Berthelot", "Marcelin", "28 Rue Berthelot", "72000", "Le Mans", "0243000000", "4657895453", "26/09/2012", 642.2));
        dbClients.insererClient(new Clients("DW01", "Who", "Doctor", "3 Rue de Beau Soleil", "72700", "Allonnes", "0243000000", "32156488465", "24/03/2012", 248.8));
        dbClients.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgButtonIdentification:
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                break;

            case R.id.imgButtonClients:
                intentAfficheListClients = new Intent(this, AfficheListeClientsActivity.class);
                this.startActivityForResult(intentAfficheListClients, 10);
                break;

            case R.id.imgButtonTransferts:
                Toast.makeText(this, "Transferts", Toast.LENGTH_SHORT).show();
                break;

            case R.id.imgButtonSauvegarde:
                Toast.makeText(this, "Sauvegarde", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}