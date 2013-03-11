package vnd.virtualarmor.hybridrouter.rest.v1;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import net.juniper.jmp.interceptors.hateoas.HATEOAS;
import net.juniper.jmp.interceptors.hateoas.CDATA;
import javax.xml.bind.annotation.XmlType;
import net.juniper.jmp.interceptors.hateoas.HATEOASMethod;
import net.juniper.jmp.interceptors.hateoas.HATEOASMethodObject;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import net.juniper.jmp.interceptors.hateoas.HATEOAS.LinkTypeEnum;
import net.juniper.jmp.parsers.annotations.Sortable;
import net.juniper.jmp.parsers.annotations.Filterable;
import java.util.Date;

/*******************************************************************************
 * FILE NAME: OidValue.java
 * PURPOSE:   These comments are auto generated
 *
 *
 * Revision History: 
 * DATE:           							  AUTHOR:              CHANGE:  
 * Sat Oct 27 22:53:32 MDT 2012               Auto generated       Initial Version  
 * 
 * 
 ******************************************************************************/
@XmlRootElement(name = "oid-values")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "value" })
public class OidValue {

	@XmlElement(name = "value", required = true)
	String value;
	@HATEOAS(uri = "")
	@XmlAttribute(name = "uri")
	private String uri = null;

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return uri
	 */
	public String getUri() {
		return uri;
	}
}
