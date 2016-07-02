package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import socket.Client;
import ui.MainFrame.VersionMenu;

public class ChooseFileFrame extends JFrame {

	private JPanel contentPane= new JPanel();
	Client client;
	JLabel chooseFileLable;
	JLabel fileListLable;
	JTextField fieldname;
	JButton chooseButton;
	JList<String> list;
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
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		chooseFileLable=new JLabel("Filename");
		chooseFileLable.setFont(new Font("not determined", Font.BOLD, 28));
		fileListLable=new JLabel("Filelist");
		fileListLable.setFont(new Font("not determined", Font.BOLD, 28));
		fieldname=new JTextField(10);
		fieldname.setFont(new Font("not determined", Font.BOLD, 25));
		fieldname.setForeground(new Color(50, 255, 0));
		fieldname.setMaximumSize(new Dimension(800, 600));
		chooseButton=new JButton("OK");
		chooseButton.addActionListener(new LoadFile());
		setList();
		JScrollPane listScrollPane=new JScrollPane(list);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(fileListLable);
		contentPane.add(listScrollPane);
		contentPane.add(chooseFileLable);
		contentPane.add(fieldname);
		contentPane.add(chooseButton);
		
		setBackground(Color.lightGray);
		setSize(500, 400);
		setLocation(400, 200);
		setContentPane(contentPane);
		setVisible(true);
	}
	
	public void setList() {
		list=new JList<String>(client.readFileList(username));
		list.setVisibleRowCount(4);
		list.setFont(new Font("not determined", Font.BOLD, 35));
		
		//list.setCellRenderer(new FontCellRenderer());
		list.addListSelectionListener(new ChooseFile());
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
	
	class ChooseFile implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			try {
				if(e.getValueIsAdjusting()){
					String name=((JList)e.getSource()).getSelectedValue()+"";
					ArrayList<String> codes=client.readCode(username, name);
					codeArea.setText(codes.get(0));
					if(codes!=null){
						MainFrame.fileName=name;
					}
					versionMenu.init(codes);
				}
			} catch (Exception e1) {
					e1.printStackTrace();
			}
			dispose();
		}

	}

	class FontCellRenderer extends JPanel implements ListCellRenderer{
		private String text;
	    private Color background;
	    private Color foreground;
	    public Component getListCellRendererComponent(JList list, Object value, int index, 
	    		boolean isSelected, boolean cellHasFocus){
	       text = (String)value;
	       background = isSelected ? list.getSelectionBackground() : list.getBackground();
	       foreground = isSelected ? list.getSelectionForeground() : list.getForeground();
			return this;
	    }
	    public void paintComponent(Graphics g){
	       g.setColor(background);
	       g.fillRect(0, 0, getWidth(), getHeight());  //设置背景色
	       g.setColor(foreground);
	       g.drawString(text, 5, 15);   //在制定位置绘制文本
	    }
	    public Dimension getPreferredSize(){
	    	return new Dimension(80, 60);   //Cell的尺寸
	    }
	    
	 }
}
