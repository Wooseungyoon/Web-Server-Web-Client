package WebClient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class WebClient {
	public static void main(String[] args) {
		WebClient client = new WebClient();
		Scanner key = new Scanner(System.in);
		try {
			String s1 = client.getWebContentByGet("http://192.168.11.245:6262/index.html","UTF-8",1000000);
			System.out.println(s1);
			String info = "2017029961/" + key.nextLine();
			String s2 = client.getWebContentByPost("http://192.168.11.245:6262/picResult", info, "UTF-8", 1000000);
			String s3 = client.getWebContentByPost("http://192.168.11.245:6262/postHandleTest", info,"UTF-8" , 1000000);
			System.out.println(s3);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public String getWebContentByGet(String urlString, final String charset, int timeout) throws IOException{
		if(urlString == null || urlString.length()==0) {
			return null;
		}
		urlString = (urlString.startsWith("http://")||urlString.startsWith("https://")) ? urlString
				:("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		conn.setRequestProperty("User-Agent", 
		//		"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; NET CLR 1.1.4322; .NET CLR 2.0.50727");
				"2017029961/WooSeungYoon/WEBCLIENT/COMPUTERNETWORK");
		conn.setRequestProperty("Accept", "text/html");
		conn.setConnectTimeout(timeout);
		try {
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input,charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while((line = reader.readLine())!=null) {
			sb.append(line).append("\r\n");
		}
		if(reader!=null) {
			reader.close();
		}
		if(conn != null) {
			conn.disconnect();
		}
		return sb.toString();
	}
	public String getWebContentByGet(String urlString)throws IOException{
		return getWebContentByGet(urlString,"iso-8859-1",5000);
	}
	
	public String getWebContentByPost(String urlString, String data, final String charset, int timeout)
			throws IOException{
		if(urlString == null || urlString.length() == 0) {
			return null;
		}
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString
				:("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		
		connection.setRequestProperty("User-Agent", "2017029961/WooSeungYoon/WEBCLIENT/COMPUTERNETWORK");
		
		connection.setRequestProperty("Accept", "text/html");
		connection.setConnectTimeout(timeout);
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		
		
		byte[] content = data.getBytes("UTF-8");
		
		out.write(content);
		out.flush();
		out.close();
		try {
	         if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
	            return null;
	         } 
	      }catch(IOException e) {
	         e.printStackTrace();
	         return null;
	      }
	      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
	      String line;
	      StringBuffer sb = new StringBuffer();
	      while ((line = reader.readLine()) != null) {
	         sb.append(line).append("\r\n");   
	      }
	      if(reader != null) reader.close();
	      if (connection != null) connection.disconnect();
	      return sb.toString();
	}
	
}

