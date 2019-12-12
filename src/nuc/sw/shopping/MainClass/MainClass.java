package nuc.sw.shopping.MainClass;


import java.util.Scanner;
import javax.sql.rowset.serial.SerialArray;
import nuc.sw.shopping.service.*;
import nuc.sw.shopping.entity.*;
import nuc.sw.shopping.frame.LogFrame;

public class MainClass {
    
    public static void main(String[] args) {
	
    	UserServiceImpl user = new UserServiceImpl();
    	new LogFrame(user);
    }

}

