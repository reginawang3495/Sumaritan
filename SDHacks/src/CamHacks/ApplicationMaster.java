package CamHacks;

public class ApplicationMaster {
	static String status = "0"; // 1 is visually impaired, 2 is education summary, 3 is education no summary 
	static String fileName = "C:/Users/rrreg/Desktop/CamHacks/SDHacks/image.JPG";
	public static void main(String [] args){
		status = FlowlayoutExperiment.main();
		CameraToPicture.main(fileName);
		while(!CameraToPicture.interrupted){}
		try{
			Thread.sleep(1000);
		}
		catch(Exception e){}
		String text = "";
		if(status.equals("1"))
			text = PictureToText.main(fileName, false);
		else 
			text = PictureToText.main(fileName, true);
		if(status.equals("2"))
			text = TextToSummary.main(text);
		System.out.println(text);
		TextToSpeech.main(text);
	}
	public static void toText(){


	}
}
