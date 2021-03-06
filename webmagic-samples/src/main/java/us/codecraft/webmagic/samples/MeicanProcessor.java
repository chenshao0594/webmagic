package us.codecraft.webmagic.samples;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SiteConfig;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author code4crafter@gmail.com <br>
 * Date: 13-5-20
 * Time: 下午5:31
 */
public class MeicanProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        //http://progressdaily.diandian.com/post/2013-01-24/40046867275
        List<String> requests = page.getHtml().xpath("//a[@class=\"area_link flat_btn\"]/@href").all();
        if (requests.size() > 2) {
            requests = requests.subList(0, 2);
        }
        page.addTargetRequests(requests);
        page.addTargetRequests(page.getHtml().links().regex("(.*/restaurant/[^#]+)").all());
        page.putField("items", page.getHtml().xpath("//ul[@class=\"dishes menu_dishes\"]/li/span[@class=\"name\"]/text()"));
        page.putField("prices", page.getHtml().xpath("//ul[@class=\"dishes menu_dishes\"]/li/span[@class=\"price_outer\"]/span[@class=\"price\"]/text()"));
    }

    @Override
    public SiteConfig getSite() {
		return SiteConfig.getInstance().setDomain("meican.com").setCharset("utf-8");
    }

    public static void main(String[] args) {
        Spider.create(new MeicanProcessor()).addUrl("http://www.meican.com/shanghai/districts").run();
    }
}
