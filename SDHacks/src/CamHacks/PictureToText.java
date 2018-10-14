package CamHacks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.ByteString;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;






import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class PictureToText {
	static String fileName;
	public static String main(String file, boolean document){
		try {
			fileName = file;
			PrintStream ps;
			//	ps = new PrintStream(new File(fileName));
			//	detectText("C:/Users/rrreg/Desktop/Pictures/9/IMG_6025.png",ps);
			return toText(new File(fileName),document).replace("-", "").replaceAll("\\s+", " ");
			//	ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ApplicationMaster.main(new String [0]);
		return "";
	}

	public static String toText(File file, boolean document){
		String arr;
		try {
			arr = Base64.encodeBase64URLSafeString(FileUtils.readFileToByteArray(file));

			String json;
			if(!document) // is not document
				json = post("{\"requests\":[{\"image\":{\"content\":\""+ arr+"\" },		   \"features\": [		    {		     \"type\": \"TEXT_DETECTION\" }	   ],  \"imageContext\": {   \"languageHints\": [\"en\"  ] 	}  }	 ]		}		 ");
			else
				json = post("{\"requests\":[{\"image\":{\"content\":\""+ arr+"\" },		   \"features\": [		    {		     \"type\": \"DOCUMENT_TEXT_DETECTION\" }	   ],  \"imageContext\": {   \"languageHints\": [\"en\"  ] 	}  }	 ]		}		 ");

			JsonParser jsonParser = new JsonParser();
			try{
			JsonObject expobj = jsonParser.parse(json).getAsJsonObject();
			JsonArray obj2 =	expobj.get("responses").getAsJsonArray();
			JsonObject obj3 = obj2.get(0).getAsJsonObject();
				JsonArray obj4 = obj3.get("textAnnotations").getAsJsonArray();
				JsonObject obj5 = obj4.get(0).getAsJsonObject();
				String result = obj5.get("description").getAsString();
				return result;
			}
			catch(Exception e){
				try{
					Thread.sleep(3000);
				}
				catch(Exception ex){}
				ApplicationMaster.main(new String [0]);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String post(String data) {
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			String url = "https://vision.googleapis.com/v1/images:annotate?key=AIzaSyChCeK5v6rjUM7qlReCW8wf9l1pmvFagv8";
			HttpPost securedResource = new HttpPost(url);
			securedResource.setEntity(new StringEntity(data));
			// securedResource.addHeader("Accept", "application/json");
			securedResource.setHeader("Content-type", "application/json");
			securedResource.setHeader("Accept", "application/json");

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

	public static void detectText(String filePath, PrintStream out) throws Exception, IOException {
		// set GOOGLE_APPLICATION_CREDENTIALS=C:/Users/rrreg/Downloads/request.json
		GoogleCredential credential = GoogleCredential.fromStream(
				Files.newInputStream(Paths.get("C:\\Users\\rrreg\\Downloads\\request.json")))
				.createScoped(VisionScopes.all());
		Vision vision = new Vision.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(), credential)
		.setApplicationName("SD Hacks")
		.build();

		GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\rrreg\\Downloads\\request.json"))
				.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

		List<AnnotateImageRequest> requests = new ArrayList<>();

		ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

		Image img = Image.newBuilder().setContent(imgBytes).build();
		Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();

		AnnotateImageRequest request =
				AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);

		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					out.printf("Error: %s\n", res.getError().getMessage());
					return;
				}

				// For full list of available annotations, see http://g.co/cloud/vision/docs
				for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
					out.printf("Text: %s\n", annotation.getDescription());
					out.printf("Position : %s\n", annotation.getBoundingPoly());
				}
			}
		}
	}
}
