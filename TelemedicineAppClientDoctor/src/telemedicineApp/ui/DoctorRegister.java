package telemedicineApp.ui;

import java.awt.Image;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import connection.ClientDoctor;
import telemedicineApp.pojos.Doctor;

import telemedicineApp.pojos.Sex;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DoctorRegister extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JTextField name;
	private JComboBox sex;

	/**
	 * Create the frame.
	 */
	public DoctorRegister(JFrame appDisplay, ClientDoctor client) {
		setTitle("Doctor");
		appDisplay.setVisible(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 453, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		id = new JTextField();
		id.setBackground(new Color(240, 240, 240));
		id.setBounds(new Rectangle(112, 53, 218, 20));
		id.setBorder(null);
		contentPane.add(id);
		id.setColumns(10);

		JLabel lblNewLabel = new JLabel("ID :");
		lblNewLabel.setBounds(21, 62, 28, 14);
		contentPane.add(lblNewLabel);

		name = new JTextField();
		name.setBackground(new Color(240, 240, 240));
		name.setColumns(10);
		name.setBounds(new Rectangle(112, 96, 218, 20));
		name.setBorder(null);
		contentPane.add(name);

		JLabel lblFullName = new JLabel("Full name : ");
		lblFullName.setBounds(21, 105, 71, 14);
		contentPane.add(lblFullName);

		sex = new JComboBox();
		sex.setModel(new DefaultComboBoxModel(new String[] { "Male", "Female" }));
		sex.setBounds(112, 153, 95, 22);
		contentPane.add(sex);

		JLabel lblSex = new JLabel("Sex :");
		lblSex.setBounds(21, 157, 95, 14);
		contentPane.add(lblSex);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(112, 73, 218, 2);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(112, 116, 218, 2);
		contentPane.add(separator_1);

		JButton back = new JButton("");
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					client.sendFunction("back");
					appDisplay.setVisible(true);
					DoctorRegister.this.setVisible(false);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(DoctorRegister.this, "Problems connecting with server", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		back.setBounds(21, 257, 77, 18);
		Image backImg = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		back.setIcon(new ImageIcon(backImg));
		contentPane.add(back);

		JButton register = new JButton("");
		register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (id == null) {
					JOptionPane.showMessageDialog(DoctorRegister.this, "Introduce your ID", "Message",
							JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						Doctor doctor = new Doctor();
						doctor.setId(id.getText());
						doctor.setName(name.getText());
						if (sex.getSelectedItem().toString().equalsIgnoreCase("male")) {
							doctor.setSex(Sex.MALE);
						} else {
							doctor.setSex(Sex.FEMALE);
						}

						client.sendFunction("register");

						// check that the client is registered successfully
						if (client.registerDoctor(doctor)) {
							JOptionPane.showMessageDialog(DoctorRegister.this, "Successfully registered", "Message",
									JOptionPane.PLAIN_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(DoctorRegister.this, "Existing account. Go to log in", "Message",
									JOptionPane.PLAIN_MESSAGE);
						}
						appDisplay.setVisible(true);
						DoctorRegister.this.setVisible(false);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(DoctorRegister.this, "Problems connecting with server", "Message",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});
		register.setBounds(317, 257, 90, 20);
		Image registerImg = new ImageIcon(this.getClass().getResource("/register.png")).getImage();
		register.setIcon(new ImageIcon(registerImg));
		contentPane.add(register);

		// CLOSING CONNECTION WHEN CLOSING FRAME
		WindowListener exitListener = (WindowListener) new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.sendFunction("back");
					appDisplay.setVisible(true);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(DoctorRegister.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		this.addWindowListener(exitListener);
	}

}
