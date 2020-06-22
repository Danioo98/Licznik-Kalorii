package pl.android.licznikkalorii.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {
    @SerializedName("produkty")
    public List<Product> products;

    public List<Product> getProduct() {
        return products;
    }

    public void setProduct(List<Product> products) {
        this.products = products;
    }
}
