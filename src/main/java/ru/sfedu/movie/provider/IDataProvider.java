package ru.sfedu.movie.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import ru.sfedu.movie.model.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static ru.sfedu.movie.Constant.*;
import static ru.sfedu.movie.utils.ConfigurationUtil.getConfigurationEntry;

public interface IDataProvider {
    Logger log = LogManager.getLogger();

    Optional<Ticket> addTicket(Ticket ticket) throws Exception;
    Optional<Ticket> findTicketById(Long id);
    List<Ticket> findTicketBySeanceId(Long seanceId);
    List<Ticket> findAllTickets();
    void deleteTicketById(Long id);
    void deleteAllTickets();
    boolean isFreeSpace(Poster seance);

    Optional<Poster> addPoster(Poster poster) throws Exception;
    Optional<Poster> findPosterById(Long id);
    List<Poster> findPostersByMovieName(String movieName);
    List<Poster> findPostersByShowTime(Date showTime);
    List<Poster> findPostersByMovieNameAndShowTime(String movieName, Date showTime);
    List<Poster> findAllPosters();
    void deletePosterById(Long id);
    void deleteAllPosters();

    Optional<Cartoon> addCartoon(Cartoon cartoon) throws Exception;
    Optional<Cartoon> findCartoonById(Long id);
    Optional<Cartoon> findCartoonByName(String name);
    List<Cartoon> findCartoonByAgeBefore(Integer age);
    List<Cartoon> findCartoonByAgeAfter(Integer age);
    List<Cartoon> findAllCartoons();
    void deleteCartoonById(Long id);
    void deleteCartoonByName(String name);
    void deleteAllCartoons();

    Optional<Film> addFilm(Film film) throws Exception;
    Optional<Film> findFilmById(Long id);
    Optional<Film> findFilmByName(String name);
    List<Film> findFilmByAgeBefore(Integer age);
    List<Film> findFilmByAgeAfter(Integer age);
    List<Film> findAllFilms();
    void deleteFilmById(Long id);
    void deleteFilmByName(String name);
    void deleteAllFilms();

    default Optional<? extends Movie> findMovieById(TypeMovie type, Long id) {
        switch (type) {
            case FILM:
                return findFilmById(id);
            case CARTOON:
                return findCartoonById(id);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
    default Optional<? extends Movie> findMovieByName(TypeMovie type, String name) {
        switch (type) {
            case FILM:
                return findFilmByName(name);
            case CARTOON:
                return findCartoonByName(name);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
    default void saveToLog(History object) {
        try (MongoClient client = MongoClients.create(getConfigurationEntry(MONGO_CONNECT))) {
            final MongoDatabase database = client.getDatabase(getConfigurationEntry(MONGO_DATABASE));
            final MongoCollection<Document> collection = database.getCollection(getConfigurationEntry(MONGO_COLLECTION));

            Document document = Document.parse(new ObjectMapper().writeValueAsString(object));
            collection.insertOne(document);
        } catch (Exception exception) {
            log.error(exception);
        }
    }
}
