package qa.all.amharic;
public class QueryGenerator {
	public String QueryExpand(String q) {
		AQAMain aqamain=new AQAMain();
	//	q=aqamain.jTextField1.getText();
		
		while (true)
		{
		AnalyzeQuestion anq = new AnalyzeQuestion();
		//remove question particles
		for (int i = 0; i < anq.PlaceQuestinParticles.length - 1; i++)
			if (q.contains(anq.PlaceQuestinParticles[i])) {
				StringBuffer x = new StringBuffer();
				x.append(q
						.substring(0, q.indexOf(anq.PlaceQuestinParticles[i])));
				int xx = anq.PlaceQuestinParticles[i].length();
				int xxx = q.indexOf(anq.PlaceQuestinParticles[i]);
				int zz = xx + xxx;
				x.append(q.substring(zz));
				q = x.toString();					
				break;
				
			}
		//remove question particles
		for (int i = 0; i < anq.PersonQuestionParticles.length - 1; i++)
			if (q.contains(anq.PersonQuestionParticles[i])) {
				StringBuffer x = new StringBuffer();
				x.append(q
						.substring(0, q.indexOf(anq.PersonQuestionParticles[i])));
				int xx = anq.PersonQuestionParticles[i].length();
				int xxx = q.indexOf(anq.PersonQuestionParticles[i]);
				int zz = xx + xxx;
				x.append(q.substring(zz));
				q = x.toString();// + "አቶ ወይዘሮ ወይዘሪት ዶክተር ኢንጂነር";
				
				break;
			}
		//remove question particles
		for (int i = 0; i < anq.NumberQuestionParticles.length - 1; i++)
			if (q.contains(anq.NumberQuestionParticles[i])) {
				StringBuffer x = new StringBuffer();
				x.append(q
						.substring(0, q.indexOf(anq.NumberQuestionParticles[i])));
				int xx = anq.NumberQuestionParticles[i].length();
				int xxx = q.indexOf(anq.NumberQuestionParticles[i]);
				int zz = xx + xxx;
				x.append(q.substring(zz));
				q = x.toString();
				break;
			}
		//remove question particles
		for (int i = 0; i < anq.TimeQuestionParticles.length - 1; i++)
			if (q.contains(anq.TimeQuestionParticles[i])) {
				StringBuffer x = new StringBuffer();
				x.append(q
						.substring(0, q.indexOf(anq.TimeQuestionParticles[i])));
				int xx = anq.TimeQuestionParticles[i].length();
				int xxx = q.indexOf(anq.TimeQuestionParticles[i]);
				int zz = xx + xxx;
				x.append(q.substring(zz));
				q = x.toString();
				
				break;
			}
		if( q.contains(" ይባላል"))
		q=q.replace(" ይባላል", "");
		if (q.contains(" ይባላሉ"))
		q=q.replace(" ይባላሉ", "");
		if (q.contains(" ነው"))
		q=q.replace(" ነው", "");
		if( q.contains("?"))
		q=q.replace("?","");
		q=q.trim();
		return q;
		}
	}

	public static void main(String[] args) {
		QueryGenerator qg = new QueryGenerator();
		String quetion = "ጠቅላይ ሚኒስትር መሆን የሚቻለው ከስንት አመት በላይ ነው";
		System.out.println(qg.QueryExpand(quetion));
	}

}
