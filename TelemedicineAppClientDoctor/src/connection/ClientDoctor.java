package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import telemedicineApp.pojos.Doctor;
import telemedicineApp.pojos.Patient;

import java.net.Socket;

public class ClientDoctor {
	private Socket socket;
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;
	
	//Constructor
	public ClientDoctor(String serverIP, int port) {
		
		try {
			this.socket = new Socket (serverIP, port);
			this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
			this.objectInput = new ObjectInputStream(socket.getInputStream());
			

		}catch(IOException ex) {
            Logger.getLogger(ClientDoctor.class.getName()).log(Level.SEVERE, null, ex);

		}
	}
	
	
	
	public void sendFunction(String function) throws IOException {
		objectOutput.writeObject(function);
	}
	
	public boolean registerDoctor(Doctor doctor) throws IOException {
		objectOutput.writeObject(doctor);
		return objectInput.readBoolean();
	}
	
	public Doctor checkDoctor(String id) throws IOException, ClassNotFoundException {
		objectOutput.writeObject(id);
		Doctor doctor = (Doctor) objectInput.readObject();
		return doctor;
	}
	public ArrayList <Patient> getPatients (String id) throws IOException, ClassNotFoundException{ 
		objectOutput.writeObject(id);
		ArrayList <Patient> patients = (ArrayList <Patient>)objectInput.readObject();
		return patients;
		
	}
	
	private static void releaseResources(OutputStream outputStream, Socket socket) {
        try {
            try {
            	
                outputStream.close();
                
            } catch (IOException ex) {
                Logger.getLogger(ClientDoctor.class.getName()).log(Level.SEVERE, null, ex);
            }

            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(ClientDoctor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
