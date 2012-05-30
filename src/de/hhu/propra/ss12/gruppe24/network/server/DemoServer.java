package de.hhu.propra.ss12.gruppe24.network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DemoServer {
	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(1338)) {
			Socket clientSocket = serverSocket.accept();
			InputStream in = clientSocket.getInputStream();
			OutputStream out = clientSocket.getOutputStream();
			DataInputStream dataIn = new DataInputStream(in);
			DataOutputStream dataOut = new DataOutputStream(out);
			String nachricht = dataIn.readUTF();
			dataOut.writeUTF(nachricht);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
