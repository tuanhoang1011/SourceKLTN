package development.mobile.quanlygoimon.code.goimon;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import development.mobile.quanlygoimon.code.R;
import development.mobile.quanlygoimon.code.entity.MonAn;

public class ListViewMonDangGoiAdapter extends ArrayAdapter<MonAn> {
    private Activity context = null;
    private int layoutID;
    private List<MonAn> monAnLst = null;
    private SendTongTien send;

    public ListViewMonDangGoiAdapter(Activity context, int textViewResourceId, List<MonAn> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutID = textViewResourceId;
        this.monAnLst = objects;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutID, null);
        send = (SendTongTien) context;

        if (monAnLst.size() > 0 && position >= 0) {
            final TextView tenMonTxtView_item = (TextView) convertView.findViewById(R.id.tenMonTxtView_item);
            final TextView giaTxtView_item = (TextView) convertView.findViewById(R.id.giaTxtView_item);
            final ImageButton minusBtn_item = (ImageButton) convertView.findViewById(R.id.minusBtn_item);
            final ImageButton plusBtn_item = (ImageButton) convertView.findViewById(R.id.plusBtn_item);
            final TextView ghiChuTxtView_item = (TextView) convertView.findViewById(R.id.ghiChuTxtView_item);
            final EditText soLuongEditTxt_item = (EditText) convertView.findViewById(R.id.soLuongEditTxt_item);
            final String tempSoLuong;
            final MonAn monAn = monAnLst.get(position);

            tenMonTxtView_item.setText(monAn.getTenMonAn());
            giaTxtView_item.setText(NumberFormat.getCurrencyInstance().format(monAn.getGia()));
            ghiChuTxtView_item.setText(monAn.getGhiChu());
            soLuongEditTxt_item.setText(monAn.getSoLuong()+"");

            soLuongEditTxt_item.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String newVal = soLuongEditTxt_item.getText().toString();
                    double tongTienCu = monAn.getGia();
                    int soLuongCu;
                    if(newVal.trim().equals("")){
                        soLuongEditTxt_item.setText("1");
                        soLuongCu = monAn.getSoLuong();
                        monAn.setSoLuong(1);
                        monAn.setGia(monAn.getGia() / soLuongCu);
                    }
                    else if(newVal.equals("0")){
                        monAnLst.remove(position);
                    }
                    else{
                        soLuongCu = monAn.getSoLuong();
                        monAn.setSoLuong(Integer.parseInt(newVal));
                        monAn.setGia((monAn.getGia() / soLuongCu) * monAn.getSoLuong());
                    }
                    send.senTongTien(monAn.getGia() - tongTienCu);
                    notifyDataSetChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        return convertView;
    }

    interface SendTongTien {
        void senTongTien(double tongTienMonAnMoi);
    }
}
