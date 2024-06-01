package com.example.safecityapp;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends NavMenuActivity implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap myMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_maps, findViewById(R.id.fragment_container));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);

            // Se a permissão for negada, defina a localização para Guarulhos
            currentLocation = new Location("");
            currentLocation.setLatitude(-23.4676);
            currentLocation.setLongitude(-46.5278);

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
            mapFragment.getMapAsync(MapsActivity.this);

            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    currentLocation = location;
                } else {
                    // Se a localização for nula, defina a localização para Guarulhos
                    currentLocation = new Location("");
                    currentLocation.setLatitude(-23.4676);
                    currentLocation.setLongitude(-46.5278);
                }

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
                mapFragment.getMapAsync(MapsActivity.this);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        LatLng guarulhos = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        myMap.addMarker(new MarkerOptions()
                .position(guarulhos)
                .title("Localização Atual"));

        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(guarulhos, 13.0f));

        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);

        // Adicionar círculos transparentes
        adicionarCirculos(myMap);

       // Adicionar polígono para delimitar Guarulhos
        // adicionarPoligonoGuarulhos(myMap);
    }

    private void adicionarCirculos(GoogleMap googleMap) {
        LatLng[] locais = {
                new LatLng(-23.422455, -46.502131),
                new LatLng(-23.409479, -46.458937),
                new LatLng(-23.456100, -46.465460),
                new LatLng(-23.435154, -46.517216),
                new LatLng(-23.442162, -46.547171),
                new LatLng(-23.449722, -46.502453),
                new LatLng(-23.471059, -46.533180),
                new LatLng(-23.475310, -46.547771),
                new LatLng(-23.453501, -46.426493),
                new LatLng(-23.438461, -46.462284),
                new LatLng(-23.442871, -46.401773)
        };

        int[] raios = {1000, 800, 600, 400, 200, 900, 700, 500, 300, 100, 1000};
        int[] cores = {
                0x66FF0000, // vermelho com 40% de opacidade
                0x44FF0000, // vermelho com 26.7% de opacidade
                0x55FF0000, // vermelho com 33.3% de opacidade
                0x33FF0000, // vermelho com 20% de opacidade
                0x22FF0000, // vermelho com 13.3% de opacidade
                0x77FF0000, // vermelho com 46.7% de opacidade
                0x88FF0000, // vermelho com 53.3% de opacidade
                0x99FF0000, // vermelho com 60% de opacidade
                0x55FF0000, // vermelho com 33.3% de opacidade
                0x44FF0000, // vermelho com 26.7% de opacidade
                0x66FF0000  // vermelho com 40% de opacidade
        };

        for (int i = 0; i < locais.length; i++) {
            googleMap.addCircle(new CircleOptions()
                    .center(locais[i])
                    .radius(raios[i]) // raio em metros
                    .strokeColor(0xFFFF0000) // borda vermelha
                    .fillColor(cores[i]) // vermelho com transparência
                    .strokeWidth(2));
        }
    }

//    private void adicionarPoligonoGuarulhos(GoogleMap googleMap) {
//        LatLng[] coordenadasGuarulhos = {
//                new LatLng(-23.4152, -46.4854),
//                new LatLng(-23.4225, -46.4944),
//                new LatLng(-23.4267, -46.5084),
//                new LatLng(-23.4309, -46.5202),
//                new LatLng(-23.4371, -46.5295),
//                new LatLng(-23.4478, -46.5374),
//                new LatLng(-23.4617, -46.5392),
//                new LatLng(-23.4700, -46.5320),
//                new LatLng(-23.4733, -46.5187),
//                new LatLng(-23.4786, -46.5089),
//                new LatLng(-23.4856, -46.4957),
//                new LatLng(-23.4917, -46.4874),
//                new LatLng(-23.4944, -46.4700),
//                new LatLng(-23.4897, -46.4593),
//                new LatLng(-23.4753, -46.4547),
//                new LatLng(-23.4593, -46.4547),
//                new LatLng(-23.4484, -46.4630),
//                new LatLng(-23.4358, -46.4708),
//                new LatLng(-23.4234, -46.4786),
//                new LatLng(-23.4152, -46.4854) // Fechar o polígono
//        };
//
//        PolygonOptions polygonOptions = new PolygonOptions()
//                .add(coordenadasGuarulhos)
//                .strokeColor(0xFF0000FF) // Cor da borda (azul)
//                .strokeWidth(3)
//                .fillColor(0x220000FF); // Azul com transparência
//
//        googleMap.addPolygon(polygonOptions);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Este aplicativo precisa da sua localização para funcionar corretamente.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
