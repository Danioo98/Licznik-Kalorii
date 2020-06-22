package pl.android.licznikkalorii.model;

public class FoodItem {
    private Product product;
    private Integer counter;

    public FoodItem(Product product, Integer counter) {
        this.product = product;
        this.counter = counter;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}
