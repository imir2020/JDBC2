package by.javaguru.je.mapper;

public interface Mapper<T, F> {

    T mapFrom(F object);
}
