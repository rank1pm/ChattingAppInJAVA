package com.srtmunChatting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener {
	
	JPanel navBar,footBar;
	static JTextArea textArea;
	JTextField sendTextField;
	JButton closeButton,sendTextButton;
	static Socket socket;
	static DataInputStream din;
	static DataOutputStream dout;
	
	
	public Client() {
		navBar=new JPanel();
		navBar.setLayout(null);
		navBar.setBackground(new Color(255, 204, 153));
		navBar.setBounds(0,0,450,80);
		add(navBar);
		
		footBar=new JPanel();
		footBar.setLayout(null);
		footBar.setBackground(new Color(255, 204, 153));
		footBar.setBounds(0,640, 450, 80);
		add(footBar);
		
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
		
		textArea=new JTextArea();
		textArea.setBounds(5,85, 440, 550);
		textArea.setFont(new Font("TIMES NEW ROMAN",Font.PLAIN,16));
		textArea.setBackground(new Color(248, 246, 247));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		add(textArea);
		
		
		setLayout(null);
		getContentPane().setBackground(new Color(255, 223, 175));
		setSize(450,700);
		setLocation(900,90);
		setUndecorated(true);
		setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		new Client().setVisible(true);
		String inComingMessege="";
		try {
			
			socket=new Socket("127.0.0.1", 0505);
			din=new DataInputStream(socket.getInputStream());
			dout=new DataOutputStream(socket.getOutputStream());
			
			while(true) {
			inComingMessege=din.readUTF();
			textArea.setText(textArea.getText()+"\n"+inComingMessege);
			
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		String sentText=sendTextField.getText();
		textArea.setText(textArea.getText()+"\n\t\t\t"+sentText);
		sendTextField.setText("");
		dout.writeUTF(sentText);
			
		} catch (Exception e2) {
			// TODO: handle exception
		}
		
	}
}
