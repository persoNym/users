package users;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Users implements Serializable {
    

    
    public static void main(String[] args) {
    
        
        
        System.out.println("Security java program by Victoria");
       java.util.Date date = new java.util.Date();
       System.out.println("Last acessed:" + date.toGMTString());
       try{
       Administrator AdministratorInstance = new Administrator();
       }
      
      catch (IOException io) { 
        System.out.println("General I/O exception: " + io.getMessage() );
        io.printStackTrace();
        System.exit(-1);
      }

    }
}
