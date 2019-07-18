package devframework.services;

import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HtmlMaker {
	private static final class HTMLStyle extends ToStringStyle {
 
		public HTMLStyle() {
			setFieldSeparator("</td></tr>"+ SystemUtils.LINE_SEPARATOR + "<tr><td>");
 
			setContentStart("<table>"+ SystemUtils.LINE_SEPARATOR +
								"<thead><tr><th>Field</th><th>Data</th></tr></thead>" +
								"<tbody><tr><td>");
 
			setFieldNameValueSeparator("</td><td>");
 
			setContentEnd("</td></tr>"+ SystemUtils.LINE_SEPARATOR + "</tbody></table>");
 
			setArrayContentDetail(true);
			setUseShortClassName(true);
			setUseClassName(false);
			setUseIdentityHashCode(false);
		}
 
		@Override
		public void appendDetail(StringBuffer buffer, String fieldName, Object value) {
			if (value.getClass().getName().startsWith("java.lang")) {
				super.appendDetail(buffer, fieldName, value);
			} else {
				buffer.append(ReflectionToStringBuilder.toString(value, this));
			}
		}
	}
 
	private String makeHTML(Object object) {
		return	ReflectionToStringBuilder.toString(object, new HTMLStyle());
	}
	
	static public String makeHTML(List<Object> objects) {
		String html = "";
		for (Object object : objects) {
			html+=ReflectionToStringBuilder.toString(object, new HTMLStyle());
		}
		return html;
	}
}