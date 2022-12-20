package edu.upi.cs.yudiwbs.uas_template;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.upi.cs.yudiwbs.uas_template.databinding.FragmentDuaBinding;
import edu.upi.cs.yudiwbs.uas_template.databinding.FragmentSatuBinding;

public class FragmentDua extends Fragment implements SensorEventListener {

    private FragmentDuaBinding binding;

    ArrayList<Hasil> alHasil = new ArrayList<>();
    AdapterHasil adapter;
    RecyclerView.LayoutManager lm;

    // sensor
    private SensorManager sm;
    private Sensor senAccel;
    private boolean isTerangkat;
    private int writen;

    public FragmentDua() {
        // Required empty public constructor
    }

    public static FragmentDua newInstance(String param1, String param2) {
        FragmentDua fragment = new FragmentDua();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDuaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        adapter = new AdapterHasil(alHasil);
        binding.rvHasil.setAdapter(adapter);

        lm = new LinearLayoutManager(getActivity());
        binding.rvHasil.setLayoutManager(lm);

        //supaya ada garis antar row
        binding.rvHasil.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        //cek apakah sensor tersedia
        sm = (SensorManager)    getActivity().getSystemService(getActivity().getApplicationContext().SENSOR_SERVICE);
        senAccel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (senAccel != null){
            Log.d("debug_ari_sensor","Sukses, device punya sensor accelerometer!");
        }
        else {
            // gagal, tidak ada sensor accelerometer.
            Log.d("debug_ari_sensor","Tidak ada sensor accelerometer!");
        }

        binding.buttonFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
                String ts = s.format(new Date());
                alHasil.add(new Hasil(ts));
                alHasil.add(new Hasil("HP Diangkat!"));
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double ax=0,ay=0,az=0;
        // menangkap perubahan nilai sensor
        if (sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            ax=sensorEvent.values[0];
            ay=sensorEvent.values[1];
            az=sensorEvent.values[2];
        }
        if  (ay>=6) {
            isTerangkat = true;
        }
        if (isTerangkat) {
            if(writen == 0){
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
                String ts = s.format(new Date());
                alHasil.add(new Hasil(ts));
                alHasil.add(new Hasil("HP Diangkat!"));
                adapter.notifyDataSetChanged();
                // jika sudah ditulis terangkat setelah sensor mendeteksi hp terangkat,
                // tandai sudah ditulis ke recycler view
                writen = 1;
            }
            isTerangkat = false;
        } else{
            long timestamp = System.currentTimeMillis();
            // Menampilkan log dari accelerometer beserta timestamp
            String msg = "X: " + ax + ", Y: " + ay + ", Z: " + az + ", Timestamp: " + timestamp;
            Log.d("debug_yudi", msg);
            writen = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener(this, senAccel, SensorManager.SENSOR_DELAY_NORMAL);
    }

}