import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyDrawPanel12 extends JPanel implements ActionListener{

	/**
	 * Create the panel.
	 */
	public MyDrawPanel12() {

	}
	public void actionPerformed(ActionEvent event) {
		
	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(20, 50, 100, 100);
		
		Image image=new ImageIcon("abc.jpg").getImage();
		g.drawImage(image, 3, 4, this);
		
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		Color color=new Color(255, 0, 0);
		g.setColor(color);
		g.fillOval(70, 70, 100, 100);
	}
}
