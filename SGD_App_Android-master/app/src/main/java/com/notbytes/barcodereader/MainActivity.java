package com.notbytes.barcodereader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.notbytes.barcode_reader.BarcodeReaderFragment;
import com.notbytes.barcodereader.Model.Posts;
import com.notbytes.barcodereader.io.APIRetrofitInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BarcodeReaderFragment.BarcodeReaderListener {
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    private TextView mTvResult;
    private TextView mTvResultHeader;
    private TextView mJsonTxtView;
    private TextView Usuario;
    private ListView listView;
    private TextView contarelementos;
    private TextView abc;
    int totalelementoslist = 1;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView latTextView, lonTextView;
    //private Spinner spin;
    ArrayList<String> list2;

    private APIRetrofitInterface jsonPlaceHolderApi;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btones
        findViewById(R.id.btn_activity).setOnClickListener(this);
        findViewById(R.id.btn_fragment).setOnClickListener(this);
        //txtvws
        //mJsonTxtView = findViewById(R.id.tv_result);
        //mTvResultHeader = findViewById(R.id.tv_result_head);
        //mTvResult = findViewById(R.id.tv_result);
        contarelementos = findViewById(R.id.contarelementos);
        Usuario = findViewById(R.id.usuarios);

        //GPS
        //latTextView = findViewById(R.id.latTextView);
        //lonTextView = findViewById(R.id.lonTextView);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        //GPS

        //Lista--------------------------------------------------------
        listView = findViewById(R.id.listview);
        String[] android_flavours = {};
        arrayList = new ArrayList<>(Arrays.asList(android_flavours));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        // Result value = barcode.receiveScan();

        //arrayList.add(barcode.rawValue);
        for (int i = 0; i < android_flavours.length; i++) {
            arrayList.add(android_flavours[i]);

            HashSet<String> hashSet = new HashSet<String>();
            hashSet.addAll(arrayList);
            arrayList.clear();
            arrayList.addAll(hashSet);
        }

        //listarpro = findViewById(R.id.lista);
        //String resul = mTvResult.getText().toString();----------------

        //Retrofit------------------------------------------------------

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiCyT/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);

        //createPost();
        // Redirección al Login
        //    if(true){
        //    startActivity(new Intent(this, LoginActivity.class));
        //    finish();
        //    return;
        // }

        //Fill Spinner----------------------------------
        //FillSpinner();
        //----------------------------------------------
        //Llamar datos usuario--------------------------
        Bundle b = getIntent().getExtras();

        String receivingdata = b.getString("Key");
        //String usu = receivingdata.toString();
        //TextView tv = findViewById(R.id.usuarios);
        //tv.append(receivingdata);
        //----------------------------------------------

        //Sonido beep
        //final MediaPlayer mp = MediaPlayer.create(this,R.raw.beep);

    }

    /*
        private void FillSpinner(){

            spin = findViewById(R.id.spinner);
            //String[] wee = list2.toArray(new String[list2.size()]);
            //final String[] str={"Report 1","Report 2","Report 3","Report 4","Report 5"};
            ArrayList<String> str = new ArrayList<>();
            str.add(new String("Recibido en MIAMI"));
            str.add(new String("En tránsito"));
            str.add(new String("En almacén Scharff(LIMA)"));
            str.add(new String("Entregado"));

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_dropdown_item, str);
            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
    // Spinner spinYear = (Spinner)findViewById(R.id.spin);
            spin.setAdapter(adapter);
        }
    */
    private void createPost() {
        //mTvResult = findViewById(R.id.tv_result);
        //listView= findViewById(R.id.listview);
        //Spinner mySpinner = findViewById(R.id.spinner);
        //final String Estado = mySpinner.getSelectedItem().toString();

        //String itemValue = (String) listView.getItemAtPosition(position);
        //String values=((TextView)view).getText().toString();
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                //Llamar datos usuario--------------------------
                                Bundle b = getIntent().getExtras();
                                String receivingdata = b.getString("Key");
                                String spinnerdata = b.getString("Key2");
                                //String usu = receivingdata.toString();
                                //TextView tv = findViewById(R.id.usuarios);
                                //tv.append(receivingdata);
                                //----------------------------------------------

                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    View parentView = null;
                                    int position = 0;
                                    for (int i = 0; i < listView.getCount(); i++) {
                                        parentView = getViewByPosition(i, listView);

                                        String textItemList = (String) listView.getItemAtPosition(position);
                                        position++;
                                        //Aqui enviar los datos
                                        //String resul = mTvResult.getText().toString();
                                        Posts posts = new Posts(textItemList, "" + receivingdata, "" + location.getLatitude(), "" + location.getLongitude() + "", "" + spinnerdata/*+Estado*/);
                                        Call<Posts> call = jsonPlaceHolderApi.createPost(posts);
                                        call.enqueue(new Callback<Posts>() {
                                            @Override
                                            public void onResponse(Call<Posts> call, Response<Posts> response) {
                                                if (!response.isSuccessful()) {
                                                    //mJsonTxtView.setText("Codigo:" + response.code());
                                                    Toast.makeText(getApplicationContext(), "Datos ingresados exitosamente", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                Posts postsResponse = response.body();
                                                String content = "";
                                                //content += "Estado:" + postsResponse.estado() + "\n";
                                                content += "Mensaje:" + postsResponse.mensaje() + "\n";
                                                Toast.makeText(getApplicationContext(), "Datos ingresados exitosamente", Toast.LENGTH_SHORT).show();
                                                //mJsonTxtView.append(content);

                                            }

                                            @Override
                                            public void onFailure(Call<Posts> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                                //mJsonTxtView.setText(t.getMessage());
                                            }
                                        });
                                    }
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }

    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void addBarcodeReaderFragment() {
        BarcodeReaderFragment readerFragment = BarcodeReaderFragment.newInstance(true, false, View.VISIBLE);
        readerFragment.setListener(this);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fm_container, readerFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent i = new Intent(MainActivity.this, Logeo.class);
                startActivity(i);
                break;
            case R.id.btn_fragment:
                addBarcodeReaderFragment();
                addBarcodeReaderFragment();
                break;
            case R.id.btn_activity:
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                Fragment fragmentById = supportFragmentManager.findFragmentById(R.id.fm_container);
                if (fragmentById != null) {
                    fragmentTransaction.remove(fragmentById);
                }
                fragmentTransaction.commitAllowingStateLoss();
                launchBarCodeActivity();
                break;
            case R.id.btnConfirmarDatos:
                createPost();
                break;
        }
        //Re-dirige al activity principal(login)
        //Intent i = new Intent(MainActivity.this, LoginActivity.class);
        //startActivity(i);
    }

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
            Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();

            //Agrega datos al textview
            //mTvResult.setText("Último valor scaneado: "+barcode.rawValue);
            //Agregar datos a la list

            arrayList.add(barcode.rawValue);
            adapter.notifyDataSetChanged();

            HashSet<String> hashSet = new HashSet<String>();
            hashSet.addAll(arrayList);
            arrayList.clear();
            arrayList.addAll(hashSet);
            //Contar elementos del spinner ~
            int count = listView.getAdapter().getCount();
            int ctf= totalelementoslist+count;
            contarelementos.setText(""+count);
            //---

            //Sonido beep
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.beeps);
            mp.start();
            //--
            //mTvResultHeader.setText("Resultado");

            //mTvResult.setText(barcode.rawValue);
        }
    }

    @Override
    public void onScanned(Barcode barcode) {

        Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();
        //mTvResultHeader.setText("Datos generales");
     /*   //Lista---------------------------------------------------------
        listView= findViewById(R.id.listview);
        String[]android_flavours={};
        arrayList= new ArrayList<>(Arrays.asList(android_flavours));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
        // Result value = barcode.receiveScan();


        //arrayList.add(barcode.rawValue);
        for (int i = 0; i < android_flavours.length; i++) {
            arrayList.add(android_flavours[i]);
            //Agregar datos a la list
        }
*/
        //Agregar datos a la list
        //Agrega datos al textview
        //mTvResult.setText("Último valor scaneado: "+barcode.rawValue+".");
        arrayList.add(barcode.rawValue);

        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(arrayList);
        arrayList.clear();
        arrayList.addAll(hashSet);

        //Contar elementos del spinner ~
        int count = listView.getAdapter().getCount();
        int ctf= totalelementoslist+count;
        contarelementos.setText(""+count);
        //---

        //Sonido beep
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.beeps);
        mp.start();
        //--

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_LONG).show();
    }

    //GPS
    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    //latTextView.setText("Latitud actual: "+location.getLatitude()+".");
                                    //lonTextView.setText("Longitud actual: "+location.getLongitude()+".");
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //latTextView.setText(mLastLocation.getLatitude()+"");
            //lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };

    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }
    //FIN GPS
}
