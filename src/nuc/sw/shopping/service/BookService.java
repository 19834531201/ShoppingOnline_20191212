package nuc.sw.shopping.service;
import nuc.sw.shopping.entity.*;
import nuc.sw.shopping.exception.BookException;

public interface BookService {
    Book[] queryBooks();
    Book queryById(String id);
    Book queryByName(String name);
}
