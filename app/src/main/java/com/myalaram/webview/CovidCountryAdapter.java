package com.myalaram.webview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CovidCountryAdapter extends RecyclerView.Adapter<CovidCountryAdapter.ViewHolder> implements Filterable{


    private List<CovidCountry> covidCountries;
    private List<CovidCountry> currentlist;
    View view;

    private Context context;


    public CovidCountryAdapter(List<CovidCountry> covidCountries, Context context){
        this.covidCountries=covidCountries;
        this.context=context;
        currentlist=new ArrayList<>(covidCountries);

    }




    @NonNull
    @Override
    public CovidCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_covid_country,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidCountryAdapter.ViewHolder holder, int position) {


        CovidCountry covidCountry=covidCountries.get(position);
        holder.tvtotalcases.setText(covidCountry.getMcases());
        holder.tvcountryname.setText(covidCountry.getmCovidCountry());

        Glide.with(context)
                .load(covidCountry.getmFlage())
                .apply(new RequestOptions().override(240,160))
                .into(holder.countryflage );


    }



    @Override
    public int getItemCount() {
        return covidCountries.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvtotalcases,tvcountryname;
        ImageView countryflage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtotalcases=itemView.findViewById(R.id.tvTotalcases);
            tvcountryname=itemView.findViewById(R.id.tvcountaryname);
            countryflage=itemView.findViewById(R.id.tvcountryflag);




        }
    }

    @Override
    public Filter getFilter() {
        return coviedcountryfilter;
    }
    private Filter coviedcountryfilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CovidCountry> filteredcovidcountry = new ArrayList<>();
            if(constraint==null||constraint.length()==0){
                filteredcovidcountry.addAll(currentlist);
            }else{
                String filterpattren = constraint.toString().toLowerCase().trim();
                for(CovidCountry itemcovidcountry : currentlist){
                    if(itemcovidcountry.getmCovidCountry().toLowerCase().contains(filterpattren)){
                        filteredcovidcountry.add(itemcovidcountry);
                    }
                }
            }

            FilterResults result = new FilterResults();
            result.values=filteredcovidcountry;
            return result;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                covidCountries.clear();
                covidCountries.addAll((List) results.values);
                notifyDataSetChanged();
        }
    };

}
