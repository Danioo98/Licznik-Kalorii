package pl.android.licznikkalorii.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.android.licznikkalorii.R;
import pl.android.licznikkalorii.list.CounterClickListener;
import pl.android.licznikkalorii.list.FoodAdapter;
import pl.android.licznikkalorii.model.FoodItem;
import pl.android.licznikkalorii.model.Product;
import pl.android.licznikkalorii.model.Products;

public class FoodActivity extends AppCompatActivity {

    RecyclerView rvProdukty;
    Button button;
    Float bmr;
    List<FoodItem> data;
    FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        //czytanie danych z JSONa
        Gson gson = new GsonBuilder().create();
        Products productObject = gson.fromJson(loadJSONFromAsset(), Products.class);
        List<Product> products = productObject.getProduct();
        //odczytanie bmr
        bmr = getIntent().getFloatExtra("BMR", 0f);

        button = findViewById(R.id.btnWyniki);
        //tworzenie listy produktow i ustawienie listenera na dodawanie/odejmowanie
        rvProdukty = findViewById(R.id.rvFood);
        rvProdukty.setLayoutManager(new LinearLayoutManager(this));
        data = getFoodItemsList(products);
        foodAdapter = new FoodAdapter(data, new CounterClickListener() {
            @Override
            public void onAddClicked(int position) {
                //pobranie ilosci i dodanie 1
                int counter = data.get(position).getCounter();
                if (counter <= 99) {
                    data.get(position).setCounter(counter + 1);
                    foodAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onMinusClicked(int position) {
                int counter = data.get(position).getCounter();
                if (counter > 0) {
                    data.get(position).setCounter(counter - 1);
                    foodAdapter.notifyDataSetChanged();
                }

            }
        });
        rvProdukty.setAdapter(foodAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcCalories(data, bmr);
            }
        });

        //odczytanie bmr
        bmr = getIntent().getFloatExtra("BMR", 0f);
    }

    private void calcCalories(List<FoodItem> data, Float bmr) {
        int totalCalories = 0;
        int totalBialko = 0;
        int totalTluszcze = 0;
        int totalWeglowodany = 0;
        //przejscie po liscie i obliczanie kalorii i statystyk
        for (FoodItem item : data) {
            if (item.getCounter() != 0) {
                totalCalories += item.getCounter() * item.getProduct().getKcal();
                totalBialko += item.getCounter() * item.getProduct().getBialko();
                totalTluszcze += item.getCounter() * item.getProduct().getTluszcze();
                totalWeglowodany += item.getCounter() * item.getProduct().getWeglowodany();
            }
        }
        String rezultat = "";
        String staty = "Razem - Bialko: " + totalBialko + "g, Tluszcze: " + totalTluszcze + "g, Weglowodany: " + totalWeglowodany + "g";
        if ((float) totalCalories > bmr) {
            rezultat = "Twoje BMR: " + bmr + ", Twoje dzienne spożycie (" + totalCalories + " kcal) jest za duże";
        } else {
            rezultat = "Twoje BMR: " + bmr + ", Twoje dzienne spożycie (" + totalCalories + " kcal) jest za małe";
        }

        showResults(staty, rezultat);
    }

    private void showResults(String statistics, String results) {
        //utworzenie dialogu z wynikami
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_results);
        dialog.setTitle("TWOJE WYNIKI");
        TextView tvStats = dialog.findViewById(R.id.dialog_stats);
        tvStats.setText(statistics);
        TextView tvResults = dialog.findViewById(R.id.dialog_result);
        tvResults.setText(results);

        Button ok = dialog.findViewById(R.id.dialog_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private List<FoodItem> getFoodItemsList(List<Product> products) {
        //tworzenie listy produktow z licznikiem kazdego z nich
        List<FoodItem> foodItems = new ArrayList<>();
        for (Product product : products) {
            foodItems.add(new FoodItem(product, 0));
        }
        return foodItems;
    }

    //parsowanie danych z pliku json
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("produkty.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
