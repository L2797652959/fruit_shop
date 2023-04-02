package cn.edu.guet.bean;

/**
 * @Author liwei
 * @Date 2023/3/12 13:11
 * @Version 1.0
 */
public class Fruit {

    private int f_id;
    private String f_name;
    private Float f_price;
    private Float f_sellprice;
    private Float f_number;

    public int getF_id() { return f_id; }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public Float getF_price() {
        return f_price;
    }

    public void setF_price(Float f_price) {
        this.f_price = f_price;
    }

    public Float getF_sellprice() {
        return f_sellprice;
    }

    public void setF_sellprice(Float f_sellprice) {
        this.f_sellprice = f_sellprice;
    }

    public Float getF_number() { return f_number; }

    public void setF_number(Float f_number) { this.f_number = f_number; }
}
