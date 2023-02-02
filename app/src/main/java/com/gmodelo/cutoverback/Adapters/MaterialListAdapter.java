package com.gmodelo.cutoverback.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gmodelo.cutoverback.Activity.R;
import com.gmodelo.cutoverback.Utilities.MaterialListBean;

import java.util.List;

public class MaterialListAdapter extends ArrayAdapter<MaterialListBean> {

    Context context;


    public MaterialListAdapter(@NonNull Context context, int resource, List<MaterialListBean> items) {
        super(context, resource, items);
        this.context = context;
    }

    private class ViewHolder {
        TextView matnr;
        TextView matnrE;
        TextView matkx;
        ImageView leftIcon;
        Button rigthIcon;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        MaterialListBean matBean = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.material_linear_layout, null);
            holder = new ViewHolder();
            holder.matnr = convertView.findViewById(R.id.material_linear_layout_text_material);
            holder.matkx = convertView.findViewById(R.id.material_linear_layout_text_description);
            holder.matnrE = convertView.findViewById(R.id.material_linear_layout_text_external);
            holder.leftIcon = convertView.findViewById(R.id.material_linear_layout_left_image);
            holder.rigthIcon = convertView.findViewById(R.id.material_linear_layout_right_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String mapKey = matBean.getStoredKey();
        holder.matnr.setText(matBean.getMatnr());
        holder.matnrE.setText(matBean.getMatnr_ext());
        holder.matkx.setText(matBean.getMatkx());
        holder.leftIcon.setImageResource(matBean.getLeftIcon());
        holder.rigthIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Inside Position : ", "Position: " + position + " mapa: " + mapKey);
            }
        });
        return convertView;
    }

}
