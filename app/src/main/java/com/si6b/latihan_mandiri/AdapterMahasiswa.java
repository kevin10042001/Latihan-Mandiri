package com.si6b.latihan_mandiri;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMahasiswa extends RecyclerView.Adapter<AdapterMahasiswa.ViewHolderDestinasi>{
    private Context ctx;
    private ArrayList arrId, arrNPM, arrNama, arrProdi;

    public AdapterMahasiswa(Context ctx, ArrayList arrId, ArrayList arrNPM, ArrayList arrNama, ArrayList arrProdi) {
        this.ctx = ctx;
        this.arrId = arrId;
        this.arrNPM = arrNPM;
        this.arrNama = arrNama;
        this.arrProdi = arrProdi;
    }

    @NonNull
    @Override
    public ViewHolderDestinasi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_item_mahasiswa, parent, false);
        return new ViewHolderDestinasi(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDestinasi holder, int position) {
        holder.tvId.setText(arrId.get(position).toString());
        holder.tvNPM.setText(arrNPM.get(position).toString());
        holder.tvNama.setText(arrNama.get(position).toString());
        holder.tvProdi.setText(arrProdi.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return arrNama.size();
    }

    public class ViewHolderDestinasi extends RecyclerView.ViewHolder{
        TextView tvId, tvNPM, tvNama, tvProdi;

        public ViewHolderDestinasi(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNPM = itemView.findViewById(R.id.tv_npm);
            tvNama= itemView.findViewById(R.id.tv_nama);
            tvProdi = itemView.findViewById(R.id.tv_prodi);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Perintah Apa yang Akan Dilakukan?");
                    pesan.setCancelable(true);
                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String id, npm, nama, prodi;

                            id = tvId.getText().toString();
                            npm = tvNPM.getText().toString();
                            nama = tvNama.getText().toString();
                            prodi = tvProdi.getText().toString();

                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xId", id);
                            kirim.putExtra("xNPM", npm);
                            kirim.putExtra("xNama", nama);
                            kirim.putExtra("xProdi", prodi);
                            ctx.startActivity(kirim);

                        }
                    });
                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MyDatabaseHelper myDB = new MyDatabaseHelper(ctx);
                            long eks = myDB.hapusData(tvId.getText().toString());
                            if(eks == -1){
                                Toast.makeText(ctx, "Gagal Hapus Data", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(ctx, "Sukses Hapus Data", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                ((MainActivity)ctx).onResume();
                            }

                        }
                    });
                    pesan.show();

                    return false;
                }
            });
        }
    }
}