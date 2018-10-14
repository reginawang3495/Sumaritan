package CamHacks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class TextToSummary {
	public static String main(String args){
		String json = post(args);
		JsonParser jsonParser = new JsonParser();
		JsonArray done = jsonParser.parse(json).getAsJsonObject().get("sentences").getAsJsonArray();
		String endString = "";
		for(JsonElement sent: done)
			endString += sent.getAsString()+" ";
		endString = endString.trim();
		return endString;
	}
	public static String post(String data) {
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			String url = "https://api.aylien.com/api/v1/summarize";
			HttpPost securedResource = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);

			params.add(new BasicNameValuePair("text", data));
			params.add(new BasicNameValuePair("title", ""));
			params.add(new BasicNameValuePair("sentences_percentage", "30"));


			securedResource.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		//	securedResource.setHeader("Content-type", "application/json");
		//	securedResource.setHeader("Accept", "application/json");
			securedResource.setHeader("X-AYLIEN-TextAPI-Application-Key", "13815f6458e4609f8a34b505116d49f0");
			securedResource.setHeader("X-AYLIEN-TextAPI-Application-ID", "fb4ec7fb");


			CloseableHttpResponse httpResponse = client
					.execute(securedResource);
			HttpEntity responseEntity = httpResponse.getEntity();

			try {
				String strResponse = EntityUtils.toString(responseEntity);
				EntityUtils.consume(responseEntity);
				httpResponse.close();

				return strResponse;
			} catch (Exception e) {
				httpResponse.close();

				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
