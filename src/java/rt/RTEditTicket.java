package rt;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Edit Ticket Utility for RT
 * 
 * @author Kenneth Serrano and Vincent Fugnitto from soen487-team08
 *
 */
public class RTEditTicket {

	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String HOSTNAME = "localhost";

	/**
	 * Test Runner for Edit Ticket
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String username = "user";
		String password = "pwd";
		String content = "test";
		int ticketId = 1;
		editTicket(HOSTNAME, username, password, ticketId, content);
	}

	/**
	 * Edit RT Ticket
	 * 
	 * @param username
	 * @param password
	 * @param ticketId
	 * @param content
	 */
	public static void editTicket(String host, String username, String password, int ticketId, String content) {
		String url = "http://" + host + "/REST/1.0/ticket/" + ticketId + "/edit?user=" + username + "&pass=" + password + "";
		String postData = "AdminCc: userX\nText: This is a REST test edit ticket\n";
		try {
			sendPost(url, postData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send Post HTTP Request
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private static void sendPost(String url, String params) throws Exception {

		// open URL connection
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setDoOutput(true);

		// add content
		DataOutputStream w = new DataOutputStream(con.getOutputStream());
		w.writeBytes(params);
		w.flush();
		w.close();

		// get response code
		int responseCode = con.getResponseCode();
		System.out.println("Response Code: " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
	}
}
