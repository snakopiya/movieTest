package ru.sfedu.movie.provider;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import ru.sfedu.movie.model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static ru.sfedu.movie.Constant.*;
import static ru.sfedu.movie.utils.ConfigurationUtil.getConfigurationEntry;

public class DataProviderCSV implements IDataProvider {
//    private final Logger log = LogManager.getLogger(DataProviderCSV.class);

    @Override
    public Optional<Ticket> addTicket(Ticket ticket) throws Exception {
        List<Ticket> tickets = findAllTickets();

        if (!isFreeSpace(ticket.getSeance())) {
            throw new Exception(THROW_TICKET_NOT_FREE_SPACE);
        } else if (tickets.stream().anyMatch(var -> var.getId().equals(ticket.getId()))) {
            throw new Exception(String.format(THROW_TICKET_UNIQUE_ID, ticket.getId()));
        }

        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        return execute(CSV_ENV_TICKET_KEY, ticket, currentMethod);
    }

    @Override
    public Optional<Ticket> findTicketById(Long id) {
        return findAllTickets().stream().filter(ticket -> ticket.getId().equals(id)).findAny();
    }

    @Override
    public List<Ticket> findTicketBySeanceId(Long id) {
        return findAllTickets().stream().filter(t -> t.getSeance().getId().equals(id)).collect(toList());
    }

    @Override
    public List<Ticket> findAllTickets() {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        return select(CSV_ENV_TICKET_KEY, currentMethod, Ticket.class);
    }

    @Override
    public void deleteTicketById(Long id) {
        List<Ticket> tickets = findAllTickets();
        tickets.removeIf(ticket -> ticket.getId().equals(id));
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        executeAll(CSV_ENV_TICKET_KEY, tickets, currentMethod, false);
    }

    @Override
    public void deleteAllTickets() {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        executeAll(CSV_ENV_TICKET_KEY, Collections.emptyList(), currentMethod, false);
    }

    @Override
    public boolean isFreeSpace(Poster seance) {
        final long count = findTicketBySeanceId(seance.getId()).size();
        return seance.getNumberOfSeats() > count;
    }

    private void deleteTicketByPosterId(Long posterId, String method) {
        List<Ticket> tickets = findAllTickets();
        tickets.removeIf(ticket -> ticket.getSeance().getId().equals(posterId));
        executeAll(CSV_ENV_TICKET_KEY, tickets, method, false);
    }

    private void deleteTicketByMovie(TypeMovie movie, String method) {
        List<Ticket> tickets = findAllTickets();
        tickets.removeIf(ticket -> ticket.getSeance().getTypeMovie().equals(movie));
        executeAll(CSV_ENV_TICKET_KEY, tickets, method, false);
    }

    private void deleteTicketByMovieId(TypeMovie m, Long id, String method) {
        List<Ticket> tickets = findAllTickets();
        tickets.removeIf(t -> t.getSeance().getTypeMovie().equals(m) && t.getSeance().getMovie().getId().equals(id));
        executeAll(CSV_ENV_TICKET_KEY, tickets, method, false);
    }

    private void deleteTicketByMovieName(TypeMovie m, String n, String method) {
        List<Ticket> tickets = findAllTickets();
        tickets.removeIf(t -> t.getSeance().getTypeMovie().equals(m) && t.getSeance().getMovie().getName().equals(n));
        executeAll(CSV_ENV_TICKET_KEY, tickets, method, false);
    }

    @Override
    public Optional<Poster> addPoster(Poster poster) throws Exception {
        List<Poster> posters = findAllPosters();

        if (!findMovieById(poster.getTypeMovie(), poster.getMovie().getId()).isPresent()) {
            throw new Exception(String.format(THROW_POSTER_EXISTS_MOVIE, poster.getMovie().getName()));
        }
        for (Poster var : posters) {
            if (var.getId().equals(poster.getId())) {
                throw new Exception(String.format(THROW_POSTER_UNIQUE_ID, poster.getId()));
            } else if (var.getShowTime().equals(poster.getShowTime())) {
                throw new Exception(String.format(THROW_POSTER_UNIQUE_TIME, poster.getShowTime()));
            }
        }

        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        return execute(CSV_ENV_POSTER_KEY, poster, currentMethod);
    }

    @Override
    public Optional<Poster> findPosterById(Long id) {
        return findAllPosters().stream().filter(poster -> poster.getId().equals(id)).findAny();
    }

    @Override
    public List<Poster> findPostersByMovieName(String name) {
        return findAllPosters().stream().filter(p -> p.getMovie().getName().equals(name)).collect(toList());
    }

    @Override
    public List<Poster> findPostersByShowTime(Date showTime) {
        return findAllPosters().stream().filter(p -> p.getShowTime().getTime() == showTime.getTime()).collect(toList());
    }

    @Override
    public List<Poster> findPostersByMovieNameAndShowTime(String name, Date showTime) {
        return findAllPosters().stream()
                .filter(p -> p.getShowTime().getTime() == showTime.getTime() && p.getMovie().getName().equals(name))
                .collect(toList());
    }

    @Override
    public List<Poster> findAllPosters() {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        return select(CSV_ENV_POSTER_KEY, currentMethod, Poster.class);
    }

    @Override
    public void deletePosterById(Long id) {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        deleteTicketByPosterId(id, currentMethod);

        List<Poster> posters = findAllPosters();
        posters.removeIf(poster -> poster.getId().equals(id));
        executeAll(CSV_ENV_POSTER_KEY, posters, currentMethod, false);
    }

    private void deletePosterByMovieId(String method, TypeMovie typeMovie, Long id) {
        deleteTicketByMovieId(typeMovie, id, method);

        List<Poster> posters = findAllPosters();
        posters.removeIf(poster -> poster.getTypeMovie().equals(typeMovie) && poster.getMovie().getId().equals(id));
        executeAll(CSV_ENV_POSTER_KEY, posters, method, false);
    }

    private void deletePosterByMovieName(String method, TypeMovie typeMovie, String name) {
        deleteTicketByMovieName(typeMovie, name, method);

        List<Poster> posters = findAllPosters();
        posters.removeIf(poster -> poster.getTypeMovie().equals(typeMovie) && poster.getMovie().getName().equals(name));
        executeAll(CSV_ENV_POSTER_KEY, posters, method, false);
    }

    private void deleteAllPosterByMovie(String method, TypeMovie typeMovie) {
        deleteTicketByMovie(typeMovie, method);
        List<Poster> posters = findAllPosters();
        posters.removeIf(poster -> poster.getTypeMovie().equals(typeMovie));
        executeAll(CSV_ENV_POSTER_KEY, posters, method, false);
    }

    @Override
    public void deleteAllPosters() {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        deleteAllTickets();
        executeAll(CSV_ENV_POSTER_KEY, Collections.emptyList(), currentMethod, false);
    }

    @Override
    public Optional<Cartoon> addCartoon(Cartoon cartoon) throws Exception {
        List<Cartoon> cartoons = findAllCartoons();

        for (Cartoon var : cartoons) {
            if (var.getId().equals(cartoon.getId())) {
                throw new Exception(String.format(THROW_CARTOON_UNIQUE_ID, cartoon.getId()));
            } else if (var.getName().equals(cartoon.getName())) {
                throw new Exception(String.format(THROW_CARTOON_UNIQUE_NAME, cartoon.getName()));
            }
        }

        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        return execute(CSV_ENV_CARTOON_KEY, cartoon, currentMethod);
    }

    @Override
    public Optional<Cartoon> findCartoonById(Long id) {
        return findAllCartoons().stream().filter(cartoon -> cartoon.getId().equals(id)).findAny();
    }

    @Override
    public Optional<Cartoon> findCartoonByName(String name) {
        return findAllCartoons().stream().filter(cartoon -> cartoon.getName().equals(name)).findAny();
    }

    @Override
    public List<Cartoon> findCartoonByAgeBefore(Integer age) {
        return findAllCartoons().stream().filter(cartoon -> cartoon.getAgeRestriction() < age).collect(toList());
    }

    @Override
    public List<Cartoon> findCartoonByAgeAfter(Integer age) {
        return findAllCartoons().stream().filter(cartoon -> cartoon.getAgeRestriction() > age).collect(toList());
    }

    @Override
    public List<Cartoon> findAllCartoons() {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        return select(CSV_ENV_CARTOON_KEY, currentMethod, Cartoon.class).stream()
                .sorted(Comparator.comparing(Movie::getRelease).reversed()).collect(toList());
    }

    @Override
    public void deleteCartoonById(Long id) {
        List<Cartoon> cartoons = findAllCartoons();
        cartoons.removeIf(cartoon -> cartoon.getId().equals(id));

        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        deletePosterByMovieId(currentMethod, TypeMovie.CARTOON, id);
        executeAll(CSV_ENV_CARTOON_KEY, cartoons, currentMethod, false);
    }

    @Override
    public void deleteCartoonByName(String name) {
        List<Cartoon> cartoons = findAllCartoons();
        cartoons.removeIf(cartoon -> cartoon.getName().equals(name));

        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        deletePosterByMovieName(currentMethod, TypeMovie.CARTOON, name);
        executeAll(CSV_ENV_CARTOON_KEY, cartoons, currentMethod, false);
    }

    @Override
    public void deleteAllCartoons() {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        deleteAllPosterByMovie(currentMethod, TypeMovie.CARTOON);
        executeAll(CSV_ENV_CARTOON_KEY, Collections.emptyList(), currentMethod,false);
    }

    @Override
    public Optional<Film> addFilm(Film film) throws Exception {
        List<Film> films = findAllFilms();

        for (Film var : films) {
            if (var.getId().equals(film.getId())) {
                throw new Exception(String.format(THROW_FILM_UNIQUE_ID, film.getId()));
            } else if (var.getName().equals(film.getName())) {
                throw new Exception(String.format(THROW_FILM_UNIQUE_NAME, film.getName()));
            }
        }

        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        return execute(CSV_ENV_FILM_KEY, film, currentMethod);
    }

    @Override
    public Optional<Film> findFilmById(Long id) {
        return findAllFilms().stream().filter(film -> film.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<Film> findFilmByName(String name) {
        return findAllFilms().stream().filter(film -> film.getName().equals(name)).findFirst();
    }

    @Override
    public List<Film> findFilmByAgeBefore(Integer age) {
        return  findAllFilms().stream().filter(film -> film.getAgeRestriction() < age).collect(toList());
    }

    @Override
    public List<Film> findFilmByAgeAfter(Integer age) {
        return findAllFilms().stream().filter(film -> film.getAgeRestriction() > age).collect(toList());
    }

    @Override
    public List<Film> findAllFilms() {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        return select(CSV_ENV_FILM_KEY, currentMethod, Film.class).stream()
                .sorted(Comparator.comparing(Movie::getRelease).reversed()).collect(toList());
    }

    @Override
    public void deleteFilmById(Long id) {
        List<Film> films = findAllFilms();
        films.removeIf(film -> film.getId().equals(id));

        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        deletePosterByMovieId(currentMethod, TypeMovie.FILM, id);
        executeAll(CSV_ENV_FILM_KEY, films, currentMethod, false);
    }

    @Override
    public void deleteFilmByName(String name) {
        List<Film> films = findAllFilms();
        films.removeIf(film -> film.getName().equals(name));

        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        deletePosterByMovieName(currentMethod, TypeMovie.FILM, name);
        executeAll(CSV_ENV_FILM_KEY, films, currentMethod, false);
    }

    @Override
    public void deleteAllFilms() {
        final String currentMethod = new Throwable().getStackTrace()[0].getMethodName();
        deleteAllPosterByMovie(currentMethod, TypeMovie.FILM);
        executeAll(CSV_ENV_FILM_KEY, Collections.emptyList(), currentMethod, false);
    }

    private <T> Optional<T> execute(String csvKey, T t, String method) {
        List<T> result = executeAll(csvKey, Collections.singletonList(t), method, true);

        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(result.get(0));
    }

    private <T> List<T> executeAll(String csvKey, List<T> list, String method, boolean append) {
        try {
            FileWriter fileWriter = new FileWriter(getConfigurationEntry(csvKey), append);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(list);

            csvWriter.close();
            fileWriter.close();

            saveToLog(new History(new Date(), method, list));
            return list;
        } catch (Exception exception) {
            log.error(exception);
        }
        return Collections.emptyList();
    }

    private <T> List<T> select(String csvKey, String method, Class<T> cls) {
        try {
            FileReader fileReader = new FileReader(getConfigurationEntry(csvKey));
            CSVReader csvReader = new CSVReader(fileReader);

            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader).withType(cls).build();
            List<T> list = csvToBean.parse();

            csvReader.close();
            fileReader.close();

            saveToLog(new History(new Date(), method, list));
            return list;
        } catch (Exception exception) {
            log.error(exception);
        }
        return Collections.emptyList();
    }
}
