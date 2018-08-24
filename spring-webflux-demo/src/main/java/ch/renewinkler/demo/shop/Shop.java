package ch.renewinkler.demo.shop;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Shop {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shop_generator")
    @SequenceGenerator(name = "shop_generator", sequenceName = "shop_seq", allocationSize = 1)
    private Long id;

    @Column
    private String name;


    @Column
    private double size;


    public Shop(Long id, String name, double size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Shop(String name, double size) {
        this.name = name;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}