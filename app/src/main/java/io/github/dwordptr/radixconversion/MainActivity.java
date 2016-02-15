package io.github.dwordptr.radixconversion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Spinner.OnItemSelectedListener{
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.convert_button){
            EditText numberFieldET = (EditText)findViewById(R.id.convertNum_editText);
            TextView convertedTextView = (TextView)findViewById(R.id.converted_textView);
            String numberCandidate = numberFieldET.getText().toString().trim();
            int inputConvert = 0;
            try{
                if(convertFrom == RadixType.Dec)
                    inputConvert = Integer.parseInt(numberCandidate);
                else if(convertFrom == RadixType.Bin)
                    inputConvert = Integer.parseInt(numberCandidate, 2);
                else if(convertFrom == RadixType.Hex)
                    inputConvert = Integer.parseInt(numberCandidate.toLowerCase(),16);
            }
            catch(Exception e){
                Toast.makeText(this,getString(R.string.invalidInput),Toast.LENGTH_SHORT).show();
                Log.e("Convert Issues","derp",e);
                return;
            }
            if(convertTo==RadixType.Dec)
                convertedTextView.setText(Integer.toString(inputConvert));
            else if(convertTo==RadixType.Bin)
                convertedTextView.setText(Integer.toBinaryString(inputConvert));
            else if(convertTo == RadixType.Hex)
                convertedTextView.setText(Integer.toHexString(inputConvert));
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.convertFrom_spinner){
            Spinner convertFromSpinner = (Spinner)findViewById(R.id.convertFrom_spinner);
            //convertFromSpinner.setSelection(position);
            convertFrom = radixTable[position];
            Toast.makeText(this,"Switched convert from to "+convertFrom.name(),Toast.LENGTH_SHORT).show();


        }
        else if(parent.getId() == R.id.convertTo_spinner)
        {
            Spinner convertToSpinner = (Spinner)findViewById(R.id.convertTo_spinner);
            //convertToSpinner.setSelection(position);
            convertTo = radixTable[position];
            Toast.makeText(this,"Switched convert from to "+convertTo.name(),Toast.LENGTH_SHORT).show();
        }
        TextView resultEt = (TextView)findViewById(R.id.converted_textView);
        resultEt.setText(R.string.goesHere);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }

    public enum RadixType{
        Hex ,Bin,Dec;

    }
    private RadixType convertFrom =  RadixType.Dec;
    private RadixType convertTo = RadixType.Dec;
    private RadixType[] radixTable = {RadixType.Bin,RadixType.Dec,RadixType.Hex};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        Spinner convertFromSpinner = (Spinner)findViewById(R.id.convertFrom_spinner);
        Spinner convertToSpinner = (Spinner)findViewById(R.id.convertTo_spinner);
        ArrayAdapter<CharSequence> convertSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.radix_choices,android.R.layout.simple_spinner_item);

        convertFromSpinner.setAdapter(convertSpinnerAdapter);
        convertToSpinner.setAdapter(convertSpinnerAdapter);
        convertFromSpinner.setSelection(1);
        convertToSpinner.setSelection(1);

        convertToSpinner.setOnItemSelectedListener(this);
        convertFromSpinner.setOnItemSelectedListener(this);

        Button convertButton = (Button)findViewById(R.id.convert_button);
        convertButton.setOnClickListener(this);

        //textwatcher
        EditText inputEt = (EditText)findViewById(R.id.convertNum_editText);
        inputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView results = (TextView)findViewById(R.id.converted_textView);
                results.setText(R.string.goesHere);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
