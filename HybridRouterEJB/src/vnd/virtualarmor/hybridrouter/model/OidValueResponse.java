package vnd.virtualarmor.hybridrouter.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "oid-values")
@XmlAccessorType(XmlAccessType.FIELD)
public class OidValueResponse
{

	@XmlElement(required = true)
	protected String field;

	@XmlAttribute
	protected String href;

	public OidValueResponse()
	{
	}

	public String getField()
	{
		return field;
	}

	public void setField(String field)
	{
		this.field = field;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}
}
