package ru.sfedu.movie.model;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.movie.provider.DataProviderCSV;

public class CsvPosterMovieConverter extends AbstractBeanField<Movie, String> {

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Movie movie = (Movie) value;
        return super.convertToWrite(String.format("%s:%d", movie.getTypeMovie().toString(), movie.getId()));
    }

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String[] strings = s.split(":");
        TypeMovie typeMovie = TypeMovie.valueOf(strings[0]);
        return new DataProviderCSV().findMovieById(typeMovie, Long.parseLong(strings[1])).orElseThrow(NullPointerException::new);
    }
}
