package pl.android.licznikkalorii.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import pl.android.licznikkalorii.R;

public class MainActivity extends AppCompatActivity {
    private EditText Wzrost, Waga, Wiek;
    private CheckBox cbM, cbK;
    private Button zapiszBtn;
    private float wzrost, waga, wynik;
    private int wiek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListeners();
    }

    //ustawienie widokow i klikniec
    private void setListeners() {
        Wzrost = findViewById(R.id.etWzost);
        Waga = findViewById(R.id.etWaga);
        Wiek = findViewById(R.id.etWiek);
        cbK = findViewById(R.id.cbWomen);
        cbM = findViewById(R.id.cbMen);
        zapiszBtn = findViewById(R.id.btnSave);

        zapiszBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obliczBMR();
            }
        });

        cbK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbM.isChecked()) {
                    cbM.setChecked(false);
                    cbK.setChecked(true);
                }
            }
        });

        cbM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbK.isChecked()) {
                    cbK.setChecked(false);
                    cbM.setChecked(true);
                }
            }
        });
    }

    private float getWaga() {
        return Float.parseFloat(Waga.getText().toString());
    }

    private float getWzrost() {
        return Float.parseFloat(Wzrost.getText().toString());
    }

    private int getWiek() {
        return Integer.parseInt(Wiek.getText().toString());
    }

    private boolean isMen() {
        return cbM.isChecked();
    }

    private boolean isSexChoosen() {
        return cbK.isChecked() || cbM.isChecked();
    }

    public void obliczBMR() {
        //sprawdzenie danych z formularza i pobranie ich
        if (!Waga.getText().toString().isEmpty() && !Wzrost.getText().toString().isEmpty() && !Wiek.getText().toString().isEmpty()) {
            waga = getWaga();
            wzrost = getWzrost();
            wiek = getWiek();

            if (waga != 0 && wzrost != 0 && isSexChoosen()) {
                wynik = calculateBMR(waga, wzrost, wiek, isMen());
                openFoodList(wynik);
            } else {
                Toast.makeText(this, "Sprawdz wprowadzone dane", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Sprawdz wprowadzone dane", Toast.LENGTH_SHORT).show();
        }
    }

    //zapis bmr i przejscie do nowego widoku
    private void openFoodList(float wynik) {
        Intent intent = new Intent(MainActivity.this, FoodActivity.class);
        intent.putExtra("BMR", wynik);
        startActivity(intent);
        finish();
    }

    private float calculateBMR(float waga, float wzrost, int wiek, boolean men) {
        if (men) {
            return (9.99f * waga) + (6.25f * wzrost) - (4.92f * wiek) + 5;
        } else {
            return (9.99f * waga) + (6.25f * wzrost) - (4.92f * wiek) - 161;
        }
    }
}
