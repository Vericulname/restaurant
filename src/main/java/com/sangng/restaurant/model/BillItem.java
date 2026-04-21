package com.sangng.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BillItem {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private double totalprice;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id")
    @JsonIgnore
    private Bill bill;

    public void setTotalPrice() {
        this.totalprice = dish.getPrice() * quantity;
    }
}
