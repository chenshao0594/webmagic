package us.codecraft.webmagic.handler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SiteConfig;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author code4crafter@gmail.com
 */
public class CompositePageProcessor implements PageProcessor {

    private SiteConfig site;

    private List<SubPageProcessor> subPageProcessors = new ArrayList<SubPageProcessor>();

    public CompositePageProcessor(SiteConfig site) {
        this.site = site;
    }

    @Override
    public void process(Page page) {
        for (SubPageProcessor subPageProcessor : subPageProcessors) {
            if (subPageProcessor.match(page.getRequest())) {
                SubPageProcessor.MatchOther matchOtherProcessorProcessor = subPageProcessor.processPage(page);
                if (matchOtherProcessorProcessor == null || matchOtherProcessorProcessor != SubPageProcessor.MatchOther.YES) {
                    return;
                }
            }
        }
    }

    public CompositePageProcessor setSite(SiteConfig site) {
        this.site = site;
        return this;
    }

    public CompositePageProcessor addSubPageProcessor(SubPageProcessor subPageProcessor) {
        this.subPageProcessors.add(subPageProcessor);
        return this;
    }

    public CompositePageProcessor setSubPageProcessors(SubPageProcessor... subPageProcessors) {
        this.subPageProcessors = new ArrayList<SubPageProcessor>();
        for (SubPageProcessor subPageProcessor : subPageProcessors) {
            this.subPageProcessors.add(subPageProcessor);
        }
        return this;
    }

    @Override
    public SiteConfig getSite() {
        return site;
    }
}
