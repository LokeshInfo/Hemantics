package www.lok.hemantics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Data_Model> key_vals;
    private EditText ed_var, ed_val, ed_enter;
    private TextView tv_all,tv_answer;
    private Button badd, bshow;
    String var, val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_var = findViewById(R.id.ed_var);
        ed_val = findViewById(R.id.ed_value);
        ed_enter = findViewById(R.id.ed_enter);
        badd = findViewById(R.id.b_add);
        bshow = findViewById(R.id.b_cryp);
        tv_all = findViewById(R.id.tv_allvals);
        tv_answer = findViewById(R.id.tv_answer);

        key_vals = new ArrayList<>();

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    put_value();
                }else{
                }
            }
        });

        bshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String anwerr="";
                String lkk = ed_enter.getText().toString();
                if (lkk.matches("")){
                    Toast.makeText(MainActivity.this, "Please Enter and Show...", Toast.LENGTH_SHORT).show();
                }else {
                    lkk.length();
                    get_answer();
                    Toast.makeText(MainActivity.this, "Length of String is " + lkk.length(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ed_enter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private Boolean validate(){

        var = ed_var.getText().toString();
        val = ed_val.getText().toString();

        if (var.matches("") && val.matches("")){
            Toast.makeText(this, "Enter All Values and Add...", Toast.LENGTH_SHORT).show();
            return false;
        }else if (var.matches("")){
            Toast.makeText(this, "Enter Variable and Add...", Toast.LENGTH_SHORT).show();
            return false;
        } else if (val.matches("")){
        Toast.makeText(this, "Enter Value and Add...", Toast.LENGTH_SHORT).show();
        return false;
    }else{
            return true;
        }

    }

    private void put_value() {
        Boolean check = false;

        for (int y = 0; y < key_vals.size(); y++) {

            String value = key_vals.get(y).getValue();
            String varrble = key_vals.get(y).getValue();
            Log.e("Value at " + y + " is ", " " + value);
            Log.e("\nVariable at " + y + " is ", " " + varrble);
            if (var.equalsIgnoreCase(key_vals.get(y).getVariable())) {
                key_vals.get(y).setValue(val);
                check = true;
                break;
            } else {
                check = false;
            }
        }
        if (!check) {
            key_vals.add(new Data_Model(var, val));
            Toast.makeText(this, "Value Saved...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Value Updated...", Toast.LENGTH_SHORT).show();
        }
        var = "";
        val = "";
        ed_var.getText().clear();
        ed_val.getText().clear();
        tv_all.setText("");
        String all_plus="";
        for (int y = 0; y < key_vals.size(); y++) {
            String value = key_vals.get(y).getValue();
            String variable = key_vals.get(y).getVariable();
            all_plus = all_plus+ "\n"+variable+ " = "+value;
        }
        tv_all.setText(all_plus);

        }

    private void get_answer(){

        String fanswer = "";
        String entr = ed_enter.getText().toString();

       /* for (int y=0 ; y<key_vals.size(); y++){

            char ch = key_vals.get(y).getVariable().charAt(0);

            for (int x=0 ; x<entr.length(); x++){

                char cc = entr.charAt(x);
                if (Character.toLowerCase(cc)==Character.toLowerCase(ch)){

                    Log.e(cc+"  Value for "+ch," ===== "+key_vals.get(y).getValue());

                    fanswer = fanswer + key_vals.get(y).getValue();
                }
            }
        }*/


        for (int x=0 ; x<entr.length(); x++){

            char cc = entr.charAt(x);

            for (int j=0 ; j<key_vals.size(); j++){

                char ch = key_vals.get(j).getVariable().charAt(0);

                if (Character.toLowerCase(cc)==Character.toLowerCase(ch)){
                    Log.e(cc+"  Value for "+ch," ===== "+key_vals.get(j).getValue());
                    fanswer = fanswer + key_vals.get(j).getValue();
                    break;
                }
            }

        }


        //tv_answer.setText(""+new StringBuilder(fanswer).reverse());
        tv_answer.setText(""+fanswer);

    }

}