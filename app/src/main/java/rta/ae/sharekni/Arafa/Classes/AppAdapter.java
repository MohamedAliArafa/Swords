package rta.ae.sharekni.Arafa.Classes;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rta.ae.sharekni.R;

/**
 * Created by Amgad on 09-Jun-16.
 */
public class AppAdapter extends ArrayAdapter<ResolveInfo> {
    private PackageManager pm = null;
    private Context context;
    public AppAdapter(Context context, PackageManager pm, List<ResolveInfo> apps) {
        super(context, R.layout.share_dialog_row, apps);
        this.pm = pm;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = newView(parent);
        }

        bindView(position, convertView);

        return (convertView);
    }

    private View newView(ViewGroup parent) {
        return (LayoutInflater.from(context).inflate(R.layout.share_dialog_row, parent, false));
    }

    private void bindView(int position, View row) {
        TextView label = (TextView) row.findViewById(R.id.label);

        label.setText(getItem(position).loadLabel(pm));

        ImageView icon = (ImageView) row.findViewById(R.id.icon);

        icon.setImageDrawable(getItem(position).loadIcon(pm));
    }
}