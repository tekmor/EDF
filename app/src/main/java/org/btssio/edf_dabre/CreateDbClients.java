package org.btssio.edf_dabre;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDbClients extends SQLiteOpenHelper {

    private static final String TABLE_CLIENTS = "table_clients";
    private static final String COL_ID = "_id";
    private static final String COL_NOM = "NOM";
    private static final String COL_PRENOM = "PRENOM";
    private static final String COL_ADRESSE = "ADRESSE";
    private static final String COL_CP = "CP";
    private static final String COL_VILLE = "VILLE";
    private static final String COL_TEL = "TEL";
    private static final String COL_IDCOMPTEUR = "IDCOMPTEUR";
    private static final String COL_DATEANCIENRELEVE = "DATEANCIENRELEVE";
    private static final String COL_ANCIENRELEVE = "ANCIENRELEVE";
    private static final String COL_DATEDERNIERRELEVE = "DATEDERNIERRELEVE";
    private static final String COL_SIGNATUREBASE64 = "SIGNATUREBASE64";
    private static final String COL_DERNIERRELEVE = "DERNIERRELEVE";
    private static final String COL_SITUATION = "SITUATION";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_CLIENTS
            + " (" + COL_ID + " TEXT PRIMARY KEY," + COL_NOM + " TEXT,"
            + COL_PRENOM + " TEXT," + COL_ADRESSE + " TEXT," + COL_CP
            + " TEXT," + COL_VILLE + " TEXT," + COL_TEL + " TEXT,"
            + COL_IDCOMPTEUR + " TEXT," + COL_DATEANCIENRELEVE + " TEXT,"
            + COL_ANCIENRELEVE + " REAL," + COL_DATEDERNIERRELEVE + " TEXT,"
            + COL_SIGNATUREBASE64 + " TEXT," + COL_DERNIERRELEVE + " REAL,"
            + COL_SITUATION + " INTEGER" + ");";

    public CreateDbClients(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_CLIENTS + ";");
        onCreate(db);
    }
}