package development.mobile.quanlygoimon.code.goimon;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
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
            final TextView soLuongTxtView_item = (TextView) convertView.findViewById(R.id.soLuongTxtView_item);
            final String tempSoLuong;
            final MonAn monAn = monAnLst_DSMonDGFrag.get(position);

            tenMonTxtView_item.setText(monAn.getTenMonAn());
            giaTxtView_item.setText(NumberFormat.getCurrencyInstance().format(monAn.getGia()));
            ghiChuTxtView_item.setText(monAn.getGhiChu());
            soLuongTxtView_item.setText(monAn.getSoLuong() + "");

            soLuongTxtView_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Nhập số lượng");

                    final EditText input = new EditText(context);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    input.setText(soLuongTxtView_item.getText());
                    input.requestFocus();
                    builder.setView(input);

                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String newVal = input.getText().toString();
                            double tongTienCu = monAn.getGia();
                            if (newVal.trim().equals("")) {
                                dialog.cancel();
                            } else if (newVal.equals("0")) {
                                monAnLst_DSMonDGFrag.remove(position);
                                send.sendTongTien(0 - tongTienCu);
                            } else {
                                int soLuongCu = monAn.getSoLuong();
                                monAn.setSoLuong(Integer.parseInt(newVal));
                                monAn.setGia((tongTienCu / soLuongCu) * monAn.getSoLuong());
                            }
                            send.sendTongTien(monAn.getGia() - tongTienCu);
                            notifyDataSetChanged();

                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

            noteBtn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Ghi chú món " + monAn.getTenMonAn());

                    final EditText input = new EditText(context);
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(100)});
                    input.setText(ghiChuTxtView_item.getText());
                    input.requestFocus();
                    builder.setView(input);

                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            monAn.setGhiChu(input.getText().toString());
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

            minusBtn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int soLuongCu = monAn.getSoLuong();
                    double tongTienCu = monAn.getGia();
                    monAn.setSoLuong(monAn.getSoLuong() - 1);
                    int newVal = monAn.getSoLuong();
                    if (newVal == 0) {
                        monAnLst_DSMonDGFrag.remove(position);
                        send.sendTongTien(0 - tongTienCu);
                    } else {
                        monAn.setGia((tongTienCu / soLuongCu) * monAn.getSoLuong());
                    }
                    send.sendTongTien(monAn.getGia() - tongTienCu);
                    notifyDataSetChanged();
                }
            });

            plusBtn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int soLuongCu = monAn.getSoLuong();
                    double tongTienCu = monAn.getGia();
                    monAn.setSoLuong(monAn.getSoLuong() + 1);
                    monAn.setGia((tongTienCu / soLuongCu) * monAn.getSoLuong());
                    send.sendTongTien(monAn.getGia() - tongTienCu);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }

    interface SendTongTien {
        void sendTongTien(double tongTienMonAnMoi);
    }
}
