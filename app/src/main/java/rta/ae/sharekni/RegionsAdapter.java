package rta.ae.sharekni;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Nezar Saleh on 10/21/2015.
 */


public class RegionsAdapter extends ArrayAdapter<RegionsDataModel> {

    private LayoutInflater inflater;
    int resourse;
    Context context;


    private final String MY_DEBUG_TAG = "CustomerAdapter";
    private ArrayList<RegionsDataModel> items;

    private ArrayList<RegionsDataModel> itemsAll;
    private ArrayList<RegionsDataModel> suggestions;


    public RegionsAdapter(Context context, int resource, ArrayList<RegionsDataModel> items) {
        super(context, resource, items);
        this.items = items;
        this.context = context;
        this.resourse = resource;
        this.itemsAll = (ArrayList<RegionsDataModel>) items.clone();
        this.suggestions = new ArrayList<RegionsDataModel>();


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;
        ViewHolder vh;

        try {
            if (v == null) {
                v = inflater.inflate(resourse, parent, false);
                vh = new ViewHolder();
                vh.RegionEnName = (TextView) v.findViewById(R.id.row_name_search);
                v.setTag(vh);
            } else {
                vh = (ViewHolder) v.getTag();
            }
            RegionsDataModel reg = items.get(position);
            vh.RegionEnName.setText(reg.getRegionEnName());
            vh.Regions_Id = reg.getID();
            vh.RegionLatitude = reg.RegionLatitude;
            vh.RegionLongitude = reg.RegionLongitude;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;

    }


    static class ViewHolder {


        int Regions_Id;
        TextView RegionEnName;
        Double RegionLatitude, RegionLongitude;
    }


    @Override
    public Filter getFilter() {
        return nameFilter;
    }


    Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            if (constraint != null) {
                suggestions.clear();
                for (RegionsDataModel customer : itemsAll) {
                    if (customer.RegionEnName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
// Throug an error Here ConcurrentModificationException
            ArrayList<RegionsDataModel> filteredList = (ArrayList<RegionsDataModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (RegionsDataModel c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }


        }

        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((RegionsDataModel) (resultValue)).getRegionEnName();
            return str;
        }


    };


}
