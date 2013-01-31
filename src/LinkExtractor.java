import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LinkExtractor {
	public LinkExtractor() {

	}
	public ArrayList<String> grabLinks(final String html) {
		ArrayList<String> result = new ArrayList<String>();
		Document doc = Jsoup.parse(html);
		for (int i = 0; i < doc.select("a").size(); i++) {
			result.add(doc.select("a").get(i).attr("href"));
		}
		return result;
	}
}