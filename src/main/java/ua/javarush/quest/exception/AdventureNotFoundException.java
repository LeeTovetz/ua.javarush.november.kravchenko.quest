package ua.javarush.quest.exception;

public class AdventureNotFoundException extends RuntimeException {
    public AdventureNotFoundException(String message) {
        super(message);
    }
}
