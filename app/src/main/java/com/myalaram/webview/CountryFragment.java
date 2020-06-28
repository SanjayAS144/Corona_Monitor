package com.myalaram.webview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountryFragment extends Fragment {
    RecyclerView rvCovidCountry;
    ProgressBar prograssbar;
    CovidCountryAdapter covidCountryAdapter;

    private static final String TAG = CountryFragment.class.getSimpleName();

    List<CovidCountry> covidCountries;
    SearchView searchView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_country, container, false);

        rvCovidCountry=root.findViewById(R.id.rvCovidcountry);
        prograssbar=root.findViewById(R.id.progress_circular_country);
        rvCovidCountry.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView=root.findViewById(R.id.searchview);
        searchView.setQueryHint("Search...");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(covidCountryAdapter != null){
                    covidCountryAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCovidCountry.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.line_divider));
        rvCovidCountry.addItemDecoration(dividerItemDecoration);


        covidCountries = new ArrayList<>();

        getDataFromServer();
        showRecyclerView();




        return root;
    }

    private void showRecyclerView(){

        covidCountryAdapter = new CovidCountryAdapter(covidCountries,getActivity());
        rvCovidCountry.setAdapter(covidCountryAdapter);

        ItemClickSupport.addTo((rvCovidCountry)).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showonselect(covidCountries.get(position));
            }
        });

    }


    public void showonselect(CovidCountry covidCountry){
        Intent covidcountrydetails=new Intent(getActivity(), CovidCountrydetailsof.class);
        covidcountrydetails.putExtra("EXTRA_COVID",covidCountry);
        startActivity(covidcountrydetails);
    }

    private void getDataFromServer() {


        String url =" https://corona.lmao.ninja/v2/countries";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prograssbar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse:" + response);
                    try {

                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            JSONObject countryflag=data.getJSONObject("countryInfo");

                            covidCountries.add(new CovidCountry(data.getString("country"),
                                    data.getString("cases"),
                                    data.getString("todayCases"),
                                    data.getString("deaths"),
                                    data.getString("todayDeaths"),
                                    data.getString("recovered"),
                                    countryflag.getString("flag")));

                        }showRecyclerView();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prograssbar.setVisibility(View.GONE);
                        Log.e(TAG,"onResponse:" +error);
                    }
                });
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }
}
