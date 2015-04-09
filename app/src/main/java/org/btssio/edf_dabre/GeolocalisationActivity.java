package org.btssio.edf_dabre;

import android.app.Activity;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GeolocalisationActivity extends Activity implements LocationListener {

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private String provider, adresseClient;
    private LatLng positionClient, positionAgent;
    private boolean reussiGeolocalisationAgent = false, reussiGeolocalisationClient = false;
    private LatLngBounds.Builder builder = new LatLngBounds.Builder();
    private String id;
    private DbAdapter dbClients;
    private Clients client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalisation);

        id = this.getIntent().getExtras().getString("id");
        dbClients = new DbAdapter(this);
        dbClients.open();
        client = dbClients.getClientWithId(id);

        adresseClient = client.getAdresse() + ", " + client.getCodePostal() + " " + client.getVille() + ", France";

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(true);
        criteria.setSpeedRequired(false);

        provider = locationManager.getBestProvider(criteria, false);

        if (provider == null || provider.equals("")) {
            Toast.makeText(this, "Géolocalisation désactivée", Toast.LENGTH_SHORT).show();
        } else {
            recupPositionAgent();
            recupPositionClient();
            afficheCarte();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void recupPositionAgent() {
        locationManager.requestLocationUpdates(provider, 20000, 0, this);

        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            positionAgent = new LatLng(location.getLatitude(), location.getLongitude());

            reussiGeolocalisationAgent = true;
        } else {
            Toast.makeText(this, "Géolocalisation impossible", Toast.LENGTH_SHORT).show();
        }
    }

    public void recupPositionClient() {
        Geocoder fwdGeocoder = new Geocoder(this, Locale.FRANCE);

        List<Address> locations = null;

        try {
            locations = fwdGeocoder.getFromLocationName(adresseClient, 10);
        } catch (IOException e) {
        }

        if ((locations == null) || (locations.isEmpty())) {
            Toast.makeText(this, "Adresse client introuvable", Toast.LENGTH_SHORT).show();
        } else {
            positionClient = new LatLng(locations.get(0).getLatitude(), locations.get(0).getLongitude());
            reussiGeolocalisationClient = true;
        }
    }

    public void afficheCarte() {
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        if (reussiGeolocalisationClient) {
            googleMap.addMarker(new MarkerOptions().position(positionClient).title("Client").snippet(adresseClient).icon(BitmapDescriptorFactory.fromResource(R.drawable.blupushpin)));
            builder.include(positionClient);
        }
        if (reussiGeolocalisationAgent) {

            googleMap.addMarker(new MarkerOptions().position(positionAgent).title("Position actuelle"));
            builder.include(positionAgent);
        }

        if (reussiGeolocalisationClient && reussiGeolocalisationAgent) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), this.getResources().getDisplayMetrics().widthPixels, this.getResources().getDisplayMetrics().heightPixels, 100));

        }
    }
}
