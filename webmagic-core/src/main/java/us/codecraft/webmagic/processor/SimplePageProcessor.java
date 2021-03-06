package us.codecraft.webmagic.processor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SiteConfig;

/**
 * A simple PageProcessor.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public class SimplePageProcessor implements PageProcessor {

	private String urlPattern;

	private SiteConfig site;

	public SimplePageProcessor(String urlPattern) {
		this.site = SiteConfig.getInstance();
		// compile "*" expression to regex
		this.urlPattern = "(" + urlPattern.replace(".", "\\.").replace("*", "[^\"'#]*") + ")";

	}

	@Override
	public void process(Page page) {
		List<String> requests = page.getHtml().links().regex(urlPattern).all();
		// add urls to fetch
		page.addTargetRequests(requests);
		// extract by XPath
		page.putField("title", page.getHtml().xpath("//title"));
		page.putField("html", page.getHtml().toString());
		// extract by Readability
		page.putField("content", page.getHtml().smartContent());
	}

	@Override
	public SiteConfig getSite() {
		return site;
	}
}
