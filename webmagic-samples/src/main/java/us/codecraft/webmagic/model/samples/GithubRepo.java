package us.codecraft.webmagic.model.samples;

import java.util.List;

import us.codecraft.webmagic.SiteConfig;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.HasKey;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author code4crafter@gmail.com <br>
 */
@TargetUrl("https://github.com/\\w+")
@HelpUrl({ "https://github.com/\\w+\\?tab=repositories", "https://github.com/\\w+", "https://github.com/explore/*" })
public class GithubRepo implements HasKey {

	@ExtractBy(value = "//h1[@class='entry-title public']/strong/a/text()", notNull = true)
	private String name;

	@ExtractByUrl("https://github\\.com/(\\w+)/.*")
	private String author;

	@ExtractBy("//div[@id='readme']")
	private String readme;

	@ExtractBy(value = "//div[@class='repository-lang-stats']//li//span[@class='lang']")
	private List<String> language;

	@ExtractBy("//a[@class='social-count js-social-count']/text()")
	private String star;

	@ExtractBy("//a[@class='social-count js-social-count']/text()")
	private String fork;

	@ExtractByUrl
	private String url;

	@Override
	public String key() {
		return author + ":" + name;
	}

	public String getName() {
		return name;
	}

	public String getReadme() {
		return readme;
	}

	public String getAuthor() {
		return author;
	}

	public List<String> getLanguage() {
		return language;
	}

	public String getUrl() {
		return url;
	}

	public String getStar() {
		return star;
	}

	public String getFork() {
		return fork;
	}

	public static void main(String[] args) {
		OOSpider.create(SiteConfig.getInstance().setSleepTime(5).setRetryTimes(3), new ConsolePageModelPipeline(),
				GithubRepo.class).addUrl("https://github.com/explore").setScheduler(new QueueScheduler()).thread(1)
				.run();
	}
}
