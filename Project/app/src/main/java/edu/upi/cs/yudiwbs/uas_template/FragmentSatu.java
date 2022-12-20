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

//        binding.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.tvKeterangan.setText("Tunggu sebentar, loading data harga bitcoin (USD)");
//                //https://api.coindesk.com/v1/bpi/currentprice.json
//                Log.d("debug_ari","onclick");
//                ApiHargaBitcoin.get("bpi/currentprice.json", null, new JsonHttpResponseHandler() {
//                    @Override
//                    //hati2 success jsonobjek atau jsonarray
//                    public void onSuccess(int statusCode,
//                                          cz.msebera.android.httpclient.Header[] headers,
//                                          org.json.JSONObject response) {
//                        Log.d("debugyudi","onSuccess jsonobjek");
//
//                        /* hasil jsonnha
//                        {"time":{"updated":"Dec 19, 2022 09:53:00 UTC","updatedISO":"2022-12-19T09:53:00+00:00",
//                                "updateduk":"Dec 19, 2022 at 09:53 GMT"},
//
//                        "disclaimer":"This data was produced from the CoinDesk Bitcoin Price Index (USD).
//                              Non-USD currency data converted using hourly conversion rate from openexchangerates.org",
//                         "chartName":"Bitcoin",
//                         "bpi":{"USD":{"code":"USD","symbol":"&#36;","rate":"16,730.3955",
//                                    "description":"United States Dollar","rate_float":16730.3955},
//                                "GBP":{"code":"GBP","symbol":"&pound;","rate":"13,979.7846",
//                                  "description":"British Pound Sterling","rate_float":13979.7846},      "EUR":{"code":"EUR","symbol":"&euro;","rate":"16,297.8478","description":"Euro","rate_float":16297.8478}}}
//                         */
//
//                        //ambil USD rate
//                        String rate="";
//                        try {
//                            JSONObject bpi = response.getJSONObject("bpi"); // 4 adalah "bpi"
//                            JSONObject usd = bpi.getJSONObject("USD");
//                            rate = (String) usd.get("rate");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.e("debugyudi", "msg error" +":" +e.getMessage());
//                        }
//                        Log.d("debugyudi", "rate" +":" +rate);
//                        binding.tvKeterangan.setText(rate);
//                    }
//
//                    public void onSuccess(int statusCode,
//                                          cz.msebera.android.httpclient.Header[] headers,
//                                          org.json.JSONArray response) {
//
//                        Log.d("debugyudi","onSuccess jsonarray");
//
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                        Log.e("debugyudi", "error " + ":" + statusCode +":"+ errorResponse.toString());
//                    }
//                });
//
//            }
//        });

//        binding.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.tvKeterangan.setText("Tunggu sebentar, loading data bored activity");
//                //https://www.boredapi.com/api/activity
//                Log.d("debug_ari","onclick");
//                ApiBored.get(null, new JsonHttpResponseHandler() {
//                    @Override
//                    //hati2 success jsonobjek atau jsonarray
//                    public void onSuccess(int statusCode,
//                                          cz.msebera.android.httpclient.Header[] headers,
//                                          org.json.JSONObject response) {
//                        Log.d("debug_ari","onSuccess jsonobjek");
//
//                        /* hasil jsonnha
//                        {"time":{"updated":"Dec 19, 2022 09:53:00 UTC","updatedISO":"2022-12-19T09:53:00+00:00",
//                                "updateduk":"Dec 19, 2022 at 09:53 GMT"},
//
//                        "disclaimer":"This data was produced from the CoinDesk Bitcoin Price Index (USD).
//                              Non-USD currency data converted using hourly conversion rate from openexchangerates.org",
//                         "chartName":"Bitcoin",
//                         "bpi":{"USD":{"code":"USD","symbol":"&#36;","rate":"16,730.3955",
//                                    "description":"United States Dollar","rate_float":16730.3955},
//                                "GBP":{"code":"GBP","symbol":"&pound;","rate":"13,979.7846",
//                                  "description":"British Pound Sterling","rate_float":13979.7846},      "EUR":{"code":"EUR","symbol":"&euro;","rate":"16,297.8478","description":"Euro","rate_float":16297.8478}}}
//                         */
//
//                        //ambil USD rate
////                        String rate="";
//                        String activity = "";
//                        try {
////                            JSONObject bpi = response.getJSONObject("bpi"); // 4 adalah "bpi"
////                            JSONObject usd = bpi.getJSONObject("USD");
////                            rate = (String) usd.get("rate");
////                            JSONObject jsonActivity = response.getJSONObject("activity");
//                            activity = (String) response.get("activity");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.e("debug_ari", "msg error" +":" +e.getMessage());
//                        }
//                        Log.d("debug_ari", "activity" +":" +activity);
//                        binding.tvKeterangan.setText(activity);
//                    }
//
//                    public void onSuccess(int statusCode,
//                                          cz.msebera.android.httpclient.Header[] headers,
//                                          org.json.JSONArray response) {
//
//                        Log.d("debugyudi","onSuccess jsonarray");
//
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                        Log.e("debugyudi", "error " + ":" + statusCode +":"+ errorResponse.toString());
//                    }
//                });
//
//            }
//        });

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}



