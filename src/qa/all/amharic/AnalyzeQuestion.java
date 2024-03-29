package qa.all.amharic;

import java.util.StringTokenizer;

import org.apache.lucene.search.QueryTermVector;

public class AnalyzeQuestion {
	/*
	 * @q the question already asked by the user
	 */
	private  String QType = null;
	//public String[]
	public  String[] PlaceQuestinParticles = { "የት", "ወደ የት", "ከየትኛው",
			"የየት", "ከየት", "ለየት", "እስከ የት", "በየት" };/*
													 * Question particles that
													 * are indicative of
													 */
	public  String[] PersonQuestionParticles = { "ማን", "ለማን", "ማነው",
			"እነማን", "ማንማን", "ማንን", "የማን", "ከማን", "በማን" };
	public  String[] TimeQuestionParticles = { "መቸ", "መቼ", "በመቸ",
			"እስከመቸ", "ለመቸ", "ከመቸ","መች","ለመች","እስከመች" };
	public  String[] NumberQuestionParticles = { "ከስንት","ምን ያክል","ምንያክል",
			"በስንት", "ምን ያህል", "ምን ያህሉ","ለስንት","ስንትን", "በምን ያህል","ስንቱ","ስንት","ስንተኛ" ,"በስንተኛው","ለስንተኛው","ለምን ያህል"};
	public  String[] PlaceQuestionTypes = { "ከተማ", "አገር", "ተራራ", "ወንዝ",
			"ሀይቅ", "ሆቴል", "አህጉር", "ፕላኔት" ,"ዋና ከተማ" };
	public String[] PersonQuestionTypes = { "ፕሬዝዳንት", "ጠቅላይ ሚኒስትር",
			"ሀላፊ", "ሌላ" };
	public  String[] NumericQuestionTypes = { "ዋጋ", "ርቀት", "ብዛት", };

	public String AnalyzedQuery(String q) {
		while (true) {
			if(q.contains("ማን ይባላል")&&q.contains("ዋና ከተማ"))
				{QType="Place";return QType;}
			if(q.contains("ማን ይባላል")&&q.contains("ፕሬዚዳንት"))
			{	QType = "Person";return QType;}
			// check if the question seeks Place Name
			for (int i = 0; i < PlaceQuestinParticles.length; i++) {
				if (q.contains(PlaceQuestinParticles[i])) {
					QType = "Place";
					break;
				}
			}
			// check if the question seeks Person Name
			for (int i = 0; i < PersonQuestionParticles.length; i++) {
				if (q.contains(PersonQuestionParticles[i])) {
					QType = "Person";
					break;
				}
			}
			// check if the question seeks Time and Date Value
			for (int i = 0; i < TimeQuestionParticles.length; i++) {
				if (q.contains(TimeQuestionParticles[i])) {
					QType = "Time";
					break;
				}
			}
			// check if the question seeks Numerical Value
			for (int i = 0; i < NumberQuestionParticles.length; i++) {
				String x=NumberQuestionParticles[i];
				if (q.contains(NumberQuestionParticles[i])) {
					QType = "Number";
					break;
				}
			}
			return QType;
		}
		
	}
	public static void main(String[] args) {
		AnalyzeQuestion qtype = new AnalyzeQuestion();
		String Question = "መኮንን ፈንታው ስንት ልጅ ወለደ?";
		System.out.println(qtype.AnalyzedQuery(Question));
	}
}