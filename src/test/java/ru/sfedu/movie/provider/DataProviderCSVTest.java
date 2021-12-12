package ru.sfedu.movie.provider;

import org.junit.jupiter.api.Test;
import ru.sfedu.movie.TestInstance;
import ru.sfedu.movie.model.Cartoon;
import ru.sfedu.movie.model.Film;
import ru.sfedu.movie.model.Poster;
import ru.sfedu.movie.model.Ticket;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCSVTest {
    DataProviderCSV provider = new DataProviderCSV();

    @Test
    void testCrudTicket() throws Exception {
        final Ticket t1 = TestInstance.getTicket1();
        final Ticket t0 = TestInstance.getTicket2();

//        create movie and poster
        assertTrue(provider.addCartoon((Cartoon) t1.getSeance().getMovie()).isPresent());
        assertTrue(provider.addPoster(t1.getSeance()).isPresent());
//        check creating movie and poster
        Optional<Poster> optionalPoster = provider.findPosterById(t1.getSeance().getId());
        assertTrue(optionalPoster.isPresent());
        System.out.println(optionalPoster);

//        positive save
        assertTrue(provider.addTicket(t1).isPresent());
//        check unique id
        t0.setId(t1.getId());
        assertThrows(Exception.class, () -> {provider.addTicket(t0);});
//        check limit space
        final Ticket t2 = TestInstance.getTicket2();
        assertThrows(Exception.class, () -> {provider.addTicket(t2);});

//        check delete poster and cascade delete tickets
        provider.deletePosterById(t1.getSeance().getId());
        assertFalse(provider.findPosterById(t1.getSeance().getId()).isPresent());
        assertFalse(provider.findTicketById(t1.getId()).isPresent());
//        check not delete movie
        assertTrue(provider.findCartoonById(t1.getSeance().getMovie().getId()).isPresent());

//        find all
        t1.setSeance(TestInstance.getPoster2());
        t2.setSeance(TestInstance.getPoster2());
        assertTrue(provider.addCartoon((Cartoon) t1.getSeance().getMovie()).isPresent());
        assertTrue(provider.addPoster(t1.getSeance()).isPresent());
        assertTrue(provider.addTicket(t1).isPresent());
        assertTrue(provider.addTicket(t2).isPresent());
        List<Ticket> tickets = provider.findTicketBySeanceId(t1.getSeance().getId());
        assertEquals(tickets.size(), 2);
        tickets.forEach(System.out::println);

//        check delete movie and cascade delete posters and tickets
        provider.deleteAllCartoons();
        assertTrue(provider.findAllCartoons().isEmpty());
        assertTrue(provider.findAllPosters().isEmpty());
        assertTrue(provider.findAllTickets().isEmpty());
    }

    @Test
    void testCrudPoster() throws Exception {
        final Poster p1 = TestInstance.getPoster1();
        final Poster p0 = TestInstance.getPoster2();
//        check composition
        assertThrows(Exception.class, () -> provider.addPoster(p1));

        assertTrue(provider.addCartoon((Cartoon) p1.getMovie()).isPresent());
        assertTrue(provider.addPoster(p1).isPresent());
//        find by id
        Optional<Poster> optionalPoster = provider.findPosterById(p1.getId());
        assertTrue(optionalPoster.isPresent());
        System.out.println(optionalPoster);

//        check unique id
        p0.setId(p1.getId());
        assertThrows(Exception.class, () -> provider.addPoster(p0));

//        save 2 poster
        final Poster p2 = TestInstance.getPoster2();
        assertTrue(provider.addCartoon((Cartoon) p2.getMovie()).isPresent());
        assertTrue(provider.addPoster(p2).isPresent());

//        find all
        assertEquals(provider.findAllPosters().size(), 2);

//        check cascade delete
        provider.deleteCartoonById(p1.getMovie().getId());
        assertFalse(provider.findCartoonById(p1.getMovie().getId()).isPresent());
        assertFalse(provider.findPosterById(p1.getId()).isPresent());

//        check delete poster
        provider.deleteAllPosters();
        assertTrue(provider.findAllPosters().isEmpty());
        assertFalse(provider.findAllCartoons().isEmpty());

//        delete other movies
        provider.deleteAllCartoons();
        assertTrue(provider.findAllCartoons().isEmpty());
    }


    @Test
    void testCrudCartoon() throws Exception {
        final Cartoon c1 = TestInstance.getCartoon1();
        final Cartoon c2 = TestInstance.getCartoon2();

//        positive
        assertTrue(provider.addCartoon(c1).isPresent());
//        check unique id
        c2.setId(c1.getId());
        assertThrows(Exception.class, () -> provider.addCartoon(c2));
//        check unique name
        c2.setName(c1.getName());
        assertThrows(Exception.class, () -> provider.addCartoon(c2));

//        find by id
        c2.setId(TestInstance.getCartoon2().getId());
        assertTrue(provider.findCartoonById(c1.getId()).isPresent());
        assertFalse(provider.findCartoonById(c2.getId()).isPresent());

//        find by name
        c2.setName(TestInstance.getCartoon2().getName());
        assertTrue(provider.findCartoonByName(c1.getName()).isPresent());
        assertFalse(provider.findCartoonByName(c2.getName()).isPresent());

//        find all
        assertTrue(provider.addCartoon(c2).isPresent());
        assertEquals(provider.findAllCartoons().size(), 2);

//        find by age before
        int ageBefore = 6;
        provider.findCartoonByAgeBefore(ageBefore).forEach(cartoon -> {
            if (cartoon.getAgeRestriction() > ageBefore) {
                fail("DataProviderCSV.findCartoonByAgeBefore failed!");
            }
        });

//        find by age after
        int ageAfter = 6;
        provider.findCartoonByAgeAfter(ageAfter).forEach(cartoon -> {
            if (cartoon.getAgeRestriction() <= ageAfter) {
                fail("DataProviderCSV.findCartoonByAgeAfter failed!");
            }
        });

//        delete by id
        provider.deleteCartoonById(c1.getId());
        assertFalse(provider.findCartoonById(c1.getId()).isPresent());

//        delete by name
        provider.deleteCartoonByName(c2.getName());
        assertFalse(provider.findCartoonByName(c2.getName()).isPresent());

//        delete all
        provider.addCartoon(c1);
        provider.addCartoon(c2);

        provider.deleteAllCartoons();
        assertEquals(provider.findAllCartoons().size(), 0);

    }

    @Test
    void testCrudFilm() throws Exception {
        final Film c1 = TestInstance.getFilm1();
        final Film c2 = TestInstance.getFilm2();

//        positive
        assertTrue(provider.addFilm(c1).isPresent());
//        check unique id
        c2.setId(c1.getId());
        assertThrows(Exception.class, () -> provider.addFilm(c2));
//        check unique name
        c2.setName(c1.getName());
        assertThrows(Exception.class, () -> provider.addFilm(c2));

//        find by id
        c2.setId(TestInstance.getFilm2().getId());
        assertTrue(provider.findFilmById(c1.getId()).isPresent());
        assertFalse(provider.findFilmById(c2.getId()).isPresent());

//        find by name
        c2.setName(TestInstance.getFilm2().getName());
        assertTrue(provider.findFilmByName(c1.getName()).isPresent());
        assertFalse(provider.findFilmByName(c2.getName()).isPresent());

//        find all
        assertTrue(provider.addFilm(c2).isPresent());
        assertEquals(provider.findAllFilms().size(), 2);

//        find by age before
        int ageBefore = 6;
        provider.findFilmByAgeBefore(ageBefore).forEach(film -> {
            if (film.getAgeRestriction() > ageBefore) {
                fail("DataProviderCSV.findFilmByAgeBefore failed!");
            }
        });

//        find by age after
        int ageAfter = 6;
        provider.findFilmByAgeAfter(ageAfter).forEach(film -> {
            if (film.getAgeRestriction() <= ageAfter) {
                fail("DataProviderCSV.findFilmByAgeAfter failed!");
            }
        });

//        delete by id
        provider.deleteFilmById(c1.getId());
        assertFalse(provider.findFilmById(c1.getId()).isPresent());

//        delete by name
        provider.deleteFilmByName(c2.getName());
        assertFalse(provider.findFilmByName(c2.getName()).isPresent());

//        delete all
        provider.addFilm(c1);
        provider.addFilm(c2);

        provider.deleteAllFilms();
        assertEquals(provider.findAllFilms().size(), 0);

    }

}