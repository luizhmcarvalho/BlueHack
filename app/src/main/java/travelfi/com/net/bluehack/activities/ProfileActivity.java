package travelfi.com.net.bluehack.activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import travelfi.com.net.bluehack.R;
import travelfi.com.net.bluehack.fragments.TimePickerFragment;
import travelfi.com.net.bluehack.models.UserProfile;

public class ProfileActivity extends AppCompatActivity {

    EditText inputDataNascimento;
    EditText inputEmail;
    EditText inputCity;

    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("INFORMAÇÕES ADICIONAIS");
        inputDataNascimento = (EditText) findViewById(R.id.inputDataNascimento);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputCity = (EditText) findViewById(R.id.inputCity);

        inputDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view);
            }
        });


        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfile usr = UserProfile.getInstance();
                usr.setEmail(inputEmail.getText().toString());
                usr.setCity(inputCity.getText().toString());
                usr.setBirthDate(inputDataNascimento.getText().toString());
                if(usr.saveToCloudant(getApplicationContext())){
                    Intent i = new Intent(ProfileActivity.this, ChatActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        inputDataNascimento.setText(newFragment.getDate());
    }
}
