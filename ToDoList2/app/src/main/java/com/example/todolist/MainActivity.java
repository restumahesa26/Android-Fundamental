package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    private static final String TAG = "MainActivity";
    private DatabaseHelper databaseHelper;
    private ListView itemsListView;
    private FloatingActionButton fab;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        fab = findViewById(R.id.fab);
        itemsListView = findViewById(R.id.itemsList);

        populateListView();
        onFabClick();
        hideFab();
    }

    private void insertDataToDb(String title, Integer harga_beli, String satuan, Integer harga_jual) {
        boolean insertData = databaseHelper.insertData(title, harga_beli, satuan, harga_jual);
        if (insertData) {
            try {
                populateListView();
                toastMsg("Barang di tambahkan");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            toastMsg("Opps.. terjadi kesalahan saat menyimpan!");
    }

    private void populateListView() {
        try {
            ArrayList<ModelData> items = databaseHelper.getAllData();
            ItemAdapter itemsAdopter = new ItemAdapter(this, items);
            itemsListView.setAdapter(itemsAdopter);
            itemsAdopter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideFab() {
        itemsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    fab.show();
                }else{
                    fab.hide();
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    private void onFabClick() {
        try {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(buttonClick);
                    showAddDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getLayoutInflater().getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams")
        final View dialogView = inflater.inflate(R.layout.custom_dialog_todo, null);
        dialogBuilder.setView(dialogView);

        final EditText nama_barang_view = dialogView.findViewById(R.id.edit_nama_barang);
        final EditText harga_beli_view = dialogView.findViewById(R.id.edit_harga_beli);
        final EditText satuan_view = dialogView.findViewById(R.id.edit_satuan);
        final EditText harga_jual_view = dialogView.findViewById(R.id.edit_harga_jual);

        dialogBuilder.setTitle("Tambah barang baru");
        dialogBuilder.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String nama_barang = nama_barang_view.getText().toString();
                int harga_beli = Integer.parseInt(harga_beli_view.getText().toString());
                String satuan = satuan_view.getText().toString();
                int harga_jual = Integer.parseInt(harga_jual_view.getText().toString());
                if (nama_barang.length() != 0) {
                    try {
                        insertDataToDb(nama_barang, harga_beli, satuan, harga_jual);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    toastMsg("Oops, Gak bisa kosong barangnya nya.");
                }
            }
        });
        dialogBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void toastMsg(String msg) {
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0,0);
        t.show();
    }
}