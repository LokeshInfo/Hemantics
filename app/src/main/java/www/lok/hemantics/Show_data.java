package www.lok.hemantics;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class Show_data extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionMenu fabMenu;
    FloatingActionButton fab_add, fab_equate;
    DatabaseHandler dbHandler;
    TextView tv_tx;
    Click_Listener click_listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv_tx = findViewById(R.id.tv_tx);
        recyclerView = findViewById(R.id.recyclerview);
        fabMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fab_add = (FloatingActionButton) fabMenu.findViewById(R.id.f_add);
        fab_equate = (FloatingActionButton) fabMenu.findViewById(R.id.f_equation);
        dbHandler = new DatabaseHandler(Show_data.this);
        Click_Listeners();
        show_list();
    }

    private void Click_Listeners(){

        click_listener = new Click_Listener() {
            @Override
            public void item_click(int type) {
                if (type==0){
                    show_list();
                }
            }
        };
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_add();
            }
        });

        fab_equate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_encrypt_data();
            }});
    }


    private void show_list(){
        if (dbHandler.get_dbdata().isEmpty()){
            tv_tx.setVisibility(View.VISIBLE);
        }else{
            tv_tx.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(Show_data.this));
            recyclerView.setAdapter(new d_adapter(Show_data.this,dbHandler.get_dbdata(),click_listener));
        }
    }

    private void dialog_add() {
        final Dialog dialog = new Dialog(Show_data.this);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_add_data);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final EditText ed_var = dialog.findViewById(R.id.ed_var);
        final EditText ed_val = dialog.findViewById(R.id.ed_value);
        final TextView tv = dialog.findViewById(R.id.tvv);
        Button badd = dialog.findViewById(R.id.b_add);

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String var = ed_var.getText().toString();
               String val = ed_val.getText().toString();

                if (var.matches("") && val.matches("")){
                    Toast.makeText(Show_data.this, "Enter All Values and Add...", Toast.LENGTH_SHORT).show();
                }else if (var.matches("")){
                    Toast.makeText(Show_data.this, "Enter Variable and Add...", Toast.LENGTH_SHORT).show();
                } else if (val.matches("")){
                    Toast.makeText(Show_data.this, "Enter Value and Add...", Toast.LENGTH_SHORT).show();
                }else{
                    if (dbHandler.check_if_exists(var)){
                        Toast.makeText(Show_data.this, " Already Exists ", Toast.LENGTH_SHORT).show();
                        tv.setVisibility(View.VISIBLE);
                    }else {
                        tv.setVisibility(View.GONE);
                        dbHandler.insert_dbdata(new Data_Model(var, val));
                        Toast.makeText(Show_data.this, "Data Added in Database...", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        show_list();
                    }
                }

            }
        });
        dialog.show();
    }


    private void dialog_encrypt_data() {
        final Dialog dialog = new Dialog(Show_data.this);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_encrypt);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final EditText ed_val = dialog.findViewById(R.id.ed_value);
        final TextView tv_answer = dialog.findViewById(R.id.tv_answer);
        Button badd = dialog.findViewById(R.id.b_add);
        ImageView var_copy = dialog.findViewById(R.id.ic_vcopy);
        ImageView val_copy = dialog.findViewById(R.id.ic_vlcopy);


        var_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String var = ed_val.getText().toString();
                if (var.matches("")){
                    Toast.makeText(Show_data.this, "No Code Found...", Toast.LENGTH_SHORT).show();
                }else{
                    copy_to_clipboard(var);
                    Toast.makeText(Show_data.this, "Code Copied To Clipboard..", Toast.LENGTH_SHORT).show();
                }}  });

        val_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String va = tv_answer.getText().toString();
                if (va.equalsIgnoreCase("Answer Here")){
                    Toast.makeText(Show_data.this, "No Answer Found...", Toast.LENGTH_SHORT).show();
                }else{
                    copy_to_clipboard(va);
                    Toast.makeText(Show_data.this, "Answer Copied To Clipboard..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = ed_val.getText().toString();

                if (code.matches("")){
                    Toast.makeText(Show_data.this, "Please enter Code and Encrypt...", Toast.LENGTH_SHORT).show();
                }else{
                    String ans = "";
                    char[] try1 = code.toCharArray();
                    for (int i = 0; i<=try1.length-1; i++) {
                        System.out.print(try1[i]);
                        ans = ans + " " + dbHandler.get_andwer("" + try1[i]);
                    }
                    tv_answer.setText(""+ans);
                }
            }
        });
        dialog.show();
    }

    private void copy_to_clipboard(String text){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
    }

}
