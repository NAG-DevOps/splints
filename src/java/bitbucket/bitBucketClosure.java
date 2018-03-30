
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.*;

	/**
	 *
	 * @author Kenneth Serrano from soen487-team08
	 *
	 */
	public class bitBucketClosure
	{

		private static String urlprefix = "https://api.bitbucket.org/1.0/repositories/soen487-w18-08/json-parser-issue-number/issues/";

		/**
		 * Get Issue Details given IssueNumber
		 *
		 * @param issueNumber
		 * @throws Exception
		 */
		public static void closingIssues(int issueNumber) throws Exception
		{
			String inumber = Integer.toString(issueNumber);
			URL url = null;

			JSONObject data = new JSONObject();
			data.put("status", "closed");
		    //System.out.println(data);

		    HttpURLConnection connection = null;


			try {
				URL base = new URL(urlprefix);
				url = new URL(base, inumber);
				//System.out.println("The Path given is: " + url.getPath());

				//Connect using PUT Request
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("PUT");
				connection.setDoOutput(true);
				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

				//Update status to close
				DataOutputStream output = new DataOutputStream(connection.getOutputStream());
				output.writeUTF(data.toString());
				output.flush();
				output.close();
				connection.disconnect();


				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
				{
	                System.out.println("Ok!");
	            } else
	            {
	                System.out.println(connection.getResponseCode());
	                System.out.println(connection.getResponseMessage());
	            }

			} catch (MalformedURLException e) {
				System.err.println("ERROR: " + e.getMessage());
			}

		}

	}
