package bitbucket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;

/**
 * 
 * @author soen487-team08
 *
 */
public class BitbucketIssuesClient {

	private static String urlprefix = "https://api.bitbucket.org/1.0/repositories/soen487-w18-08/json-parser-issue-number/issues/";

	/**
	 * Get Issue Details given IssueNumber
	 * 
	 * @param issueNumber
	 * @throws Exception
	 */
	public static void getIssueDetails(int issueNumber) throws Exception {
		String inumber = Integer.toString(issueNumber);
		JSONObject json;

		try {
			URL base = new URL(urlprefix);
			URL url = new URL(base, inumber);
			json = new JSONObject(getText(url));
			// System.out.println(response);

			String[] names = JSONObject.getNames(json);

			for (String str : names) {
				if (!str.equals("reported_by")) {
					System.out.println(str + ":" + json.get(str));
				}
			}

		} catch (MalformedURLException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}

	/**
	 * Get Text given URL
	 * 
	 * @param url
	 * @return response
	 */
	public static String getText(URL url) {
		StringBuilder response = new StringBuilder();

		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				response.append(inputLine);

			in.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return response.toString();
	}

}
