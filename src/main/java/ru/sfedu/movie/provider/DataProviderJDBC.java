package ru.sfedu.movie.provider;

import ru.sfedu.movie.model.*;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static java.lang.String.format;
import static java.nio.file.Files.readString;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;
import static ru.sfedu.movie.Constant.*;
import static ru.sfedu.movie.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderJDBC implements IDataProvider {
    private final Connection connection;

    public DataProviderJDBC() throws Exception {
        connection = openConnection();
        execute(readString(get(getConfigurationEntry(DB_SCHEMA_SCRIPT_ENV_KEY)), StandardCharsets.UTF_8), null, null);
    }

    @Override
    public Optional<Ticket> addTicket(Ticket ticket) throws Exception {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        if (!isFreeSpace(ticket.getSeance())) {
            throw new Exception(THROW_TICKET_NOT_FREE_SPACE);
        }
        return Optional.ofNullable(execute(format(INSERT_TICKET,
                ticket.getId(),
                ticket.getSeance().getId(),
                ticket.getAge(),
                ticket.getName(),
                ticket.getDiscount() ? 1 : 0), currentMethod, ticket) ? ticket : null);
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
        return null;
    }

    @Override
    public void deleteTicketById(Long id) {

    }

    @Override
    public void deleteAllTickets() {

    }


    @Override
    public boolean isFreeSpace(Poster seance) {
        final long count = findTicketBySeanceId(seance.getId()).size();
        return seance.getNumberOfSeats() > count;
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


    private Connection openConnection() throws Exception {
        Class.forName(getConfigurationEntry(DB_DRIVER_ENV_KEY));
        Connection connection = DriverManager.getConnection(
                getConfigurationEntry(DB_CONNECT_ENV_KEY),
                getConfigurationEntry(DB_USER_ENV_KEY),
                getConfigurationEntry(DB_PASS_ENV_KEY)
        );
        connection.setAutoCommit(true);
        return connection;
    }

    private <T> boolean execute(String sql, String method, T obj) {
        log.debug(sql);
        try {
           //             final Connection connection = openConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            saveToLog(new History(new Date(), new Throwable().getStackTrace()[0].getMethodName(), obj));
            return true;
        } catch (Exception exception) {
            log.error(exception);
        }
        return false;
    }

    private ResultSet select(String sql) {
        ResultSet resultSet = null;
        try {
//            final Connection connection = openConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            log.error(e);
        }
        return resultSet;
    }
}
