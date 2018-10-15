package CamHacks;

/**
 * Write a description of class Panel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
    // instance variables - replace the example below with your own

public class FlowlayoutExperiment extends JFrame {
	static String answer = "";
    FlowLayout experimentLayout = new FlowLayout();

    private JButton firstButton = new JButton("OK");
    private JButton secondButton = new JButton("Summarize");
    private JButton thirdButton = new JButton("Full Text");
    private JLabel label1 = new JLabel("Visual Impairment",JLabel.CENTER);
    private JLabel label2 = new JLabel("Student Education",JLabel.CENTER);
    
   public FlowlayoutExperiment()
   {
        JPanel group1 = new JPanel();
        group1.setLayout(new BoxLayout(group1, BoxLayout.PAGE_AXIS));
        this.add(group1);
        setLayout(new GridLayout(1,1));
        //group1.setSize( 1024, 768 );
        group1.add(label1);
        group1.setBorder(BorderFactory.createLineBorder(Color.RED));
        group1.add(firstButton);
        firstButton.setLayout(null);
        firstButton.setBounds(300,300,100,100);
        firstButton.setAlignmentX(CENTER_ALIGNMENT);
        label1.setAlignmentX(CENTER_ALIGNMENT);

        JPanel group2 = new JPanel();
        group2.setLayout(new BoxLayout(group2, BoxLayout.PAGE_AXIS));
        this.add(group2);
        setLayout(new GridLayout(1,3));
        //group2.setSize( 1024, 768 );
        group2.add(label2);
        group2.setBorder(BorderFactory.createLineBorder(Color.blue)); 
        group2.add(secondButton);
        group2.add(thirdButton);
        secondButton.setLayout(null);
        thirdButton.setLayout(null);
        secondButton.setBounds(200,300,100,100);
        thirdButton.setBounds(400,300,100,100);
        secondButton.setAlignmentX(CENTER_ALIGNMENT);
        thirdButton.setAlignmentX(CENTER_ALIGNMENT);
        label2.setAlignmentX(CENTER_ALIGNMENT);
        
       ActionListener actionListener1 = new ActionListener() 
       {
           public void actionPerformed(ActionEvent e)
           {
        	   answer = "1";
               dispose();
           }
       };
       ActionListener actionListener2 = new ActionListener() 
       {
           public void actionPerformed(ActionEvent e)
           {
        	   answer = "2";
               dispose();
            }
       };
       ActionListener actionListener3 = new ActionListener() 
       {
           public void actionPerformed(ActionEvent e)
           {
        	   answer = "3";
               dispose();
            }
       };
       firstButton.addActionListener(actionListener1);
       secondButton.addActionListener(actionListener2);
       thirdButton.addActionListener(actionListener3);
   }
   //FlowlayoutExperiment frame = new FlowlayoutExperiment();
   
   public static String main() 
   {
       FlowlayoutExperiment frame = new FlowlayoutExperiment(); 
       frame.setTitle("Summarize Text Audio");
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