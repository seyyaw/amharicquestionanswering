package qa.all.amharic;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.lucene.document.Document;

/**
 * 
 * This module is the sentence /paragraph ranker according
 * to the answer type, question focus and query similarity. 
 * @author Seid M
 * @since 2008
 */
public class Ranker {
	/**
	 * 
	 * @param map
	 *            the document with candidate answer and weight against the  query
	 * @param Origmap the constructed hash map of map
	 * @param m integer value to check first document
	 * @return newly constructed hashmap in turn the Origmap
	 * @throws IOException 
	 */
	public HashMap<String/* list of document */, HashMap<String, Integer>> AddToHash(
			HashMap<String, HashMap<String, Integer>> map,
			HashMap<String, HashMap<String, Integer>> Origmap, int m) throws IOException {
		HashMap<String, HashMap<String, Integer>> RetHash = new HashMap<String, HashMap<String, Integer>>();

		String sentences = map.keySet().toString();// docs
		AmharicStemmer r = new AmharicStemmer();//stem document and query
		StopStemmer stwr = new StopStemmer();
		StringTokenizer sentencess = new StringTokenizer(sentences, "[] ");
		String sentence = "";
		while (sentencess.hasMoreElements()) {
			String tk = sentencess.nextToken();
			if (!stwr.isStop(tk))
				sentence = sentences + r.Stem(tk) + " ";
		}
		String answers = map.values().toString();// answers+query count
		if (!(answers.contains("NoAnswer")) && m == 0) {
			Iterator it = map.keySet().iterator();
			while (it.hasNext())
				Origmap.put(sentence, map.get(it.next()));
			return Origmap;
		} else if (!(answers.contains("NoAnswer")) && m > 0) {
			Iterator it = Origmap.keySet().iterator();
			it = Origmap.keySet().iterator();
			int weight = 0;
			Boolean flag = false;
			while (it.hasNext()) {// for every document
				String sent = it.next().toString();// doc check for repetition
				HashMap<String, Integer> checkrpeatname = new HashMap<String, Integer>();
				checkrpeatname = Origmap.get(sent);// the answer plus weight
				Iterator itt = checkrpeatname.keySet().iterator();
				HashMap<String, Integer> checknewname = new HashMap<String, Integer>();
				Iterator ittt = map.keySet().iterator();// new candidate answer plus weight
				checknewname = map.get(ittt.next());
				ittt = checknewname.keySet().iterator();
				String nameold = itt.next().toString();
				String namenew = ittt.next().toString();
				if (nameold.equals(namenew) ||( nameold.contains(namenew)// check repetition
						|| namenew.contains(nameold))&&(nameold.length()>3||namenew.length()>3)) {
					weight = (Integer) checkrpeatname.get(nameold)
							+ (Integer) checknewname.get(namenew) + 1;
					checknewname.clear();
					if(nameold.length()>namenew.length())
					checknewname.put(nameold, weight);
					else
						checknewname.put(namenew, weight);
					String sentlist = sent + sentence;
					Origmap.remove(sent);// to add the newly added document to
					// the map.
					Origmap.put(sentlist, checknewname);
					flag = true;// flag adition to the map
					break;
				}
			}
			if (flag == true) 
			{
			} 
			else {
				HashMap<String, Integer> checknewname = new HashMap<String, Integer>();
				Iterator ittt = map.keySet().iterator();
				while (ittt.hasNext())
					checknewname = map.get(ittt.next());
				Origmap.put(sentences, checknewname);
			}
		}
		return Origmap;
	}

	/**
	 * this method counts the number of expected answer type in a sentence and
	 * the number of term matches a query did with the document terms
	 */
	// 
	
	
	public HashMap<String, HashMap<String,Integer>> RankedAnswers(
			HashMap<String, HashMap<String, Integer>> hm,
			HashMap<String, HashMap<String, Integer>> map, List docs,	List answers) 
			{
		HashMap<String, HashMap<String, Integer>> tempmap = new 
		HashMap<String, HashMap<String, Integer>>();
		Iterator mapit = map.keySet().iterator();
		int count = 0;
		while (mapit.hasNext()) {
			count = count + 1;
			mapit.next();
		}
		for (int i = 0; i < count; i++)// all collections
		{
			mapit = map.keySet().iterator();
			Iterator hmit = hm.keySet().iterator();
			String sent = mapit.next().toString();
			HashMap<String, Integer> out = map.get(sent);
			Iterator outi = out.keySet().iterator();
			String outval = outi.next().toString();
			String Tempoutval=outval;
			int valoutval=out.get(Tempoutval);
			while (hmit.hasNext()) {
				String sentin = hmit.next().toString();
				HashMap<String, Integer> in = hm.get(sentin);
				Iterator init = in.keySet().iterator();
				String inval = init.next().toString();
				if (out.get(outval) < in.get(inval)) {
					if(valoutval<in.get(inval))
					{
						valoutval	=in.get(inval);
					Tempoutval = inval;
					sent = sentin;
					}
				}
			}
			outval=Tempoutval;
			docs.add(sent);
			answers.add(outval);
			tempmap.put(sent,map.get(sent));
			map.remove(sent);
			hm.remove(sent);
		}
		 return tempmap;// the ranked candidate answers
	}
	public static void main(String[] gg) throws IOException {
		NumberFormat form = NumberFormat.getInstance( );
		form.setMinimumFractionDigits(3);
		form.setMaximumFractionDigits(3);
		double x=2345.9876544;
		System.out.println(form.format(x));
		HashMap<String, HashMap<String, Integer>> test = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, HashMap<String, Integer>> test2 = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> val = new HashMap<String, Integer>();
		Ranker rk = new Ranker();
		/*val.put("Abebe", 2);
		test2.put("Abebe beso bela", val);
		rk.AddToHash(test2, test, 2);
		val = new HashMap<String, Integer>();
		test2 = new HashMap<String, HashMap<String, Integer>>();
		val.put("Almaz", 3);
		test2.put("Almaz yeabebe ehit nech", val);
		rk.AddToHash(test2, test, 2);
		val = new HashMap<String, Integer>();
		test2 = new HashMap<String, HashMap<String, Integer>>();
		val.put("Abebe", 2);
		test2.put("Almaz yeabebe ehit nech", val);
		rk.AddToHash(test2, test, 2);
		val = new HashMap<String, Integer>();
		test2 = new HashMap<String, HashMap<String, Integer>>();
		val.put("Abebu", 99);
		test2.put("Nefisa Almazin Atiwedatim", val);
		rk.AddToHash(test2, test, 2);
		val = new HashMap<String, Integer>();
		test2 = new HashMap<String, HashMap<String, Integer>>();
		val.put("Almaz", 76);
		test2.put("Nefisa Almazin Atiwedatim", val);
		rk.AddToHash(test2, test, 2);
		val = new HashMap<String, Integer>();
		val = new HashMap<String, Integer>();
		test2 = new HashMap<String, HashMap<String, Integer>>();
		val.put("nefisa", 2);
		test2.put("Nefisa did marry seid", val);
		rk.AddToHash(test2, test, 2);
		val = new HashMap<String, Integer>();
		test2 = new HashMap<String, HashMap<String, Integer>>();
		val.put("NoAnswer", 682);
		test2.put("Abebe gin lemin yikoral", val);
		rk.AddToHash(test2, test, 2);
		val = new HashMap<String, Integer>();
		test2 = new HashMap<String, HashMap<String, Integer>>();*/
		val.put("መለስ ዜናዊ ለተስፋዬ ጉታ",3);
		test2.put("ሱዳን በሁሉም መስክ ከፍተኛ ዋጋ ሊሰጣት የሚገባ የኢትዮጵያ አጋራችን ናት ሲሉ ጠቅላይ ሚኒስትር " +
				"መለስ ዜናዊ ለተስፋዬ ጉታ አስታወቁ", val);
		HashMap<String, HashMap<String, Integer>> test3 = new HashMap<String, HashMap<String, Integer>>();
		test3 = (rk.AddToHash(test2, test, 1));
		List docs = new ArrayList();
		List answers = new ArrayList();
		rk.RankedAnswers(test3, test3, docs, answers);
		//System.out.println("*******Minch *********\n" + docs);
		System.out.println("*******Answer *********\n" + answers);
	}
}
