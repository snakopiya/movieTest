package ru.sfedu.movie.model;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.movie.provider.DataProviderCSV;

public class CsvTicketPosterConverter extends AbstractBeanField<Poster, Long> {
    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        return super.convertToWrite(((Poster) value).getId());
    }

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return new DataProviderCSV().findPosterById(Long.parseLong(s)).orElseThrow(NoSuchFieldError::new);
    }
}
