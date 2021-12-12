package ru.sfedu.movie;

public final class Constant {
    public static final String ENV_PROP_KEY = "env";
    public static final String ENV_PROP_VALUE = "src/main/resources/env.properties";

    public static final String DB_DRIVER_ENV_KEY = "dbDriver";
    public static final String DB_CONNECT_ENV_KEY = "dbConnect";
    public static final String DB_USER_ENV_KEY = "dbUser";
    public static final String DB_PASS_ENV_KEY = "dbPass";
    public static final String DB_SCHEMA_SCRIPT_ENV_KEY = "dbSchemaScript";

    public static final String DB_CHECK_TABLES = "";

    public static final String CSV_ENV_POSTER_KEY = "csvPoster";
    public static final String XML_ENV_POSTER_KEY = "xmlPoster";
    public static final String THROW_POSTER_UNIQUE_ID = "Poster id %d already exists";
    public static final String THROW_POSTER_UNIQUE_TIME = "Poster time \"%s\" already exists";
    public static final String THROW_POSTER_EXISTS_MOVIE = "Poster move \"%s\" is not exists";
    public static final String DELETE_POSTER_CASCADE_MOVIE = "delete from Poster where movie=%d and typeMovie='%s';";
    public static final String DELETE_POSTER_ID = "delete from Poster where id=%d;";
    public static final String DELETE_POSTER_ALL = "delete from Poster;";
    public static final String SELECT_POSTER = "select id, movie, typeMovie, showTime, numberOfSeats from Poster;";
    public static final String INSERT_POSTER = "insert into Poster (id, movie, typeMovie, showTime, numberOfSeats) values (%d, %d, '%s', %d, %d);";

    public static final String CSV_ENV_CARTOON_KEY = "csvCartoon";
    public static final String XML_ENV_CARTOON_KEY = "xmlCartoon";
    public static final String THROW_CARTOON_UNIQUE_ID = "Cartoon id %d already exists";
    public static final String THROW_CARTOON_UNIQUE_NAME = "Cartoon name \"%s\" already exists";
    public static final String INSERT_CARTOON = "insert into Cartoon values (%d, '%s', %d, %d, '%s');";
    public static final String UPDATE_CARTOON = "update Cartoon set name=%s, release=%d, ageRestriction=%d where id=%d;";
    public static final String SELECT_CARTOON = "select id, name, release, ageRestriction from Cartoon;";
    public static final String DELETE_CARTOON_ID = "delete from Cartoon where id=%d;";
    public static final String DELETE_CARTOON_ALL = "delete from Cartoon;";
    public static final String DELETE_CARTOON_NAME = "delete from Cartoon where name=%s;";

    public static final String CSV_ENV_FILM_KEY = "csvFilm";
    public static final String XML_ENV_FILM_KEY = "xmlFilm";
    public static final String THROW_FILM_UNIQUE_ID = "Film id %d already exists";
    public static final String THROW_FILM_UNIQUE_NAME = "Film name \"%s\" already exists";
    public static final String INSERT_FILM = "insert into Film values (%d, '%s', %d, %d, '%s');";
    public static final String SELECT_FILM = "select id, name, release, ageRestriction from Film;";
    public static final String DELETE_FILM_ID = "delete from Film where id=%d;";

    public static final String CSV_ENV_TICKET_KEY = "csvTicket";
    public static final String XML_ENV_TICKET_KEY = "xmlTicket";
    public static final String THROW_TICKET_UNIQUE_ID = "Ticket id %d already exists";
    public static final String THROW_TICKET_NOT_FREE_SPACE = "There are no empty seats";
    public static final String INSERT_TICKET = "insert into Ticket (id, seance, age, name, discount) values (%d, %d, %d, '%s', %d);";
    public static final String SELECT_TICKET = "select id, seance, age, name, discount from Ticket;";
    public static final String DELETE_TICKET_ID = "delete from Ticket where id=%d;";
    public static final String DELETE_TICKET_ALL = "delete from Ticket;";

    public static final String MONGO_CONNECT = "mongoConnect";
    public static final String MONGO_DATABASE = "mongoDatabase";
    public static final String MONGO_COLLECTION = "mongoCollection";
}
