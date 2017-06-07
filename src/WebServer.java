import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class WebServer {
	public static void main(String args[]){
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			
			Socket socket = serverSocket.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String input;
			String filepath = "";
			while ((input = br.readLine()) != null){
				if (input.contains("GET")) {
					filepath = input.split(" ", 3)[1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
