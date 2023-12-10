package telemedicineApp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JDateChooser;
import com.toedter.components.JSpinField;

import connection.ClientDoctor;
import telemedicineApp.pojos.BitalinoSignal;
import telemedicineApp.pojos.Doctor;
import telemedicineApp.pojos.MedicalHistory;
import telemedicineApp.pojos.Patient;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JCalendar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Font;

public class DoctorMenu extends JFrame {

	private JPanel contentPane;
	private JTable table;
	

	/**
	 * Create the frame.
	 */
	public DoctorMenu(JFrame appDisplay, ClientDoctor client, Doctor doctor) {
		setTitle("Doctor");
		appDisplay.setVisible(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 575, 342);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 90, 464, 114);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		DefaultTableModel model = new DefaultTableModel() {
			public boolean isCellEditable(int fil, int col) {
				return false;
			}
		};
		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Sex");
		model.addColumn("Age");
		model.addColumn("Date of birth");
		model.addColumn("Email");
		model.addColumn("Phone number");

		ArrayList<Patient> patients = new ArrayList<Patient>();
		try {
			patients = client.getPatients(doctor.getId());
			System.out.println("soy client");
			System.out.println(patients);
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(DoctorMenu.this, "Problems connecting with server", "Message",
					JOptionPane.ERROR_MESSAGE);
		}
		if (patients != null) {
			for (Patient patient : patients) {
				Object[] datos = new Object[] { patient.getId(), patient.getName(), patient.getSex(), patient.getAge(),
						patient.getDob(), patient.getEmail(), patient.getPhoneNumber() };
				model.addRow(datos);
			}
		}
		table.setModel(model);

		JButton medicalHistory = new JButton("Medical history");
		medicalHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (table.getSelectedRowCount() > 0) {
					int i = table.getSelectedRow();
					String patientID = table.getValueAt(i, 0).toString();
					try {
						client.sendFunction("medicalhistory");
						Patient patientSelected = client.getPatient(patientID);
						ArrayList<MedicalHistory> allMedicalHistory = client.getAllMedicalHistory(patientID);
						if (allMedicalHistory != null) {
							JFrame patientMH = new PatientMH(DoctorMenu.this, client, patientSelected,
									allMedicalHistory);
							patientMH.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(DoctorMenu.this,
									"Patient has not upload any medical reports", "Message",
									JOptionPane.WARNING_MESSAGE);
						}
					} catch (ClassNotFoundException | IOException e1) {
						JOptionPane.showMessageDialog(DoctorMenu.this, "Problems connecting with server", "Message",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(DoctorMenu.this, "Select a patient", "Message",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		medicalHistory.setBounds(110, 245, 137, 32);
		contentPane.add(medicalHistory);

		JButton physiologicalParam = new JButton("Physiological Param");
		physiologicalParam.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (table.getSelectedRowCount() > 0) {
					int i = table.getSelectedRow();
					String patientID = table.getValueAt(i, 0).toString();
					try {
						client.sendFunction("signal");
						Patient patientSelected = client.getPatient(patientID);
						ArrayList<BitalinoSignal> bitalinoSignals = client.getBitalinoSignals(patientID);
						if (bitalinoSignals != null) {
							JFrame patientPP = new PatientPP(DoctorMenu.this, client, patientSelected,
									bitalinoSignals);
							patientPP.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(DoctorMenu.this,
									"Patient has not upload any BITalino signals", "Message",
									JOptionPane.WARNING_MESSAGE);
						}
					} catch (ClassNotFoundException | IOException e1) {
						JOptionPane.showMessageDialog(DoctorMenu.this, "Problems connecting with server", "Message",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(DoctorMenu.this, "Select a patient", "Message",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		physiologicalParam.setBounds(308, 245, 137, 32);
		contentPane.add(physiologicalParam);

		JButton logout = new JButton("Log out");
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					client.sendFunction("logout");
					client.closeConnection();
					System.exit(0);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(DoctorMenu.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		Image logOutImg = new ImageIcon(this.getClass().getResource("/logOut.png")).getImage();
		logout.setIcon(new ImageIcon(logOutImg));
		logout.setBounds(423, 25, 101, 23);
		contentPane.add(logout);
		
		JLabel userLabel = new JLabel("");
		Image userImg = new ImageIcon(this.getClass().getResource("/user.png")).getImage();
		userLabel.setIcon(new ImageIcon(userImg));
		userLabel.setBounds(27, 15, 45, 39);
		contentPane.add(userLabel);
		
		JLabel username = new JLabel(doctor.getName());
		username.setFont(new Font("Bookman Old Style", Font.PLAIN, 11));
		username.setBounds(66, 28, 121, 16);
		contentPane.add(username);

		
		// CLOSING CONNECTION WHEN CLOSING FRAME
		WindowListener exitListener = (WindowListener) new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.sendFunction("logout");
					client.closeConnection();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(DoctorMenu.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		this.addWindowListener(exitListener);
	}
}
