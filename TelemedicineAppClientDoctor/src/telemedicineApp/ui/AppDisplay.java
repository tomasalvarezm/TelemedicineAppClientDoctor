package telemedicineApp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.ClientDoctor;
import telemedicineApp.pojos.Doctor;
import telemedicineApp.pojos.Patient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.SwingConstants;

public class AppDisplay extends JFrame {

	private JPanel contentPane;
	private JTextField userID;
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
		setTitle("Doctor");

		client = new ClientDoctor("localhost", 9000);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton logIn = new JButton("Log in");
		logIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					client.sendFunction("login");
					Doctor doctor = client.checkDoctor(userID.getText());
					if (doctor != null) {
						JFrame doctorMenu = new DoctorMenu(AppDisplay.this, client, doctor);
						doctorMenu.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(AppDisplay.this, "You need to register first!", "Message",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException | IOException e1) {
					JOptionPane.showMessageDialog(AppDisplay.this, "Problems connecting with server", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		logIn.setBounds(183, 187, 230, 23);
		contentPane.add(logIn);

		userID = new JTextField();
		userID.setBackground(new Color(240, 240, 240));
		userID.setBorder(null);
		userID.setBounds(173, 125, 269, 20);
		contentPane.add(userID);
		userID.setColumns(10);

		JLabel lblNewLabel = new JLabel("ID :");
		lblNewLabel.setBounds(128, 128, 33, 14);
		contentPane.add(lblNewLabel);

		JLabel imgLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/signal.PNG")).getImage();
		imgLabel.setIcon(new ImageIcon(img));
		imgLabel.setBounds(311, 11, 205, 117);
		contentPane.add(imgLabel);

		JButton register = new JButton("Register");
		register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					client.sendFunction("register");
					JFrame doctorRegister = new DoctorRegister(AppDisplay.this, client);
					doctorRegister.setVisible(true);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(AppDisplay.this, "Problems connecting with server", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
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
		
		JSeparator separator = new JSeparator();
		separator.setBounds(173, 145, 269, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("EMG MONITORING");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Bookman Old Style", Font.BOLD, 17));
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(111, 43, 192, 59);
		contentPane.add(lblNewLabel_1);

		// CLOSING CONNECTION WHEN CLOSING FRAME
		WindowListener exitListener = (WindowListener) new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.closeConnection();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(AppDisplay.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		this.addWindowListener(exitListener);
	}
}
