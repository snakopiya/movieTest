package ru.sfedu.movie.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.movie.model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static ru.sfedu.movie.Constant.*;
import static ru.sfedu.movie.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderXML implements IDataProvider {

    @Override
    public Optional<Ticket> addTicket(Ticket ticket) throws Exception {
        List<Ticket> tickets = findAllTickets();

        if (!isFreeSpace(ticket.getSeance())) {
            throw new Exception(THROW_TICKET_NOT_FREE_SPACE);
        } else if (tickets.stream().anyMatch(var -> var.getId().equals(ticket.getId()))) {
            throw new Exception(String.format(THROW_TICKET_UNIQUE_ID, ticket.getId()));
        }

        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        return execute(XML_ENV_TICKET_KEY, currentMethod, tickets, ticket);
    }

    @Override
    public Optional<Ticket> findTicketById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Ticket> findTicketBySeanceId(Long seanceId) {
        return null;
    }


    @Override
    public List<Ticket> findAllTickets() {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        return select(XML_ENV_TICKET_KEY, currentMethod);
    }

    @Override
    public void deleteTicketById(Long id) {

    }

    @Override
    public void deleteAllTickets() {

    }

    @Override
    public boolean isFreeSpace(Poster poster) {
        final long count = findTicketBySeanceId(poster.getId()).size();
        return poster.getNumberOfSeats() > count;
    }

    @Override
    public Optional<Poster> addPoster(Poster poster) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Poster> findPosterById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Poster> findPostersByMovieName(String movieName) {
        return null;
    }

    @Override
    public List<Poster> findPostersByShowTime(Date showTime) {
        return null;
    }

    @Override
    public List<Poster> findPostersByMovieNameAndShowTime(String movieName, Date showTime) {
        return null;
    }

    @Override
    public List<Poster> findAllPosters() {
        return null;
    }

    @Override
    public void deletePosterById(Long id) {

    }

    @Override
    public void deleteAllPosters() {

    }

    @Override
    public Optional<Cartoon> addCartoon(Cartoon cartoon) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Cartoon> findCartoonById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Cartoon> findCartoonByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Cartoon> findCartoonByAgeBefore(Integer age) {
        return null;
    }

    @Override
    public List<Cartoon> findCartoonByAgeAfter(Integer age) {
        return null;
    }

    @Override
    public List<Cartoon> findAllCartoons() {
        return null;
    }

    @Override
    public void deleteCartoonById(Long id) {

    }

    @Override
    public void deleteCartoonByName(String name) {

    }

    @Override
    public void deleteAllCartoons() {

    }

    @Override
    public Optional<Film> addFilm(Film film) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Film> findFilmById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Film> findFilmByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Film> findFilmByAgeBefore(Integer age) {
        return null;
    }

    @Override
    public List<Film> findFilmByAgeAfter(Integer age) {
        return null;
    }

    @Override
    public List<Film> findAllFilms() {
        return null;
    }

    @Override
    public void deleteFilmById(Long id) {

    }

    @Override
    public void deleteFilmByName(String name) {

    }

    @Override
    public void deleteAllFilms() {

    }


    private <T> Optional<T> execute(String xmlKey, String method, List<T> list, T t) {
        list.add(t);
        list = executeAll(xmlKey, method, list);

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(t);
    }

    private <T> List<T> executeAll(String xmlKey, String method, List<T> list) {
        try {
            FileWriter fileWriter = new FileWriter(getConfigurationEntry(xmlKey));
            Serializer serializer = new Persister();

            Container<T> container = new Container<T>(list);
            serializer.write(container, fileWriter);

            fileWriter.close();

//            saveToLog(new History(new Date(), method, container.getList()));
            return container.getList();
        } catch (Exception exception) {
            log.error(exception);
        }
        return Collections.emptyList();
    }

    private <T> List<T> select(String xmlKey, String method) {
        try {
            FileReader reader = new FileReader(getConfigurationEntry(xmlKey));
            Serializer serializer = new Persister();

            Container<T> container = serializer.read(Container.class, reader);

            if (container.getList() != null) {
//                saveToLog(new History(new Date(), method, container.getList()));
                return container.getList();
            }
        } catch (Exception exception) {
            log.error(exception);
        }
        return new ArrayList<>();
    }

    @Root(name = "list")
    private static class Container<T> {
        @ElementList(inline = true, required = false)
        private List<T> list;

        public Container() {
        }

        private Container(List<T> list) {
            this.list = list;
        }

        public List<T> getList() {
            return list;
        }
    }
}
