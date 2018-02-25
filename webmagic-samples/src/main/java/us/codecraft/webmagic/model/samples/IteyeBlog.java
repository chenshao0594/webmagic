package us.codecraft.webmagic.model.samples;

import java.util.Arrays;

import us.codecraft.webmagic.SiteConfig;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @author code4crafter@gmail.com <br>
 * Date: 13-8-2 <br>
 * Time: 上午7:52 <br>
 */
@TargetUrl("http://*.iteye.com/blog/*")
public class IteyeBlog implements Blog{

    @ExtractBy("//title")
    private String title;

    @ExtractBy(value = "div#blog_content",type = ExtractBy.Type.Css)
    private String content;

    @Override
    public String toString() {
        return "IteyeBlog{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public static void main(String[] args) {
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";

		OOSpider.create(SiteConfig.getInstance().setUserAgents(Arrays.asList(userAgent)), new ConsolePageModelPipeline(),
				IteyeBlog.class)
				.addUrl("http://flashsword20.iteye.com/blog")
				.run();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
