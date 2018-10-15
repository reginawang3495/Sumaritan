package CamHacks;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextToFrame extends JFrame{
    private JLabel label1;
    static boolean ready = false;
	public TextToFrame(String text){
		JPanel group1 = new JPanel();
        group1.setLayout(new BoxLayout(group1, BoxLayout.PAGE_AXIS));
        this.add(group1);
        setLayout(new GridLayout(1,1));
        //group1.setSize( 1024, 768 );
        label1 = new JLabel("<html>"+ text +"</html>",JLabel.CENTER);
        group1.add(label1);
        this.setVisible(true);

        this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent event) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				cleanUp();
			}

			@Override
			public void keyTyped(KeyEvent e) {   
			}
		});
		TextToSpeech.main(text);

	}

	public static void main(String text){
		TextToFrame frame =   new TextToFrame(text);
		 frame.setTitle("Text");
	        frame.setSize(600, 450);
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
			TextToSpeech.main(text);

	}
	public void cleanUp(){
		TextToSpeech.stopTalking();
		ready = true;

		this.dispose();
	}
}
