package telemedicineApp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.ClientDoctor;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class AppDisplay extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	private ClientDoctor client;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppDisplay frame = new AppDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AppDisplay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton logIn = new JButton("Log in");
		logIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//doctor = databaseManager.checkDoctor(username.toString(), password.toString());
				JFrame DoctorDisplay = new DoctorDisplay(AppDisplay.this);
				DoctorDisplay.setVisible(true);
			}
		});
		logIn.setBounds(183, 187, 230, 23);
		contentPane.add(logIn);
		
		username = new JTextField();
		username.setBounds(173, 125, 269, 20);
		contentPane.add(username);
		username.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Username :");
		lblNewLabel.setBounds(93, 128, 77, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(93, 159, 77, 14);
		contentPane.add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(173, 156, 269, 20);
		contentPane.add(password);
		
		JLabel imgLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/signal.PNG")).getImage();
		imgLabel.setIcon(new ImageIcon(img));
		imgLabel.setBounds(220, 11, 205, 117);
		contentPane.add(imgLabel);
		
		JButton register = new JButton("Register");
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame doctorRegister = new DoctorRegister(AppDisplay.this, client);
				doctorRegister.setVisible(true);
			}
		});
		register.setBounds(183, 221, 230, 23);
		contentPane.add(register);
		
		JButton manual = new JButton("");
		Image manualImg = new ImageIcon(this.getClass().getResource("/manual.png")).getImage();
		manual.setIcon(new ImageIcon(manualImg));
		manual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		manual.setBounds(267, 269, 73, 54);
		contentPane.add(manual);
	}
}
