package www.lok.hemantics;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;

import java.util.List;

public class d_adapter extends RecyclerView.Adapter<d_adapter.MyViewHolder>
{

    private List<DB_data> modelList;

    private Context context;
    Activity activity;
    DatabaseHandler db;
    Click_Listener click_listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView variable, value, id;
        ImageView delete;
        FloatingActionButton update, delete_fab;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.tv_id);
            variable = (TextView) view.findViewById(R.id.tv_var);
            value = (TextView) view.findViewById(R.id.tv_val);
            update = view.findViewById(R.id.update_fab);
            delete_fab = view.findViewById(R.id.delete_fab);
            delete = view.findViewById(R.id.delete);
        }
    }

    public d_adapter(Activity activity, List<DB_data> modelList, Click_Listener click_listener) {
        this.activity = activity;
        this.modelList = modelList;
        this.click_listener = click_listener;
        db = new DatabaseHandler(activity);
    }

    @Override
    public d_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_vdata, parent, false);

        context = parent.getContext();

        return new d_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(d_adapter.MyViewHolder holder, final int position) {
        final DB_data mList = modelList.get(position);

        holder.id.setText(mList.getId()+".");
        holder.variable.setText("Variable - "+mList.getVariable());
        holder.value.setText("Value - "+mList.getValue());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_dialog(mList.getId(),position);
            }
        });

        holder.delete_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_dialog(mList.getId(),position);
            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_update_data(mList.getId(), mList.getVariable(),mList.getValue());
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private void delete_dialog(final String id,final int pos){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                db.delete_data(id);
                click_listener.item_click(0);
                Toast.makeText(activity, "Deleted...", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void dialog_update_data(final String id,String varr,String vall) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_add_data);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final EditText ed_var = dialog.findViewById(R.id.ed_var);
        final EditText ed_val = dialog.findViewById(R.id.ed_value);
        ed_var.setEnabled(false);
        final TextView tv = dialog.findViewById(R.id.tvv);
        Button badd = dialog.findViewById(R.id.b_add);

        badd.setText("Update");
        ed_var.setText(varr);
        ed_val.setText(vall);

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String var = ed_var.getText().toString();
                String val = ed_val.getText().toString();

                if (var.matches("") && val.matches("")){
                    Toast.makeText(context, "Enter All Values and Add...", Toast.LENGTH_SHORT).show();
                }else if (var.matches("")){
                    Toast.makeText(context, "Enter Variable and Add...", Toast.LENGTH_SHORT).show();
                } else if (val.matches("")){
                    Toast.makeText(context, "Enter Value and Add...", Toast.LENGTH_SHORT).show();
                }else{
                    db.update_data(id,var,val);
                    Toast.makeText(context, "Data Updated Succesfully...", Toast.LENGTH_SHORT).show();
                    click_listener.item_click(0);
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }


}
