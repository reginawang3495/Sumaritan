package CamHacks;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GmailSendFrame extends JFrame{
	static String answer = "";
    FlowLayout experimentLayout = new FlowLayout();

    static String text;
    private JButton thirdButton = new JButton("OK");
    JTextField subject = new JTextField();

    JTextField recipient = new JTextField();
    JTextField username = new JTextField();
    JTextField password = new JTextField();

    
   public GmailSendFrame(String text)
   {
	   this.text = text;

        JPanel group1 = new JPanel();
        group1.setLayout(new BoxLayout(group1, BoxLayout.PAGE_AXIS));
        this.add(group1);
        
        setLayout(new GridLayout(1,1));
        //group1.setSize( 1024, 768 );
        group1.setBorder(BorderFactory.createLineBorder(Color.RED));

        subject.setText("Subject");
        group1.add(subject);
        recipient.setText("Recipient Username");
        group1.add(recipient);

        JPanel group2 = new JPanel();
        group2.setLayout(new BoxLayout(group2, BoxLayout.PAGE_AXIS));
        username.setText("Username");
        group1.add(username);
        password.setText("Password");
        group1.add(password);
        this.add(group2);
      //  setLayout(new GridLayout(1,3));
        //group2.setSize( 1024, 768 );
        group2.setBorder(BorderFactory.createLineBorder(Color.blue)); 
        group2.add(thirdButton);
        thirdButton.setLayout(null);
        thirdButton.setBounds(400,300,100,100);
        thirdButton.setAlignmentX(CENTER_ALIGNMENT);
        
       ActionListener actionListener3 = new ActionListener() 
       {
           public void actionPerformed(ActionEvent e)
           {
        	   Gmail.main(subject.getText(),username.getText(),password.getText(),recipient.getText());
               dispose();
            }
       };
       thirdButton.addActionListener(actionListener3);
   }
   //FlowlayoutExperiment frame = new FlowlayoutExperiment();
   
   public static String main(String text) 
   {
	   GmailSendFrame frame = new GmailSendFrame(text); 
       frame.setTitle("Send Gmail");
        frame.setSize(600, 150);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        while(answer.length()!=1){
        	try{
        		Thread.sleep(100);
        	}
        	catch(Exception e){}
        	}
        return answer;
    }
}