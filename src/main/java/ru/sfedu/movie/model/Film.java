package ru.sfedu.movie.model;

import org.simpleframework.xml.Root;

import java.util.Date;

@Root(name = "film")
public class Film extends Movie {

    public Film() {
    }

    public Film(Long id, String name, Date release, Integer ageRestriction) {
        super(id, name, release, ageRestriction, TypeMovie.FILM);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", release=" + release +
                ", ageRestriction=" + ageRestriction +
                '}';
    }
}
