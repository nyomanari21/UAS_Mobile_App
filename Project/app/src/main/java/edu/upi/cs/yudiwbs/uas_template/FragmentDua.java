package edu.upi.cs.yudiwbs.uas_template;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.upi.cs.yudiwbs.uas_template.databinding.FragmentDuaBinding;
import edu.upi.cs.yudiwbs.uas_template.databinding.FragmentSatuBinding;

public class FragmentDua extends Fragment {

    private FragmentDuaBinding binding;

    ArrayList<Hasil> alHasil = new ArrayList<>();
    AdapterHasil adapter;
    RecyclerView.LayoutManager lm;


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

        binding.buttonFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Load data dari API
                ApiBored.get(null, new JsonHttpResponseHandler() {
                    @Override
                    //hati2 success jsonobjek atau jsonarray
                    public void onSuccess(int statusCode,
                                          cz.msebera.android.httpclient.Header[] headers,
                                          org.json.JSONObject response) {
                        Log.d("debug_ari","onSuccess jsonobjek");

                        //ambil type dan activity
                        String type = "";
                        String activity = "";
                        try {
                            type = (String) response.get("type");
                            activity = (String) response.get("activity");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("debug_ari", "msg error" +":" +e.getMessage());
                        }
                        // Tambahkan hasil data dari api ke recycler view
                        Log.d("debug_ari", "activity" +":" +activity);
                        alHasil.add(new Hasil(type));
                        alHasil.add(new Hasil(activity));
                        adapter.notifyDataSetChanged();
                    }

                    public void onSuccess(int statusCode,
                                          cz.msebera.android.httpclient.Header[] headers,
                                          org.json.JSONArray response) {
                        Log.d("debug_ari","onSuccess jsonarray");
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.e("debug_ari", "error " + ":" + statusCode +":"+ errorResponse.toString());
                    }
                });

//                alHasil.add(new Hasil("satu..."));
//                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}