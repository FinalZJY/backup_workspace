package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import runner.Function;
import runner.NoLastVersionException;
import runner.NoLaterVersionException;
import socket.Client;
import socket.RemoteHelper;


public class MainFrame extends JFrame {
	public static String fileName="";
	public static final String admin="admin1";
	private BFFrame frame;
	private Client client;

	public MainFrame(Client client) {
		// 创建窗体
		this.client=client;
		frame = new BFFrame("BF Client");
		frame.userPanel.automaticLogin();
	}
	
	public BFMenu setMeun(BFMenuBar menuBar,String name) {
		BFMenu newMenu = new BFMenu(name);
		menuBar.add(newMenu);
		return newMenu;
	}
	
	public BFMenuItem setItem(BFMenu menu,String name) {
		BFMenuItem item = new BFMenuItem(name);
		menu.add(item);
		return item;
	}
	
	public BFFrame getBFFrame() {
		return frame;
	}
	
	public JTextArea getCodeArea() {
		return frame.codeArea;
	}
	
	public void setOutputArea(String output) {
		frame.inputAndOutputPanel.IOArea.setText(output);
	}
	
	public void appendOutputArea(String output) {
		frame.inputAndOutputPanel.IOArea.append("Brainfuck> "+output);
		frame.inputAndOutputPanel.IOArea.requestFocus();
	}
	
	public class BFFrame extends JFrame{
		private BFMenuBar menuBar = new BFMenuBar();
		private JLabel codeLable;
		private JTextArea codeArea;
		private JScrollPane scrollPane;
		private IOPanel inputAndOutputPanel=new IOPanel();
		private UserPanel userPanel=new UserPanel();
		
		public BFFrame(String name) {
			super(name);
			this.setLayout(new BorderLayout());
						
			this.setJMenuBar(menuBar);
		
			codeArea = new JTextArea();
			codeArea.setMargin(new Insets(10, 10, 10, 10));
			codeArea.setBackground(Color.white);
			codeArea.setLineWrap(true);
			codeArea.setFont(new Font("", Font.BOLD, 30));
			codeArea.setForeground(new Color(50, 255, 0));
			codeArea.setBorder(BorderFactory.createMatteBorder(4, 4, 1, 4, Color.white));
			codeArea.addKeyListener(new CodeAreaListener());
			scrollPane=new JScrollPane(codeArea);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			

			// 显示结果
			codeLable=new JLabel("Code");
			codeLable.setFont(new Font("", Font.BOLD, 28));
			codeLable.setForeground(Color.red);
						
			codeLable.setOpaque(false);
			userPanel.setOpaque(false);
			inputAndOutputPanel.setOpaque(false);
			codeArea.setOpaque(false);
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			
			this.setContentPane(new BackgroundPanel());;
			this.getContentPane().add(BorderLayout.SOUTH,inputAndOutputPanel);
			this.getContentPane().add(BorderLayout.EAST,userPanel);
			this.getContentPane().add(BorderLayout.CENTER,scrollPane);
			this.getContentPane().add(BorderLayout.NORTH,codeLable);
			this.addWindowListener(new WindowHandler());

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setBackground(Color.lightGray);
			this.setSize(1300, 800);
			this.setLocation(300, 100);
			this.setVisible(true);
		}

		public IOPanel getIOPanel() {
			return inputAndOutputPanel;
		}
	}
	
	class BackgroundPanel extends JPanel{
		public BackgroundPanel() {
			super();
			setLayout(new BorderLayout());
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			Image background=new ImageIcon("images\\background7.jpg").getImage();
			g.drawImage(background, 0, 0,frame.getWidth(),frame.getHeight(),this);
		}
	}
	
	public class BFMenuBar extends JMenuBar{
		BFMenu File=new BFMenu("File");
		BFMenu Run=new BFMenu("Run");
		VersionMenu Version=new VersionMenu("Version");
		public BFMenuBar(){
			super();
			this.add(File);
			this.add(Run);
			this.add(Version);
			
			File.addBFMenuItem(new BFMenuItem("New"));
			File.addBFMenuItem(new BFMenuItem("Open"));
			File.addBFMenuItem(new BFMenuItem("Save"));
			File.addBFMenuItem(new BFMenuItem("Exit"));
			Run.addBFMenuItem(new BFMenuItem("Run"));
			
			try{
				File.get("New").addActionListener(new MenuItemActionListener());
				File.get("Open").addActionListener(new MenuItemActionListener());
				File.get("Save").addActionListener(new SaveActionListener());
				File.get("Exit").addActionListener(new MenuItemActionListener());
				Run.get("Run").addActionListener(new MenuItemActionListener());
			}catch (NotFoundException nfE){
				nfE.printStackTrace();
			}
		}
	}
	
	public class BFMenu extends JMenu {
		String name;
		ArrayList<BFMenuItem> items=new ArrayList<BFMenuItem>();
		public BFMenu(String name){
			super(name);
			this.name=name;
		}
		public void addBFMenuItem(BFMenuItem menuItem) {
			this.add(menuItem);
			items.add(menuItem);
		}
		public BFMenuItem get(String name) throws NotFoundException{
			for(BFMenuItem item:items){
				if(item.name.equals(name)){
					return item;
				}
			}
			throw new NotFoundException();
			
		}
	}
	
	public class VersionMenu extends BFMenu {
		String name;
		ArrayList<VersionItem> items=new ArrayList<VersionItem>();
		public VersionMenu(String name){
			super(name);
			this.name=name;
		}
		public void addVersionItem(VersionItem menuItem) {
			this.add(menuItem);
			items.add(menuItem);
		}
		public void removeAllVersionItems() {
			removeAll();
			items=new ArrayList<VersionItem>();
		}
		public VersionItem get(String name) throws NotFoundException{
			for(VersionItem item:items){
				if(item.name.equals(name)){
					return item;
				}
			}
			throw new NotFoundException();
			
		}
		
		public void init() {
			removeAllVersionItems();
		}
		
		public void init(ArrayList<String> codes) {
			removeAllVersionItems();
			for(int i=0;i<codes.size();i++){
				VersionItem item=new VersionItem("Version "+(i+1),codes.get(i)); 
				addVersionItem(item);
			}
		}
		
		public class VersionItem extends BFMenuItem{
			String name;
			String code;
			public VersionItem(String name,String code) {
				super(name);
				this.name=name;
				this.code=code;
				addActionListener(new VersionChangeListener());
			}
			public String getName() {
				return name;
			}

			class VersionChangeListener implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.codeArea.setText(code);
				}
			}
		}
	}
	
	public class BFMenuItem extends JMenuItem{
		String name;
		public BFMenuItem(String name) {
			super(name);
			this.name=name;
		}
		public String getName() {
			return name;
		}
	}
	
	public class UserPanel extends JPanel{
		JLabel usernameLable=new JLabel("username");
		JLabel passwordLable=new JLabel("password");
		JLabel nameLable=new JLabel("");
		JLabel blankLable=new JLabel("");
		ArrayList<JLabel> blankLabels=new ArrayList<JLabel>();
		JTextField username=new JTextField(7);
		JPasswordField password=new JPasswordField(7);
		JButton SignIn=new JButton("Sign In ");
		JButton SignUp=new JButton("Sign Up");
		JButton SignOut=new JButton("Sign Out");
		boolean login=false;
		public UserPanel(){
			super();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			SignIn.addActionListener(new SignInListener());
			SignUp.addActionListener(new SignUpListener());
			SignOut.addActionListener(new SignOutListener());
			SignIn.setFont(new Font("", Font.BOLD, 20));
			SignUp.setFont(new Font("", Font.BOLD, 20));
			SignOut.setFont(new Font("", Font.BOLD, 20));
			usernameLable.setFont(new Font("", Font.BOLD, 20));
			passwordLable.setFont(new Font("", Font.BOLD, 20));
			nameLable.setFont(new Font("", Font.BOLD, 30));
			username.setFont(new Font("", Font.BOLD, 30));
			password.setFont(new Font("", Font.BOLD, 30));
			this.add(usernameLable);
			this.add(username);
			this.add(passwordLable);
			this.add(password);
			this.add(SignIn);
			this.add(SignUp);
			addBlankLable(15);
		}
		
		public void automaticLogin() {
			System.out.println("automatic login");
			String[] user=Function.readUser();
			String username=user[0];
			String password=user[1];
			
			if(username!=null && password!=null){
				if(client.login(username, password)){
					System.out.println("Client: login success ");
					this.username.setText(user[0]);
					this.password.setText(user[1]);
					toUserPanel(username);
				}				
			}
		}
		
		public void logout() {
			if(login && client.logout(username.getText())){
				toLogInPanel();
				System.out.println("Client: logout");
			}
		}
		
		public void setAccount(String name) {
			nameLable.setText(name);
			login=true;
		}
		
		 public void toUserPanel(String name) {
			 login=true;
			 remove(usernameLable);
			 remove(passwordLable);
			 remove(username);
			 remove(password);
			 remove(SignIn);
			 remove(SignUp);
			 removeBlankLable();
			 setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			 nameLable.setText(name);
			 blankLable.setText("                                           ");
			 add(blankLable);
			 add(nameLable);
			 add(SignOut);
			 SignOut.setSize(10, 5);
			 repaint();
			 frame.setSize(1400, 800);//待解决：不重设大小会显示不出userPanel
			 frame.setSize(1300, 800);
			 Function.saveUser(username.getText(), password.getText());
		 }
		 
		 public void toLogInPanel() {
			 login=false;
			 remove(nameLable);
			 remove(SignOut);
			 setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			 nameLable.setText("");
			 add(usernameLable);
			 add(username);
			 add(passwordLable);
			 add(password);
			 add(SignIn);
			 add(SignUp);
			 blankLable.setText("　");
			 add(blankLable);
			 addBlankLable(15);;
			 repaint();
		}
		 
		public void  addBlankLable(int num) {
			for(int i=0;i<num;i++){
				JLabel label=new JLabel("　");
				add(label);
				blankLabels.add(label);
			}
		}
		
		public void removeBlankLable() {
			for(JLabel label:blankLabels){
				remove(label);
				label.remove(label);
			}
		}
	}
/*	
	public class IOPanel extends JPanel{
		InputPanel inputPanel=new InputPanel();
		JLabel icon=new JLabel(new ImageIcon("abc.jpg"));
		public IOPanel () {
			super();
			this.setLayout(new BorderLayout());
			inputPanel.setOpaque(false);
			icon.setOpaque(false);
			this.add(BorderLayout.WEST,inputPanel);
		}
	}
*/	
	public class IOPanel extends JPanel{
		JLabel IOLable=new JLabel("Input and Output");
		JLabel signature=new JLabel(new ImageIcon("images\\signature.png"));
		JTextArea IOArea;
		JScrollPane scrollPane=new JScrollPane();
		public IOPanel () {
			super();
			this.setLayout(new BorderLayout());
			IOArea=new JTextArea(5,25);
			IOArea.setMargin(new Insets(10, 10, 10, 10));
			IOArea.setBackground(Color.white);
			IOArea.setLineWrap(false);
			IOArea.setFont(new Font("", Font.BOLD, 32));
			IOArea.setForeground(new Color(174, 0, 255));
			IOArea.setBorder(BorderFactory.createMatteBorder(4, 4, 1, 4, Color.white));
			IOArea.addKeyListener(new IOPanelListener());
			IOArea.setOpaque(false);
			scrollPane=new JScrollPane(IOArea);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);			
			IOLable.setFont(new Font("", Font.BOLD, 28));
			IOLable.setForeground(Color.red);
			
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			
			this.add(BorderLayout.NORTH,IOLable);
			this.add(BorderLayout.WEST,scrollPane);
			//this.add(BorderLayout.EAST,signature);
		}
	}
/*	
	public class OutputPanel extends JPanel{
		JLabel outputLable=new JLabel("Output");
		JTextArea outputArea;
		JScrollPane scrollPane=new JScrollPane();
		public OutputPanel () {
			super();
			this.setLayout(new BorderLayout());
			outputArea=new JTextArea(5,20);
			outputArea.setMargin(new Insets(10, 10, 10, 10));
			outputArea.setBackground(Color.white);
			outputArea.setLineWrap(false);
			outputArea.setFont(new Font("", Font.BOLD, 32));
			outputArea.setBorder(BorderFactory.createMatteBorder(4, 4, 1, 4, Color.white));
			scrollPane=new JScrollPane(outputArea);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			outputLable.setFont(new Font("", Font.BOLD, 28));
			outputLable.setForeground(Color.red);
			
			outputArea.setOpaque(false);
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			
			this.add(BorderLayout.NORTH,outputLable);
			this.add(BorderLayout.SOUTH,scrollPane);
		}
	}
*/
	class MenuItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
				if(frame.userPanel.login){
					String username=frame.userPanel.nameLable.getText();
					ChooseFileFrame chooseFileFrame=new ChooseFileFrame("Choose File",username,frame.codeArea,frame.menuBar.Version,client);
				}
				else {
					JOptionPane.showMessageDialog(null, "You have to login first.");
				}
			} 
			else if (cmd.equals("New")) {
				fileName="";
				frame.codeArea.setText("");
				frame.menuBar.Version.init();
				frame.codeArea.requestFocus();
			}
			else if (cmd.equals("Exit")) {
				if(frame.userPanel.login==true){
					
				}
				System.exit(-1);
			}
			else if (cmd.equals("Run")) {
				System.out.println("Client: code: "+frame.codeArea.getText());
				client.runCode(frame.codeArea.getText());
			}
		}
	}

	class SaveActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(frame.userPanel.login){
				String username=frame.userPanel.nameLable.getText();
				if(!fileName.equals("")){
					client.saveCode(username,fileName,frame.codeArea.getText());
				}
				else {
					SaveFrame savaFrame=new SaveFrame("Sava",frame.codeArea.getText(),client);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "You have to login first.");
			}		
		}
	}
	
	class SignInListener implements ActionListener {
		//登陆按钮的监听
		public void actionPerformed(ActionEvent e) {
			String username=frame.userPanel.username.getText();
			String password="";
			for(char c:frame.userPanel.password.getPassword()){
				password=password+c;
			}
			if(client.login(username, password)){
				System.out.println("Client: login success ");
				frame.userPanel.toUserPanel(username);
			}
		}
	}
	
	class SignUpListener implements ActionListener {
		//登陆按钮的监听
		public void actionPerformed(ActionEvent e) {
			String username=frame.userPanel.username.getText();
			String password="";
			for(char c:frame.userPanel.password.getPassword()){
				password=password+c;
			}
			if(client.signup(username, password)){
				client.login(username, password);
				System.out.println("Client: signup success ");
				frame.userPanel.toUserPanel(username);
			}
		}
	}
	
	class SignOutListener implements ActionListener {
		//登出按钮的监听
		public void actionPerformed(ActionEvent e) {
			String username=frame.userPanel.nameLable.getText();
			if(client.logout(username)){
				System.out.println("Client: logout success ");
				frame.userPanel.toLogInPanel();
			}
		}
	}
	
	class IOPanelListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar()=='\n'){
				String[] inputs=frame.inputAndOutputPanel.IOArea.getText().split("\n");
				String newInput=inputs[inputs.length-1];
				client.sendInput(newInput);
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class CodeAreaListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z){  
		        System.out.println("Ctrl + Z");
		        try {
		        	frame.codeArea.setText(Function.undo());
				} catch (NoLastVersionException e1) {
					e1.printStackTrace();
				}
		    } 
			
			if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y){  
		        System.out.println("Ctrl + y");
		        try {
		        	frame.codeArea.setText(Function.redo());
				} catch (NoLaterVersionException e2) {
					e2.printStackTrace();
				}
		    }
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	class WindowHandler implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			frame.userPanel.logout();
			
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
