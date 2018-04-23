package development.mobile.quanlygoimon.code.phucvuchonban;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.Ban;

public class GridViewBanAdapter extends ArrayAdapter<Ban> {
    private Activity context = null;
    private int layoutID;
    private List<Ban> banLst = null;
    public List<Integer> selectedPositions;


    public GridViewBanAdapter(Activity context, int textViewResourceId, List<Ban> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutID = textViewResourceId;
        this.banLst = objects;
        this.selectedPositions = new ArrayList<Integer>();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutID, null);

        if(banLst.size() > 0 && position >= 0){
            final TextView banTxtView = (TextView) convertView.findViewById(R.id.banTxtView);
            final TextView hoaDonTxtView = (TextView) convertView.findViewById(R.id.hoaDonTxtView);
            final RelativeLayout layoutBan = (RelativeLayout) convertView.findViewById(R.id.layoutBan);

            Ban ban = banLst.get(position);
            banTxtView.setText(ban.getMaBan());
            hoaDonTxtView.setText(ban.getMaHoaDon());
            switch (ban.getTrangThai()){
                case "Trống":
                    if(selectedPositions.contains(position)){
                        layoutBan.setBackgroundResource(R.drawable.dangchon);
                    }
                    else{
                        layoutBan.setBackgroundResource(R.drawable.trong);
                    }
                    break;
                case "Đang phục vụ":
                    layoutBan.setBackgroundResource(R.drawable.dangphucvu);
                    break;
                case "Đặt trước":
                    layoutBan.setBackgroundResource(R.drawable.dattruoc);
                    break;
            }
        }
        return convertView;
    }
}
