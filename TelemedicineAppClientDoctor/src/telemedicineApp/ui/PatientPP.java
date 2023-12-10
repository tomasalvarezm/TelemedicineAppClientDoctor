package telemedicineApp.ui;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import connection.ClientDoctor;
import telemedicineApp.pojos.BitalinoSignal;
import telemedicineApp.pojos.MedicalHistory;
import telemedicineApp.pojos.Patient;

import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.time.LocalDate;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ScrollPaneConstants;

public class PatientPP extends JFrame {

	private JPanel contentPane;
	private JTextPane textPane;

	/**
	 * Create the frame.
	 */
	public PatientPP(JFrame doctorMenu, ClientDoctor client, Patient patient,
			ArrayList<BitalinoSignal> bitalinoSignals) {
		setTitle("Doctor");
		doctorMenu.setVisible(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(88, 79, 268, 211);
		contentPane.add(scrollPane);

		JButton back = new JButton("Back");
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					client.sendFunction("differentpatient");
					doctorMenu.setVisible(true);
					PatientPP.this.setVisible(false);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(PatientPP.this, "Problems connecting with server", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		back.setBounds(10, 327, 89, 23);
		contentPane.add(back);

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);

		JComboBox bitalinoDate = new JComboBox();
		bitalinoDate.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					client.sendFunction("samepatient");
					BitalinoSignal bitalinoSignal = client.getBitalinoSignal(patient.getId(),
							(LocalDate) bitalinoDate.getSelectedItem());
					if (bitalinoSignal == null) {
						JOptionPane.showMessageDialog(PatientPP.this, "Problems accesing data base", "Message",
								JOptionPane.ERROR_MESSAGE);
					} else {
						textPane.setText(bitalinoSignal.toString());
					}
				} catch (IOException | ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(PatientPP.this, "Problems connecting with server", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		bitalinoDate.setBounds(181, 28, 132, 22);
		contentPane.add(bitalinoDate);
		for (BitalinoSignal bitalinoSignal : bitalinoSignals) {
			bitalinoDate.addItem(bitalinoSignal.getDateSignal());
		}

		JLabel lblNewLabel = new JLabel("Select by date");
		lblNewLabel.setBounds(78, 30, 104, 18);
		contentPane.add(lblNewLabel);

		// CLOSING CONNECTION WHEN CLOSING FRAME
		WindowListener exitListener = (WindowListener) new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.sendFunction("differentpatient");
					client.sendFunction("logout");
					client.closeConnection();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(PatientPP.this, "Problems closing connection", "Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		this.addWindowListener(exitListener);
	}
}
