package com.srtmunChatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Server implements ActionListener {
	
	
	static JFrame serverFrame=new JFrame();
	JPanel navBar,footBar;
	static JPanel textArea;
	JTextField sendTextField;
	JButton closeButton,sendTextButton;
	static ServerSocket serverSocket;
	static Socket socket;
	static DataInputStream din;
	static DataOutputStream dout;
	static Box verticalBox=Box.createVerticalBox();
	
	
	public Server() {
		serverFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		navBar=new JPanel();
		navBar.setLayout(null);
		navBar.setBackground(new Color(255, 204, 153));
		navBar.setBounds(0,0,450,80);
		serverFrame.add(navBar);
		
		footBar=new JPanel();
		footBar.setLayout(null);
		footBar.setBackground(new Color(255, 204, 153));
		footBar.setBounds(0,640, 450, 80);
		serverFrame.add(footBar);
		
		ImageIcon logoImageIcon=new ImageIcon(ClassLoader.getSystemResource("com/srtmunChatting/icons/logo.png"));
		Image logoImage=logoImageIcon.getImage().getScaledInstance(40,60,Image.SCALE_DEFAULT);
		ImageIcon finalLogoImage=new ImageIcon(logoImage);
		JLabel logoImageLabel=new JLabel(finalLogoImage);
		logoImageLabel.setBounds(5,15,60,60);
		navBar.add(logoImageLabel);
		
		JLabel chattingProviderLabel=new JLabel("SRTMUN__CHATTING");
		chattingProviderLabel.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 26));
		chattingProviderLabel.setForeground(new Color(183, 12, 12));
		chattingProviderLabel.setBounds(100, 10,300,60);
		navBar.add(chattingProviderLabel);
		
		closeButton=new JButton();
		closeButton.setText("X");
		closeButton.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 15));
		closeButton.setBounds(410,3, 50, 20);
		closeButton.setForeground(new Color(183, 12, 12));
		closeButton.setBackground(new Color(255, 204, 153));
		closeButton.setBorder(null);
		navBar.add(closeButton);
		
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
				
			}
		});
		
		sendTextField=new JTextField();
		sendTextField.setBounds(10,10,340,40);
		sendTextField.setFont(new Font("SAN_SERIF",Font.PLAIN|Font.ITALIC,18));
		sendTextField.setBorder(null);
		footBar.add(sendTextField);
		
		sendTextButton=new JButton("SEND");
		sendTextButton.setBounds(355,15,90,30);
		sendTextButton.setFont(new Font("SAN_SERIF",Font.BOLD,16));
		sendTextButton.setForeground(new Color(183, 12, 12));
		sendTextButton.addActionListener(this);
		footBar.add(sendTextButton);
		
		textArea=new JPanel();
		textArea.setBounds(5,85, 440, 550);
		textArea.setFont(new Font("TIMES NEW ROMAN",Font.PLAIN,16));
		//textArea.setBackground(new Color(248, 246, 247));
		serverFrame.add(textArea);
		
		serverFrame.setLayout(null);
		serverFrame.getContentPane().setBackground(new Color(255, 223, 175));
		serverFrame.setSize(450,700);
		serverFrame.setLocation(200,90);
		serverFrame.setUndecorated(true);
		serverFrame.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		new Server().serverFrame.setVisible(true);
		String inComingMessege="";
		try {
			serverSocket=new ServerSocket(0505);
			socket=serverSocket.accept();
			din=new DataInputStream(socket.getInputStream());
			dout=new DataOutputStream(socket.getOutputStream());
			while(true) {
				inComingMessege=din.readUTF();
				JPanel incomingBoxPanel=formatLabel(inComingMessege);
				
				JPanel leftPanel=new JPanel(new BorderLayout());
				leftPanel.add(incomingBoxPanel,BorderLayout.LINE_START);
				verticalBox.add(leftPanel);
				serverFrame.validate();
			}
			//serverSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		String sentText=sendTextField.getText();
		JPanel sentBoxPanel=formatLabel(sentText);
		textArea.setLayout(new BorderLayout());
		
		JPanel rightPanel=new JPanel(new BorderLayout());
		rightPanel.add(sentBoxPanel,BorderLayout.LINE_END);
		verticalBox.add(rightPanel);
		verticalBox.add(Box.createVerticalStrut(10));
		textArea.add(verticalBox,BorderLayout.PAGE_START);
		serverFrame.validate();
		
		sendTextField.setText("");
		dout.writeUTF(sentText);
			
		} catch (Exception e2) {
			// TODO: handle exception
		}
		
	}



	private static JPanel formatLabel(String sentText) {
		JPanel newBoxPanel=new JPanel();
		newBoxPanel.setLayout(new BoxLayout(newBoxPanel,BoxLayout.Y_AXIS));
		
		JLabel textLabel=new JLabel("<html><p style = \"width : 150px\">"+sentText+"</p></html>");
		textLabel.setFont(new Font("SKati",Font.PLAIN,16));
		textLabel.setBackground(new Color(255, 204, 153));
		textLabel.setOpaque(true);
		textLabel.setBorder(new EmptyBorder(15,15,15,40));
		
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat formatter=new SimpleDateFormat("HH:mm");
		
		JLabel dateLabel=new JLabel(formatter.format(calendar.getTime()));
		
		newBoxPanel.add(textLabel);
		newBoxPanel.add(dateLabel);
		
		return newBoxPanel;
	}
}
