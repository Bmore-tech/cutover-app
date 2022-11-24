package com.gmodelo.cutoverback.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.gmodelo.cutoverback.Activity.R;
import com.gmodelo.cutoverback.Utilities.CarrilesListBean;

import java.util.List;

public class CarrilListAdapter extends ArrayAdapter<CarrilesListBean> {

    Context context;

    public CarrilListAdapter(@NonNull Context context, int resource, List<CarrilesListBean> items) {
        super(context, resource, items);
        this.context = context;
    }


    private class CarrilHolder {
        TextView lgpla;
        TextView ldplaDesc;
        ImageView leftIcon;

        public CarrilHolder() {
            super();
        }
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        CarrilHolder holder = null;
        CarrilesListBean carBean = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.carriles_linear_layout, null);
            holder = new CarrilHolder();
            holder.lgpla = convertView.findViewById(R.id.carriles_linear_layout_text_material);
            holder.leftIcon = convertView.findViewById(R.id.carriles_linear_layout_left_image);
            convertView.setTag(holder);
        } else {
            holder = (CarrilHolder) convertView.getTag();
        }

        holder.lgpla.setText(carBean.getLgpla());
        holder.leftIcon.setImageResource(carBean.getLeftIcon());
        return convertView;

    }
}
