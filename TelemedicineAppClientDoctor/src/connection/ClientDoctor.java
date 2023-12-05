package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import telemedicineApp.pojos.Patient;

import java.net.Socket;

public class ClientDoctor {
	private Socket socket;
	private ObjectInputStream objectInput;
	
	//Constructor
	public ClientDoctor(Socket socket, ObjectInputStream objectInput) {
		super();
		this.socket = socket;
		//para captar excepciones
		try {
			this.objectInput = new ObjectInputStream(this.socket.getInputStream());

		}catch(IOException ex) {
            Logger.getLogger(ClientDoctor.class.getName()).log(Level.SEVERE, null, ex);

		}
	}
	
	
	public Patient readPatients() {
		Patient patient=null;
		while (socket.isConnected()) {
		Object object=null;
		try {
			object = objectInput.readObject();
			//TODO revisar excepciones
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//instanceof lo utilizamos para comprobar si el objeto recibido 
		//es de la clase que se indica posteriormente.
		if(object instanceof Patient) {
			patient= (Patient) object;
		}
		}
		return patient;
	}
	
	public void releaseResources() {
		try {
		try {
            objectInput.close();

        } catch (IOException ex) {
            Logger.getLogger(ClientDoctor.class.getName()).log(Level.SEVERE, null, ex);
        }
        socket.close();
    } catch (IOException ex) {
        Logger.getLogger(ClientDoctor.class.getName()).log(Level.SEVERE, null, ex);
    }
	}
}
