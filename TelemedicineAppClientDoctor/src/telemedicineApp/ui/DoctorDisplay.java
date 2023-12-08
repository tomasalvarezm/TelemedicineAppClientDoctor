package telemedicineApp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
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

public class DoctorDisplay extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public DoctorDisplay(JFrame appDisplay, ClientDoctor client, Doctor doctor) {
		appDisplay.setVisible(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(86, 90, 358, 114);
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
			JOptionPane.showMessageDialog(DoctorDisplay.this, "Problems connecting with server", "Message",
					JOptionPane.ERROR_MESSAGE);
		}
		if(patients != null) {
			for(Patient patient : patients) {
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
				if(table.getSelectedRowCount() > 0) {
					int i = table.getSelectedRow();
					String patientID = table.getValueAt(i, 0).toString();
					try {
						client.sendFunction("medicalhistory");
						Patient patientSelected = client.getPatient(patientID);
						ArrayList<MedicalHistory> allMedicalHistory = client.getAllMedicalHistory(patientID);
						if(allMedicalHistory != null) {
							JFrame patientMH = new PatientMH(DoctorDisplay.this, client, patientSelected, allMedicalHistory);
							patientMH.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(DoctorDisplay.this, "Patient has not upload any medical reports", "Message",
									JOptionPane.WARNING_MESSAGE);
						}
					} catch (ClassNotFoundException | IOException e1) {
						JOptionPane.showMessageDialog(DoctorDisplay.this, "Problems connecting with server", "Message",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(DoctorDisplay.this, "Select a patient", "Message",
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
				if(table.getSelectedRowCount() > 0) {
					int i = table.getSelectedRow();
					String patientID = table.getValueAt(i, 0).toString();
					try {
						client.sendFunction("signal");
						Patient patientSelected = client.getPatient(patientID);
						ArrayList<BitalinoSignal> bitalinoSignals = client.getBitalinoSignals(patientID);
						if(bitalinoSignals != null) {
							JFrame patientPP = new PatientPP(DoctorDisplay.this, client, patientSelected, bitalinoSignals);
							patientPP.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(DoctorDisplay.this, "Patient has not upload any BITalino signals", "Message",
									JOptionPane.WARNING_MESSAGE);
						}
					} catch (ClassNotFoundException | IOException e1) {
						JOptionPane.showMessageDialog(DoctorDisplay.this, "Problems connecting with server", "Message",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(DoctorDisplay.this, "Select a patient", "Message",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		physiologicalParam.setBounds(283, 245, 137, 32);
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
					JOptionPane.showMessageDialog(DoctorDisplay.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		logout.setBounds(426, 11, 89, 23);
		contentPane.add(logout);
	}
}
