package telemedicineApp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DoctorDisplay extends JFrame {

	private JPanel contentPane;
	private JPanel signalPane;

	/**
	 * Create the frame.
	 */
	public DoctorDisplay(JFrame appDisplay) {
		appDisplay.setVisible(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 602, 346);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JCheckBox dizziness = new JCheckBox("Dizziness");
		dizziness.setBounds(46, 150, 97, 23);
		contentPane.add(dizziness);
		DefaultListModel dlm = new DefaultListModel();
		dlm.addElement(dizziness);
		
		JComboBox medication = new JComboBox();
		medication.setModel(new DefaultComboBoxModel(new String[] {"Levodopa", "Pramipexol"}));
		medication.setBounds(46, 60, 200, 22);
		contentPane.add(medication);
		
		JLabel lblNewLabel = new JLabel("Select your medication");
		lblNewLabel.setBounds(46, 35, 163, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblIndicateYourSymptoms = new JLabel("Indicate your symptoms");
		lblIndicateYourSymptoms.setBounds(46, 129, 163, 14);
		contentPane.add(lblIndicateYourSymptoms);
		
		JCheckBox drowsiness = new JCheckBox("Drowsiness");
		drowsiness.setBounds(46, 186, 97, 23);
		contentPane.add(drowsiness);
		
		JCheckBox nausea = new JCheckBox("Nausea");
		nausea.setBounds(145, 150, 97, 23);
		contentPane.add(nausea);
		
		JCheckBox alucinations = new JCheckBox("Alucinations");
		alucinations.setBounds(145, 186, 97, 23);
		contentPane.add(alucinations);
		
		JCheckBox swelling = new JCheckBox("Swelling");
		swelling.setBounds(260, 150, 97, 23);
		contentPane.add(swelling);
		
		JCheckBox lackOfAppetite = new JCheckBox("Lack of appetite");
		lackOfAppetite.setBounds(260, 186, 130, 23);
		contentPane.add(lackOfAppetite);
		
		signalPane = new JPanel();
		signalPane.setBounds(23, 11, 367, 215);
		contentPane.add(signalPane);
		
		JButton uploadSignal = new JButton("Upload Signal");
		uploadSignal.setBounds(46, 240, 137, 32);
		contentPane.add(uploadSignal);
		
	}
}
