package ru.sfedu.movie.model;

import org.simpleframework.xml.Root;

import java.util.Date;

@Root(name = "cartoon")
public class Cartoon extends Movie {

    public Cartoon() {
    }

    public Cartoon(Long id, String name, Date release, Integer ageRestriction) {
        super(id, name, release, ageRestriction, TypeMovie.CARTOON);
    }

    @Override
    public String toString() {
        return "Cartoon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", release=" + release +
                ", ageRestriction=" + ageRestriction +
                '}';
    }
}
