package nuc.sw.shopping.service;

import java.util.Set;

import nuc.sw.shopping.entity.*;

public interface UserService {
    int log(String acount,String type,String password);
    void storeInfo(User newUser);
    void buyBook(Book book,int number);
    public Set<User> getOrderInfo();
}
