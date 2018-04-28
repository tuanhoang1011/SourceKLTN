package development.mobile.quanlygoimon.code.quanly;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.ChucNangQuanLy;

public class GridViewQuanLyAdapter extends ArrayAdapter<ChucNangQuanLy> {
    private Activity context = null;
    private int layoutID;
    private List<ChucNangQuanLy> chucNangQuanLyList = null;

    public GridViewQuanLyAdapter(Activity context, int textViewResourceId, List<ChucNangQuanLy> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutID = textViewResourceId;
        this.chucNangQuanLyList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutID, null);

        if(chucNangQuanLyList.size() > 0 && position >= 0){
            final ChucNangQuanLy chucNangQuanLy = chucNangQuanLyList.get(position);
            final ImageView imgIconChucNang = (ImageView) convertView.findViewById(R.id.igm_icon_chucnang_quanly);
            final TextView tvTenChucNang = (TextView) convertView.findViewById(R.id.tv_ten_chucnang_quanly);
            imgIconChucNang.setImageResource(chucNangQuanLy.getIconChucNang());
            tvTenChucNang.setText(chucNangQuanLy.getTenChucNang().toString());
        }
        return convertView;
    }
}