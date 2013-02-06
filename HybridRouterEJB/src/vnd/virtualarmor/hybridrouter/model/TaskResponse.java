package vnd.virtualarmor.hybridrouter.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskResponse {

	@XmlElement(required = true)
	protected int id;

	@XmlAttribute
	protected String href;

	public TaskResponse() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
