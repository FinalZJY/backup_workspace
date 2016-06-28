package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import socket.Client;
import socket.RemoteHelper;

public class SaveFrame extends JFrame {

	private JPanel contentPane= new JPanel();
	Client client;
	String code;
	JLabel saveNameLable;
	JTextField fieldname;
	JButton saveButton;

	public SaveFrame(String name,String code,Client client) {
		super(name);
		this.code = code;
		this.client=client;
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		saveNameLable=new JLabel("Filename");
		saveNameLable.setFont(new Font("Code", Font.BOLD, 28));
		fieldname=new JTextField(10);
		fieldname.setFont(new Font("codeArea", Font.BOLD, 25));
		fieldname.setForeground(new Color(50, 255, 0));
		saveButton=new JButton("save");
		saveButton.addActionListener(new SaveFile());
		contentPane.add(saveNameLable, BorderLayout.NORTH);
		contentPane.add(fieldname, BorderLayout.CENTER);
		contentPane.add(saveButton, BorderLayout.SOUTH);
		
		setLayout(new BorderLayout());
		setBackground(Color.lightGray);
		setSize(500, 200);
		setLocation(400, 200);
		setContentPane(contentPane);
		setVisible(true);
	}

	class SaveFile implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				client.saveCode(MainFrame.admin,fieldname.getText(),code);
				MainFrame.fileName=fieldname.getText();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			dispose();
		}

	}
}
