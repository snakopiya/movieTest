package ru.sfedu.movie.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

@Root(name = "poster")
public class Poster {
    @Attribute
    @CsvBindByPosition(position = 0)
    private Long id;
    @Element
    @CsvCustomBindByPosition(position = 1, converter = CsvPosterMovieConverter.class)
    private Movie movie;
    @Attribute
    @CsvBindByPosition(position = 2)
    private TypeMovie typeMovie;
    @Attribute
    @CsvDate(value = "dd-MM-yyyy")
    @CsvBindByPosition(position = 3)
    private Date showTime;
    @Attribute
    @CsvBindByPosition(position = 4)
    private Integer numberOfSeats;

    public Poster() {
    }

    public Poster(Long id, Movie movie, Date showTime, Integer numberOfSeats) {
        this.id = id;
        this.movie = movie;
        this.typeMovie = movie.getTypeMovie();
        this.showTime = showTime;
        this.numberOfSeats = numberOfSeats;
    }

    public Poster(Long id, Movie movie, TypeMovie typeMovie, Date showTime, Integer numberOfSeats) {
        this.id = id;
        this.movie = movie;
        this.typeMovie = typeMovie;
        this.showTime = showTime;
        this.numberOfSeats = numberOfSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public TypeMovie getTypeMovie() {
        return typeMovie;
    }

    public void setTypeMovie(TypeMovie typeMovie) {
        this.typeMovie = typeMovie;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return "Poster{" +
                "id=" + id +
                ", movie=" + movie +
                ", typeMovie=" + typeMovie +
                ", showTime=" + showTime +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
