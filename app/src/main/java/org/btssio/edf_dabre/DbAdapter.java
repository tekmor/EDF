package org.btssio.edf_dabre;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DbAdapter {
    static final int VERSION_BDD = 1;
    static final String TABLE_CLIENTS = "table_clients";
    static final String COL_ID = "_id";
    static final int NUM_COL_ID = 0;
    static final String COL_NOM = "NOM";
    static final int NUM_COL_NOM = 1;
    static final String COL_PRENOM = "PRENOM";
    static final int NUM_COL_PRENOM = 2;
    static final String COL_ADRESSE = "ADRESSE";
    static final int NUM_COL_ADRESSE = 3;
    static final String COL_CP = "CP";
    static final int NUM_COL_CP = 4;
    static final String COL_VILLE = "VILLE";
    static final int NUM_COL_VILLE = 5;
    static final String COL_TEL = "TEL";
    static final int NUM_COL_TEL = 6;
    static final String COL_IDCOMPTEUR = "IDCOMPTEUR";
    static final int NUM_COL_IDCOMPTEUR = 7;
    static final String COL_DATEANCIENRELEVE = "DATEANCIENRELEVE";
    static final int NUM_COL_DATEANCIENRELEVE = 8;
    static final String COL_ANCIENRELEVE = "ANCIENRELEVE";
    static final int NUM_COL_ANCIENRELEVE = 9;
    static final String COL_DATEDERNIERRELEVE = "DATEDERNIERRELEVE";
    static final int NUM_COL_DATEDERNIERRELEVE = 10;
    static final String COL_DERNIERRELEVE = "DERNIERRELEVE";
    static final int NUM_COL_DERNIERRELEVE = 11;
    static final String COL_SIGNATUREBASE64 = "SIGNATUREBASE64";
    static final int NUM_COL_SIGNATUREBASE64 = 12;
    static final String COL_SITUATION = "SITUATION";
    static final int NUM_COL_SITUATION = 13;
    private static final String NOM_BDD = "clients.db";
    private CreateDbClients dbClients;
    private Context context;
    private SQLiteDatabase db;

    /**
     * @param context
     */
    public DbAdapter(Context context) {
        this.context = context;
        dbClients = new CreateDbClients(context, NOM_BDD, null, VERSION_BDD);
    }

    /**
     * Permet d'ouvrir la connexion à la base
     */
    public DbAdapter open() {
        db = dbClients.getWritableDatabase();
        return this;
    }

    /**
     * Permet de fermer la connexion à la base
     */
    public DbAdapter close() {
        db.close();
        return null;
    }

    /**
     * Permet d'insérer un client
     *
     * @param client
     */
    public long insererClient(Clients client) {
        ContentValues values = new ContentValues();

        values.put(COL_ID, client.getIdentifiant());
        values.put(COL_NOM, client.getNom());
        values.put(COL_PRENOM, client.getPrenom());
        values.put(COL_ADRESSE, client.getAdresse());
        values.put(COL_CP, client.getCodePostal());
        values.put(COL_VILLE, client.getVille());
        values.put(COL_TEL, client.getTelephone());
        values.put(COL_IDCOMPTEUR, client.getIdCompteur());
        values.put(COL_DATEANCIENRELEVE, client.getDateAncienReleve());
        values.put(COL_ANCIENRELEVE, client.getAncienReleve());
        values.put(COL_DATEDERNIERRELEVE, client.getDateDernierReleve());
        values.put(COL_DERNIERRELEVE, client.getDernierReleve());
        values.put(COL_SIGNATUREBASE64, client.getSignatureBase64());
        values.put(COL_SITUATION, client.getSituation());

        return db.insert(TABLE_CLIENTS, null, values);
    }

    /**
     * Récupère le client avec son ID
     * @param identifiant
     * @return le client avec son ID
     */
    public Clients getClientWithId(String identifiant) {
        Cursor c = db.query(TABLE_CLIENTS, new String[]{COL_ID, COL_NOM, COL_PRENOM, COL_ADRESSE,
                COL_CP, COL_VILLE, COL_TEL, COL_IDCOMPTEUR,
                COL_DATEANCIENRELEVE, COL_ANCIENRELEVE,
                COL_DATEDERNIERRELEVE, COL_DERNIERRELEVE,
                COL_SIGNATUREBASE64, COL_SITUATION}, COL_ID + " LIKE '" + identifiant + "'", null, null, null, null);
        return cursorToClient(c);
    }

    private Clients cursorToClient(Cursor c) {
        if (c.getCount() == 0) {
            return null;
        }

        c.moveToFirst();

        Clients client = new Clients();

        client.setIdentifiant(c.getString(NUM_COL_ID));
        client.setNom(c.getString(NUM_COL_NOM));
        client.setPrenom(c.getString(NUM_COL_PRENOM));
        client.setAdresse(c.getString(NUM_COL_ADRESSE));
        client.setCodePostal(c.getString(NUM_COL_CP));
        client.setVille(c.getString(NUM_COL_VILLE));
        client.setTelephone(c.getString(NUM_COL_TEL));
        client.setIdCompteur(c.getString(NUM_COL_IDCOMPTEUR));
        client.setDateAncienReleve(c.getString(NUM_COL_DATEANCIENRELEVE));
        client.setAncienReleve(c.getDouble(NUM_COL_ANCIENRELEVE));
        client.setDateDernierReleve(c.getString(NUM_COL_DATEDERNIERRELEVE));
        client.setDernierReleve(c.getDouble(NUM_COL_DERNIERRELEVE));
        client.setSignatureBase64(c.getString(NUM_COL_SIGNATUREBASE64));
        client.setSituation(c.getInt(NUM_COL_SITUATION));

        c.close();

        return client;
    }

    /**
     * Mise à jour d'un client
     * @param identifiant
     * @param client
     */
    public int updateClient(String identifiant, Clients client) {
        ContentValues values = new ContentValues();

        values.put(COL_ID, client.getIdentifiant());
        values.put(COL_NOM, client.getNom());
        values.put(COL_PRENOM, client.getPrenom());
        values.put(COL_ADRESSE, client.getAdresse());
        values.put(COL_CP, client.getCodePostal());
        values.put(COL_VILLE, client.getVille());
        values.put(COL_TEL, client.getTelephone());
        values.put(COL_IDCOMPTEUR, client.getIdCompteur());
        values.put(COL_DATEANCIENRELEVE, client.getDateAncienReleve());
        values.put(COL_ANCIENRELEVE, client.getAncienReleve());
        values.put(COL_DATEDERNIERRELEVE, client.getDateDernierReleve());
        values.put(COL_DERNIERRELEVE, client.getDernierReleve());
        values.put(COL_SIGNATUREBASE64, client.getSignatureBase64());
        values.put(COL_SITUATION, client.getSituation());

        return db.update(TABLE_CLIENTS, values, COL_ID + " = '" + identifiant + "'", null);
    }

    /**
     * Supprime un client avec son ID
     * @param identifiant
     */
    public int removeClientWithID(String identifiant) {
        return db.delete(TABLE_CLIENTS, COL_ID + " = '" + identifiant + "'", null);
    }

    public List<Clients> getListClients() {
        List<Clients> listeDesClients = new ArrayList<Clients>();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CLIENTS, null);

        if (c.getCount() == 0) {
            return listeDesClients;
        }

        c.moveToFirst();

        do {
            listeDesClients.add(new Clients(c.getString(NUM_COL_ID),
                            c.getString(NUM_COL_NOM),
                            c.getString(NUM_COL_PRENOM),
                            c.getString(NUM_COL_ADRESSE),
                            c.getString(NUM_COL_CP),
                            c.getString(NUM_COL_VILLE),
                            c.getString(NUM_COL_TEL),
                            c.getString(NUM_COL_IDCOMPTEUR),
                            c.getString(NUM_COL_DATEANCIENRELEVE),
                            c.getDouble(NUM_COL_ANCIENRELEVE)
                    )
            );
        } while (c.moveToNext());

        c.close();
        return listeDesClients;
    }

    public Cursor getData() {
        return db.rawQuery("SELECT * FROM TABLE_CLIENTS", null);
    }
}