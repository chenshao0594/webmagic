package us.codecraft.webmagic.configurable;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SiteConfig;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.Experimental;

import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 */
@Experimental
public class ConfigurablePageProcessor implements PageProcessor {

    private SiteConfig site;

    private List<ExtractRule> extractRules;

    public ConfigurablePageProcessor(SiteConfig site, List<ExtractRule> extractRules) {
        this.site = site;
        this.extractRules = extractRules;
    }

    @Override
    public void process(Page page) {
        for (ExtractRule extractRule : extractRules) {
            if (extractRule.isMulti()) {
                List<String> results = page.getHtml().selectDocumentForList(extractRule.getSelector());
                if (extractRule.isNotNull() && results.size() == 0) {
                    page.setSkip(true);
                } else {
                    page.getResultItems().put(extractRule.getFieldName(), results);
                }
            } else {
                String result = page.getHtml().selectDocument(extractRule.getSelector());
                if (extractRule.isNotNull() && result == null) {
                    page.setSkip(true);
                } else {
                    page.getResultItems().put(extractRule.getFieldName(), result);
                }
            }
        }
    }

    @Override
    public SiteConfig getSite() {
        return site;
    }

}
