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
	protected String value;

	@XmlAttribute
	protected String href;

	public OidValueResponse()
	{
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
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
