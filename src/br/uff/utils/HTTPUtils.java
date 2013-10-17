package br.uff.utils;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

public class HTTPUtils {

	public static String acessar(String endereco) {

		try {
			URL url = new URL(endereco);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();
			Scanner in = new Scanner(is);
			String content = in.useDelimiter("\\A").next();
			in.close();
			return content;
		} catch (Exception e) {
			return null;
		}
	}

	public static String postar(String endereco, Map<String, String> dados) {
		try {
			URL url = new URL(endereco);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoInput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			JSONObject jsdata = new JSONObject();
			for(Map.Entry<String, String> entry : dados.entrySet()){
				jsdata.put(entry.getKey(), entry.getValue());
			}
			wr.write(jsdata.toString());
			wr.flush();
			wr.close();
			
			InputStream is = conn.getInputStream();
			Scanner in = new Scanner(is);
			String content = in.useDelimiter("\\A").next();
			in.close();
			return content;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
