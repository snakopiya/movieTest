package ru.sfedu.movie.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import org.simpleframework.xml.Attribute;

import java.util.Date;

public class Movie {
    @Attribute
    @CsvBindByPosition(position = 0)
    protected Long id;
    @Attribute
    @CsvBindByPosition(position = 1)
    protected String name;
    @Attribute
    @CsvDate(value = "dd-MM-yyyy")
    @CsvBindByPosition(position = 2)
    protected Date release;
    @Attribute
    @CsvBindByPosition(position = 3)
    protected Integer ageRestriction;
    @Attribute
    @CsvBindByPosition(position = 4)
    protected TypeMovie typeMovie;

    public Movie() {
    }

    public Movie(Long id, String name, Date release, Integer ageRestriction) {
        this.id = id;
        this.name = name;
        this.release = release;
        this.ageRestriction = ageRestriction;
    }

    public Movie(Long id, String name, Date release, Integer ageRestriction, TypeMovie typeMovie) {
        this.id = id;
        this.name = name;
        this.release = release;
        this.ageRestriction = ageRestriction;
        this.typeMovie = typeMovie;
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

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public Integer getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Integer ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public TypeMovie getTypeMovie() {
        return typeMovie;
    }

    public void setTypeMovie(TypeMovie typeMovie) {
        this.typeMovie = typeMovie;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", release=" + release +
                ", ageRestriction=" + ageRestriction +
                ", typeMovie=" + typeMovie +
                '}';
    }
}
