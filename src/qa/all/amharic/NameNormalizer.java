package qa.all.amharic;
public class NameNormalizer {
	public String Suffremoved(String name)
	{
		if (name.endsWith("ና")||name.endsWith("እና"))
		{
			name=name.replace("ና", "");
		}
		return name;
	}
	public String Prefixremoved(String name)
	{
		if (name.startsWith("በ")||name.startsWith("ከ")||name.startsWith("የ")
				||name.startsWith("ለ"))
		{
			
		}
		return name;
	}
public static void main(String [] args)
{
	NameNormalizer normname=new NameNormalizer();
	System.out.print(normname.Suffremoved("አብዱራህማንና"));
}
}
