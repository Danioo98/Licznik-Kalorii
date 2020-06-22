package pl.android.licznikkalorii.model;

public class Product {

    private String produkt;
    private Double bialko;
    private Double tluszcze;
    private Double weglowodany;
    private Integer kcal;

    public String getProdukt() {
        return produkt;
    }

    public void setProdukt(String produkt) {
        this.produkt = produkt;
    }

    public Double getBialko() {
        return bialko;
    }

    public void setBialko(Double bialko) {
        this.bialko = bialko;
    }

    public Double getTluszcze() {
        return tluszcze;
    }

    public void setTluszcze(Double tluszcze) {
        this.tluszcze = tluszcze;
    }

    public Double getWeglowodany() {
        return weglowodany;
    }

    public void setWeglowodany(Double weglowodany) {
        this.weglowodany = weglowodany;
    }

    public Integer getKcal() {
        return kcal;
    }

    public void setKcal(Integer kcal) {
        this.kcal = kcal;
    }
}
