package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    private int id;
    private String productName;
    private double unitPrice;
    private int quantity;

    @Override
    public String toString() {
        return "Product ID: " + id
                + ", Product Name: " + productName
                + ", Unit Price: " + unitPrice
                + ", Quantity: " + quantity;
    }

}
