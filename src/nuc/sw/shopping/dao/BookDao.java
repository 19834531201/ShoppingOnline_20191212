package nuc.sw.shopping.dao;
import java.util.Set;

import nuc.sw.shopping.entity.*;

public interface BookDao {
    Book queryById(String id) ;
    Set<Book> queryBooks();
    Book queryByName(String name);
    void deleteBook(Book book);
}
