import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI1 extends JPanel implements ActionListener{
	JFrame frame=new JFrame();
	JButton button;
	MyDrawPanel panel=new MyDrawPanel();
	JCheckBox checkBox=new JCheckBox();
	JApplet applet=new JApplet();
	JLabel label=new JLabel("aaa");
	JTextArea textField=new JTextArea(10,20);
	public static void main(String[] args) {
		GUI1 gui1=new GUI1();
		gui1.go();
		
		
	}
	
	public void go() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(300, 300);
		button=new JButton("click me");
		button.setFont(new Font("New Font", Font.BOLD, 28));
		button.addActionListener(this);
		button.addActionListener(panel);
		
		textField.setLineWrap(false);
		JScrollPane scrollPane=new JScrollPane(textField);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		panel.setSize(250, 160);
		panel.setBackground(Color.black);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(BorderLayout.SOUTH,button);
		frame.getContentPane().add(BorderLayout.NORTH,scrollPane);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		button.setText("clicked!");
	}
	
	public class MyDrawPanel extends JPanel implements ActionListener{
		int i=0;
		/**
		 * Create the panel.
		 */
		boolean canRepaint=false;
		public void actionPerformed(ActionEvent event) {
			canRepaint=true;
			frame.repaint();
		}
		public void paintComponent(Graphics g) {
			if(canRepaint){
				g.setColor(Color.red);
				g.fillRect(20+i, 50+i, 100, 100);
				i+=5;
			}
			canRepaint=false;
		}
	}
	
	public class MyTextField extends JTextArea implements ActionListener {
		public MyTextField(int i, int j) {
			super(i, j);
		}

		public void actionPerformed(ActionEvent event) {
			System.out.println(textField.getText());
			
		}
	}
}
