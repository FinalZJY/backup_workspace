package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import socket.Client;
import ui.MainFrame.VersionMenu;

public class ChooseFileFrame extends JFrame {

	private JPanel contentPane= new JPanel();
	Client client;
	JLabel chooseFileLable;
	JTextField fieldname;
	JButton chooseButton;
	JTextArea codeArea;//BFFrame's codeArea
	VersionMenu versionMenu;//BFFrame's versionMenu
	String username;

	public ChooseFileFrame(String name,String username,JTextArea codeArea,VersionMenu versionMenu,Client client) {
		super(name);
		this.username=username;
		this.client=client;
		this.codeArea=codeArea;
		this.versionMenu=versionMenu;
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		chooseFileLable=new JLabel("Filename");
		chooseFileLable.setFont(new Font("Code", Font.BOLD, 28));
		fieldname=new JTextField(10);
		fieldname.setFont(new Font("codeArea", Font.BOLD, 25));
		fieldname.setForeground(new Color(50, 255, 0));
		chooseButton=new JButton("OK");
		chooseButton.addActionListener(new LoadFile());
		contentPane.add(chooseFileLable, BorderLayout.NORTH);
		contentPane.add(fieldname, BorderLayout.CENTER);
		contentPane.add(chooseButton, BorderLayout.SOUTH);
		
		setLayout(new BorderLayout());
		setBackground(Color.lightGray);
		setSize(500, 200);
		setLocation(400, 200);
		setContentPane(contentPane);
		setVisible(true);
	}

	class LoadFile implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String name=fieldname.getText();
				ArrayList<String> codes=client.readCode(username, name);
				codeArea.setText(codes.get(0));
				if(codes!=null){
					MainFrame.fileName=name;
				}
				versionMenu.init(codes);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			dispose();
		}

	}

}
