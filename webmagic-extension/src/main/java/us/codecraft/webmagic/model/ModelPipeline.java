package us.codecraft.webmagic.model;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * The extension to Pipeline for page model extractor.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
class ModelPipeline implements Pipeline {

	private Map<Class, PageModelPipeline> pageModelPipelines = new ConcurrentHashMap<Class, PageModelPipeline>();

	public ModelPipeline() {
	}

	public ModelPipeline put(Class clazz, PageModelPipeline pageModelPipeline) {
		pageModelPipelines.put(clazz, pageModelPipeline);
		return this;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		for (Map.Entry<Class, PageModelPipeline> classPageModelPipelineEntry : pageModelPipelines.entrySet()) {
			Object obj = resultItems.get(classPageModelPipelineEntry.getKey().getCanonicalName());
			if (obj == null) {
				return;
			}
			Annotation annotation = classPageModelPipelineEntry.getKey().getAnnotation(ExtractBy.class);
			if (annotation == null) {
				classPageModelPipelineEntry.getValue().process(obj, task);
			} else {
				List<Object> list = (List<Object>) obj;
				for (Object o1 : list) {
					classPageModelPipelineEntry.getValue().process(o1, task);
				}
			}
		}
	}
}
