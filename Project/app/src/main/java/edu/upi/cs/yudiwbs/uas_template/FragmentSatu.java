package edu.upi.cs.yudiwbs.uas_template;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpStatus;
import edu.upi.cs.yudiwbs.uas_template.databinding.FragmentSatuBinding;


public class FragmentSatu extends Fragment {

    private FragmentSatuBinding binding;
    private ViewModelRVBoredApi vmRVBoredApi;

    ArrayList<Hasil> alHasil = new ArrayList<>();
    AdapterHasil adapter;
    RecyclerView.LayoutManager lm;

    public FragmentSatu() {
        // Required empty public constructor
    }

    public static FragmentSatu newInstance(String param1, String param2) {
        FragmentSatu fragment = new FragmentSatu();
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
        binding = FragmentSatuBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        vmRVBoredApi = new ViewModelProvider(getActivity()).get(ViewModelRVBoredApi.class);

        adapter = new AdapterHasil(alHasil);
        binding.rvHasilFragSatu.setAdapter(adapter);

        lm = new LinearLayoutManager(getActivity());
        binding.rvHasilFragSatu.setLayoutManager(lm);

        //supaya ada garis antar row
        binding.rvHasilFragSatu.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        binding.button.setOnClickListener(new View.OnClickListener() {
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

            }
        });

        return view;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        lm = new LinearLayoutManager(getActivity());
        //restore state recylerview
        if (vmRVBoredApi.getRecViewState().getValue()!=null) {
            lm.onRestoreInstanceState(vmRVBoredApi.getRecViewState().getValue());
        }
        binding.rvHasilFragSatu.setLayoutManager(lm);
    }

    @Override
    public void onPause() {
        super.onPause();
        //simpan state ke viewmodel vmRVBoredApi
        vmRVBoredApi.setRecViewState(lm.onSaveInstanceState());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}



