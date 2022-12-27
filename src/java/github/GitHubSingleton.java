package github;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GitHubSingleton extends GitHub {

	
	public static void main (String []args){
		System.out.println("Enter your username:");
		Scanner scan = new Scanner(System.in);
		String user = scan.nextLine();
		System.out.println("Enter your password:");
		String password = scan.nextLine();
		setUp(user, password);
	}
	
	public static void setUp(String user, String password) {
		GitHub git = new GitHub();
		git.setUser(user);
		git.setPassword(password);
		
		Map<String, Serializable> content = new HashMap<String, Serializable>();
		content.put("title", "API Test");
		content.put("body", "body");
		content.put("assignee", "assignee");
		content.put("milestone", "milestone");
		content.put("labels", "labels");
		content.put("assignees", "assignees");
		git.createIssue(content);
	}
}