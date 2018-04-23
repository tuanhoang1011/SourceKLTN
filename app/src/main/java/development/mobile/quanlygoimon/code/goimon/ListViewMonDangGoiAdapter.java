package development.mobile.quanlygoimon.code.goimon;

import android.app.Activity;
import android.app.Dialog;
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
    private List<MonAn> monAnLst_DSMonDGFrag = null;
    private SendTongTien send;

    public ListViewMonDangGoiAdapter(Activity context, int textViewResourceId, List<MonAn> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.layoutID = textViewResourceId;
        this.monAnLst_DSMonDGFrag = objects;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutID, null);
        send = (SendTongTien) context;

        if (monAnLst_DSMonDGFrag.size() > 0 && position >= 0) {
            final TextView tenMonTxtView_item = (TextView) convertView.findViewById(R.id.tenMonTxtView_item);
            final TextView giaTxtView_item = (TextView) convertView.findViewById(R.id.giaTxtView_item);
            final ImageButton minusBtn_item = (ImageButton) convertView.findViewById(R.id.minusBtn_item);
            final ImageButton plusBtn_item = (ImageButton) convertView.findViewById(R.id.plusBtn_item);
            final ImageButton noteBtn_item = (ImageButton) convertView.findViewById(R.id.noteBtn_item);
            final TextView ghiChuTxtView_item = (TextView) convertView.findViewById(R.id.ghiChuTxtView_item);
            final EditText soLuongEditTxt_item = (EditText) convertView.findViewById(R.id.soLuongEditTxt_item);
            final String tempSoLuong;
            final MonAn monAn = monAnLst_DSMonDGFrag.get(position);

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
                        monAnLst_DSMonDGFrag.remove(position);
                    }
                    else{
                        soLuongCu = monAn.getSoLuong();
                        monAn.setSoLuong(Integer.parseInt(newVal));
                        monAn.setGia((monAn.getGia() / soLuongCu) * monAn.getSoLuong());
                    }
                    send.sendTongTien(monAn.getGia() - tongTienCu);
                    notifyDataSetChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            noteBtn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_changeinfomonan);

                    final TextView ghiChuTitle_dialogChange = (TextView) dialog.findViewById(R.id.ghiChuTitle_dialogChange);
                    final EditText ghiChuEditTxt_dialogChange = (EditText) dialog.findViewById(R.id.ghiChuEditTxt_dialogChange);
                    Button huyBtn_dialogChange = (Button) dialog.findViewById(R.id.huyBtn_dialogChange);
                    Button okBtn_dialogChange = (Button) dialog.findViewById(R.id.okBtn_dialogChange);

                    ghiChuTitle_dialogChange.setText("Ghi chú " + monAn.getTenMonAn());
                    ghiChuEditTxt_dialogChange.setText(monAn.getGhiChu());
                    ghiChuEditTxt_dialogChange.requestFocus();

                    huyBtn_dialogChange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    okBtn_dialogChange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            monAnLst_DSMonDGFrag.get(position).setGhiChu(ghiChuEditTxt_dialogChange.getText().toString());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    dialog.getWindow().setLayout(900, 450);
                }
            });
        }

        return convertView;
    }

    interface SendTongTien {
        void sendTongTien(double tongTienMonAnMoi);
    }
}
