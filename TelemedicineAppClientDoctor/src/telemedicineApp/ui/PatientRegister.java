package telemedicineApp.ui;

import java.awt.BorderLayout;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import telemedicineApp.pojos.Patient;
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

public class PatientRegister extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JTextField name;
	private JTextField email;
	private JTextField phoneNumber;
	private JComboBox sex;
	private LocalDate dob;
	//private int age;
	private JCalendar calendar;
	private JPasswordField passwordField;
	
	/**
	 * Create the frame.
	 */
	public PatientRegister(JFrame appDisplay) {
		appDisplay.setVisible(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 636, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		id = new JTextField();
		id.setBackground(new Color(240,240,240));
		id.setBounds(new Rectangle(112, 53, 218, 20));
		id.setBorder(null);
		contentPane.add(id);
		id.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ID :");
		lblNewLabel.setBounds(21, 62, 28, 14);
		contentPane.add(lblNewLabel);
		
		name = new JTextField();
		name.setBackground(new Color(240,240,240));
		name.setColumns(10);
		name.setBounds(new Rectangle(112, 84, 218, 20));
		name.setBorder(null);
		contentPane.add(name);
		
		email = new JTextField();
		email.setBackground(new Color(240,240,240));
		email.setColumns(10);
		email.setBounds(new Rectangle(112, 116, 218, 20));
		email.setBorder(null);
		contentPane.add(email);
		
		phoneNumber = new JTextField();
		phoneNumber.setBackground(new Color(240,240,240));
		phoneNumber.setColumns(10);
		phoneNumber.setBounds(new Rectangle(112, 147, 218, 20));
		phoneNumber.setBorder(null);
		contentPane.add(phoneNumber);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(240, 240, 240));
		passwordField.setBounds(new Rectangle(112, 216, 218, 20));
		passwordField.setBorder(null);
		contentPane.add(passwordField);
		
		JLabel lblFullName = new JLabel("Full name : ");
		lblFullName.setBounds(21, 93, 71, 14);
		contentPane.add(lblFullName);
		
		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setBounds(21, 124, 57, 14);
		contentPane.add(lblEmail);
		
		JLabel lblPhoneNumber = new JLabel("Phone number:");
		lblPhoneNumber.setBounds(21, 155, 95, 14);
		contentPane.add(lblPhoneNumber);
		
		sex = new JComboBox();
		sex.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
		sex.setBounds(112, 183, 95, 22);
		contentPane.add(sex);
		
		JLabel lblSex = new JLabel("Sex :");
		lblSex.setBounds(21, 187, 95, 14);
		contentPane.add(lblSex);
		
		JLabel lblAge = new JLabel("Date of birth :");
		lblAge.setBounds(455, 59, 95, 14);
		contentPane.add(lblAge);
		
		calendar = new JCalendar();
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getOldValue() != null) {
					int year = calendar.getYearChooser().getYear();
					int month = calendar.getMonthChooser().getMonth();
					int day = calendar.getDayChooser().getDay();
					dob = LocalDate.of(year, month, day);
				}
			}
		});
		calendar.setBounds(406, 87, 198, 153);
		contentPane.add(calendar);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(112, 73, 218, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(112, 104, 218, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(112, 136, 218, 2);
		contentPane.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		separator_3.setBounds(112, 167, 218, 2);
		contentPane.add(separator_3);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(21, 222, 65, 16);
		contentPane.add(lblPassword);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(Color.BLACK);
		separator_4.setBounds(112, 236, 218, 2);
		contentPane.add(separator_4);
		
		JButton back = new JButton("");
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				appDisplay.setVisible(true);
				PatientRegister.this.setVisible(false);
			}
		});
		back.setBounds(21, 318, 77, 18);
		Image backImg = new ImageIcon(this.getClass().getResource("/back.png")).getImage();
		back.setIcon(new ImageIcon(backImg));
		contentPane.add(back);
		
		JButton register = new JButton("");
		register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (!validateEmail(email.getText())) {
					JOptionPane.showMessageDialog(PatientRegister.this, "Invalid email", "Message",
							JOptionPane.ERROR_MESSAGE);
				} else if(dob == null){
					JOptionPane.showMessageDialog(PatientRegister.this, "Select your date of birth", "Message",
							JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						Patient p = new Patient();
						p.setId(id.getText());
						p.setName(name.getText());
						p.setEmail(email.getText());
						p.setPhoneNumber(Integer.parseInt(phoneNumber.getText()));
						p.setDob(dob);
						p.setAge(getAge(dob));
						if(sex.getSelectedItem().toString().equalsIgnoreCase("male")) {
							p.setSex(Sex.MALE);
						} else {
							p.setSex(Sex.FEMALE);
						}
						System.out.println(p);
						
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(PatientRegister.this, "Invalid id or pone number", "Message",
								JOptionPane.ERROR_MESSAGE);
					}
					
					
					
				}
			}
		});
		register.setBounds(520, 316, 90, 20);
		Image registerImg = new ImageIcon(this.getClass().getResource("/register.png")).getImage();
		register.setIcon(new ImageIcon(registerImg));
		contentPane.add(register);
	}
	
	private boolean validateEmail(String email) {
		//Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+"); // String of available characters and pattern
		Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@([a-z]+(\\.[a-z]+)+)"); // String of available characters and pattern
		Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}
	
	private int getAge(LocalDate birth) {
		int age = 0;
		if(LocalDate.now().getMonthValue() > birth.getMonthValue()) {
			age = LocalDate.now().getYear() - birth.getYear();
		} else if (LocalDate.now().getDayOfMonth() == birth.getMonthValue()) {
			if(LocalDate.now().getDayOfMonth() >= birth.getDayOfMonth()) {
				age = LocalDate.now().getYear() - birth.getYear();
			}
		} else {
			age = LocalDate.now().getYear() - birth.getYear() - 1;
		}
		return age;
	}
}
