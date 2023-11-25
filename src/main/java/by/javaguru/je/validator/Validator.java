package by.javaguru.je.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}