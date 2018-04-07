/**
 * Author: Colin Koo
 * Professor: Nima Davarpanah
 * Program: This program emulates receiving a HTML packet-protocol through a server socket and returns a proper corresponding webpage.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	/**
	 * Creates a server socket that receives data from a client, which would be a HTTP request.
	 * @param args
	 */
	public static void main(String args[]){
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			Socket socket = serverSocket.accept();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String[] data = getData(br);
			String filepath = "www" + data[1];	//get location of file
			PrintStream out = new PrintStream(socket.getOutputStream(), false);

			String content = "", temp = "";
			BufferedReader fileReader = null;
			
			if ((new File(filepath)).exists()){	// get content
				fileReader = new BufferedReader(new FileReader(filepath));
				while ((temp = fileReader.readLine()) != null){
					content += temp;
				}
			}
			else{
				fileReader = new BufferedReader(new FileReader(new File("www/notfound.html")));
				while ((temp = fileReader.readLine()) != null){
					content += temp;
				}
			}
			String header = data[2] + " 200 OK\nContent-type: text/html\nContent-length: " + content.length() + "\n\n";
			out.println(header + content);

			fileReader.close();
			out.close();
			br.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns a data array containing GET, the HTML file, and 
	 * @param br
	 * @return
	 */
	public static String[] getData(BufferedReader br){
		String[] data = new String[3];
		String input = "";
		try {
			while (((input = br.readLine()) != null)){
				if (input.contains("GET")) {
					//GET /hello.html HTTP/1.1
					data = input.split(" ", 3);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
