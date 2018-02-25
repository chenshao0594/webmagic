package us.codecraft.webmagic.model.samples;

import java.util.List;

import us.codecraft.webmagic.SiteConfig;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.HasKey;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author code4crafter@gmail.com <br>
 */
@TargetUrl("http://www.babytree.com/ask/detail/\\d+")
@HelpUrl({ "https://github.com/\\w+\\?tab=repositories", "https://github.com/\\w+", "https://github.com/explore/*" })
public class BabyTreeTest implements HasKey {

	@ExtractBy("//*[@id='qa-article']/div[2]/div/ul/li[1]/a/span/text()")
	private String author;

	@ExtractBy(value = "//*[@id=\"qa-answers\"]/div[2]/ul/li")
	private List<Ans> language;

	public static void main(String[] args) {
		OOSpider.create(SiteConfig.getInstance().setSleepTime(5).setRetryTimes(3), new ConsolePageModelPipeline(),
				BabyTreeTest.class).addUrl("http://www.babytree.com/ask/detail/43756180")
				.setScheduler(new QueueScheduler()).thread(1).run();
	}

	@Override
	public String key() {
		return this.author;
	}
}

class Ans {
	@ExtractBy(value = "ul/li[2]/a/span")
	private String name;
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
