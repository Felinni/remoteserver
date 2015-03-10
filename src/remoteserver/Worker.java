package remoteserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Worker {

	private boolean quit = false;

	public Worker() {

	}

	public String deploy(String input) {
		String response ="";
		switch (input) {
		case "cmd procs":
			try {
				String line;
				Process p = Runtime.getRuntime().exec("ps");
				BufferedReader input2 = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input2.readLine()) != null) {
					response += line + "\n";
				}
				input2.close();
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case "cmd pwd":
			try {
				String line;
				Process p = Runtime.getRuntime().exec("pwd");
				BufferedReader input2 = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input2.readLine()) != null) {
					response += line + "\n";
				}
				input2.close();
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case "date":
			try {
				String line;
				Process p = Runtime.getRuntime().exec("date");
				BufferedReader input2 = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = input2.readLine()) != null) {
					response += line + "\n";
				}
				input2.close();
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case "quit":
			response = "\n";
			quit = true;
			break;
		default:
			response = input + " command is not available.\n";
		}
		return response;
	}

	public boolean isQuit() {
		return quit;
	}

}
