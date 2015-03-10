package remoteserver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.KeyStore;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class Server {
	
	private static final String STOP_MESSAGE = "#Stop#";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		int socket = 9999;
		String keystore = "/Users/Feline/Documents/workspace/SSLserver/bin/foobar";
		char keystorepass[] = "foobar".toCharArray();
		char keypassword[] = "foobar".toCharArray();
		SSLServerSocket serverSocket = null;
		SSLSocket client = null;

		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(keystore), keystorepass);
			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, keypassword);

			SSLContext sslcontext = SSLContext.getInstance("TLS");

			sslcontext.init(kmf.getKeyManagers(), null, null);

			ServerSocketFactory ssf = sslcontext.getServerSocketFactory();

			serverSocket = (SSLServerSocket) ssf.createServerSocket(socket);
			// serverSocket.setNeedClientAuth(true);

			System.out.println("Starting server...");

			client = (SSLSocket) serverSocket.accept();
			System.out.println("client connected");
			String string = null, response = null;
			Worker worker = new Worker();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream()));
			while ((string = in.readLine()) != null) {
					response = worker.deploy(string);
					System.out.println(string);
					System.out.println(response);
					out.write(response);
					out.write(STOP_MESSAGE + "\n");
					out.flush();
					if(worker.isQuit())
						break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}