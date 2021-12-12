package ru.sfedu.movie;

import ru.sfedu.movie.model.Cartoon;
import ru.sfedu.movie.model.Film;
import ru.sfedu.movie.model.Poster;
import ru.sfedu.movie.model.Ticket;

import java.util.Date;

public final class TestInstance {
    public static Ticket getTicket1() {
        return new Ticket(1L, getPoster1(), 11, "Boris", true);
    }
    public static Ticket getTicket2() {
        return new Ticket(2L, getPoster1(), 11, "Sonya", true);
    }

    public static Poster getPoster1() {
        return new Poster(1L, getCartoon1(), new Date(), 1);
    }
    public static Poster getPoster2() {
        return new Poster(2L, getCartoon2(), new Date(), 2);
    }

    public static Cartoon getCartoon1() {
        return new Cartoon(1L, "Madagascar", new Date(), 5);
    }
    public static Cartoon getCartoon2() {
        return new Cartoon(2L, "Adam family", new Date(), 8);
    }

    public static Film getFilm1() {
        return new Film(1L, "Titanic", new Date(), 5);
    }
    public static Film getFilm2() {
        return new Film(2L, "Harry Potter", new Date(), 8);
    }
}
