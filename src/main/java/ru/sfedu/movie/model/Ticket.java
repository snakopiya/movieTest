package ru.sfedu.movie.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Ticket {
    @Attribute
    @CsvBindByPosition(position = 0)
    private Long id;
    @Element
    @CsvCustomBindByPosition(position = 1, converter = CsvTicketPosterConverter.class)
    private Poster seance;
    @Attribute
    @CsvBindByPosition(position = 2)
    private Integer age;
    @Attribute
    @CsvBindByPosition(position = 3)
    private String name;
    @Attribute
    @CsvBindByPosition(position = 4)
    private Boolean discount;

    public Ticket() {
    }

    public Ticket(Long id, Poster seance, Integer age, String name, Boolean discount) {
        this.id = id;
        this.seance = seance;
        this.age = age;
        this.name = name;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Poster getSeance() {
        return seance;
    }

    public void setSeance(Poster seance) {
        this.seance = seance;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", seance=" + seance +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", discount=" + discount +
                '}';
    }
}
