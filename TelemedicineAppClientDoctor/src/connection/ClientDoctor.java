package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import telemedicineApp.pojos.BitalinoSignal;
import telemedicineApp.pojos.Doctor;
import telemedicineApp.pojos.MedicalHistory;
import telemedicineApp.pojos.Patient;

import java.net.Socket;
import java.time.LocalDate;

public class ClientDoctor {
	private Socket socket;
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;

	// Constructor
	public ClientDoctor(String serverIP, int port) {

		try {

			this.socket = new Socket(serverIP, port);
			this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
			this.objectInput = new ObjectInputStream(socket.getInputStream());
			
			//when initiating the program as doctor the server receives the "doctor" message to know the role of the client
			String role = "doctor";
			objectOutput.writeObject(role);
			objectOutput.flush();

		} catch (IOException ex) {
			Logger.getLogger(ClientDoctor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void closeConnection() throws IOException {

		objectInput.close();
		objectOutput.close();
		socket.close();

	}

	public void sendFunction(String function) throws IOException {
		objectOutput.writeObject(function);
		objectOutput.flush();
	}

	public boolean registerDoctor(Doctor doctor) throws IOException {
		objectOutput.writeObject(doctor);
		objectOutput.flush();
		return objectInput.readBoolean();
	}

	public Doctor checkDoctor(String id) throws IOException, ClassNotFoundException {
		objectOutput.writeObject(id);
		objectOutput.flush();
		Doctor doctor = (Doctor) objectInput.readObject();
		return doctor;
	}

	public ArrayList<Patient> getPatients(String id) throws IOException, ClassNotFoundException {
		objectOutput.writeObject(id);
		objectOutput.flush();
		ArrayList<Patient> patients = (ArrayList<Patient>) objectInput.readObject();
		return patients;
	}

	public Patient getPatient(String id) throws IOException, ClassNotFoundException {
		objectOutput.writeObject(id);
		objectOutput.flush();
		Patient patient = (Patient) objectInput.readObject();
		return patient;
	}

	public MedicalHistory getMedicalHistory(String patientID, LocalDate date)
			throws IOException, ClassNotFoundException {
		objectOutput.writeObject(patientID);
		objectOutput.flush();
		objectOutput.writeObject(date);
		objectOutput.flush();
		MedicalHistory medicalHistory = (MedicalHistory) objectInput.readObject();
		return medicalHistory;
	}

	public ArrayList<MedicalHistory> getAllMedicalHistory(String patientID) throws IOException, ClassNotFoundException {
		objectOutput.writeObject(patientID);
		objectOutput.flush();
		ArrayList<MedicalHistory> allMedicalHistory = (ArrayList<MedicalHistory>) objectInput.readObject();
		return allMedicalHistory;
	}

	public BitalinoSignal getBitalinoSignal(String patientID, LocalDate date)
			throws IOException, ClassNotFoundException {
		objectOutput.writeObject(patientID);
		objectOutput.flush();
		objectOutput.writeObject(date);
		objectOutput.flush();
		BitalinoSignal bitalinoSignal = (BitalinoSignal) objectInput.readObject();
		return bitalinoSignal;
	}

	public ArrayList<BitalinoSignal> getBitalinoSignals(String patientID) throws IOException, ClassNotFoundException {
		objectOutput.writeObject(patientID);
		objectOutput.flush();
		ArrayList<BitalinoSignal> bitalinoSignals = (ArrayList<BitalinoSignal>) objectInput.readObject();
		return bitalinoSignals;
	}

}
