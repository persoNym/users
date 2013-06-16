
package users;

import java.util.*;
import java.io.*; 
import java.security.*;
import java.util.logging.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Administrator extends JFrame  implements ActionListener { 
    

private static String[] menu = {"1. New User","2. Log in",
"3. Change Password","4. Log off","5. List users","6. Find User", "7. Quit"}; 
ArrayList<Person> userList = new ArrayList();
Person currentUser = null;
String UID;


JFrame frame = new JFrame("Log In Menu");
     
JTabbedPane tabbedPane = new JTabbedPane();
JPanel newUserCard = new JPanel();  //for newUser function
JButton submitButton = new JButton("SUBMIT");

JButton quitButton = new JButton("QUIT");


JTextArea data = new JTextArea(3, 40); //for userlist data
JScrollPane userPane = new JScrollPane( data );
        
JTextField textName = new JTextField(" ",20);
JTextField textUID = new JTextField(" ",20);
JTextField textPWord = new JTextField(" ",20);
JTextField textPPWord = new JTextField(" ",20);

StringBuilder S = new StringBuilder("\t\tREGISTERED USERS \n\n");



public Administrator () throws FileNotFoundException, IOException {
   
        Person newPerson = null;
    try{ 
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.txt"));
        userList = (ArrayList<Person>) ois.readObject();
        for(int k = 0; k <userList.size(); k++) {
        S.append(userList.get(k).toString());
        S.append("\n");
    }
        
        ois.close(); //close stream
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
        }    catch (FileNotFoundException e) {
        System.err.println("FileNotFoundException: File user.txt does not exist " + e);
                                    }
    try{
        
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.txt"));
        oos.writeObject(userList);
        }
    catch(IOException io) {
    System.out.println("IOException: Problem reading file user.txt. Throw to main " + io);
    throw io;
                          }

 newUserCard.setLayout(new BoxLayout(newUserCard, BoxLayout.Y_AXIS));
    
        JPanel name = new JPanel( new FlowLayout());
        name.add(BorderLayout.CENTER,new JLabel("Name"));
        name.add(new JLabel("              "));
        name.add(BorderLayout.EAST,textName);
         
      
        JPanel UID = new JPanel( new FlowLayout());
        UID.add(BorderLayout.CENTER,new JLabel("Username"));
        UID.add(new JLabel("      "));
        UID.add(BorderLayout.EAST,textUID);
        
        
        JPanel pword = new JPanel( new FlowLayout());
        pword.add(BorderLayout.CENTER,new JLabel("Password"));
        pword.add(new JLabel("      "));
        pword.add(BorderLayout.EAST,textPWord);
        
        JPanel ppword = new JPanel( new FlowLayout());
        ppword.add(BorderLayout.CENTER,new JLabel("Verify"));
        ppword.add(new JLabel("              "));
        ppword.add(BorderLayout.EAST,textPPWord);
        
        JPanel BPanel = new JPanel(new FlowLayout());
        BPanel.add(new JLabel("                         "));
        BPanel.add(BorderLayout.CENTER,submitButton);
        BPanel.add(BorderLayout.CENTER,quitButton);
        
        newUserCard.add(name);
        newUserCard.add(UID);
        newUserCard.add(pword);
        newUserCard.add(ppword);
        newUserCard.add(BPanel);
                 
        submitButton.addActionListener(this);
        quitButton.addActionListener(this);
        
        JPanel newUserPanel = new JPanel();
        data.setText(S.toString());
        
        Container pane = getContentPane();
        
        pane.add(BorderLayout.WEST, newUserCard);
        pane.add(BorderLayout.EAST, userPane);
        
        newUserPanel.add(pane);
        tabbedPane.addTab( "New User", pane);
        
        frame.add(pane);
        frame.setSize(900,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);  
}

//----------------------------------------------------------------------------------------
          //compute Hash
     public static byte[] computeHash( String x )   
     throws NoSuchAlgorithmException  
     {
          MessageDigest d = MessageDigest.getInstance("SHA-1");
          d.update(x.getBytes());
          return  d.digest();
     }
//----------------------------------------------------------------------------------------
     //write users to file
     public void writeUsers() {
         
        try{
          ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.txt")); 
          oos.writeObject(userList);
           
            oos.close();
            System.out.print("Information has been saved to user.txt\n");
         
         }
        catch(FileNotFoundException fnf) {
            System.err.println("FileNotFoundException: File user.txt does not exist " + fnf);
        }
        catch(IOException eyeoh) {
            System.err.println("IOException: Error writing to user.txt " + eyeoh);
        }
     }       
     
//----------------------------------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == submitButton) {
            String P1;
            String P2;
            P1 = textPWord.getText();
            P2 = textPPWord.getText();
            if(P1.equals(P2)){ 
                try { //string compare and encrypts the password
                    P1 = new String(computeHash(P1));
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Administrator.class.getName()).log(Level.SEVERE, null, ex);
                }

            int v = userList.size();
            Person newPerson = null;
            newPerson = new Person(textName.getText(), textUID.getText(), P1 );
            userList.add(newPerson);
            S.append(userList.get(v).toString());
            data.setText(S.toString());
            System.out.println(userList); //debug
            writeUsers();
            textName.setText("");
            textUID.setText("");
            textPWord.setText("");
            textPPWord.setText("");
            repaint();
        }
            else{ //popup to show incorrect password
               JPanel panel1 = new JPanel(new GridBagLayout());
               GridBagConstraints c = new GridBagConstraints();
               JFrame frame1 = new JFrame("\t\tERROR");
               JLabel label = new JLabel("INVALID PASSWORD");
               c.gridx = 1;
               c.gridy = 1;
               
               panel1.add(label,c);
               frame1.add(BorderLayout.CENTER,panel1);
               frame1.setSize(250,100);
               frame1.setLocationRelativeTo(null);
               frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
               frame1.setVisible(true); 

               textPWord.setText("");
               textPPWord.setText("");
               repaint();
               System.out.println("Password Error");
               
               
            }
          
        }
        
        else if(ae.getSource() == quitButton) { //sets everything back to null and writes to the user.txt
            textName.setText("");
            textUID.setText("");
            textPWord.setText("");
            textPPWord.setText("");
            writeUsers();
            System.out.println(S);
            System.exit(0);
        }
    }
    
    public void paint(Graphics g){
        super.paint(g);
        data.setText(S.toString());
    }
    
   
}
