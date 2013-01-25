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
 * FILE NAME: SnmpDevice_getOidValue.java
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
@XmlType(propOrder = { "field" })
public class SnmpDevice_getOidValue {

	@XmlElement(name = "field", required = true)
	String field;
	@HATEOAS(uri = "")
	@XmlAttribute(name = "uri")
	private String uri = null;

	/**
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return String
	 */
	public String getField() {
		return field;
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
