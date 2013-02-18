package vnd.virtualarmor.hybridrouter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scriptParams")
public class ScriptParams implements Serializable {

	/**
	 * generated serial id
	 */
	private static final long serialVersionUID = 7328982692248620675L;
	
	@XmlElement(required = false)
	protected List<ScriptParam> scriptParam;

	public List<ScriptParam> getScriptParam() {
		if (scriptParam == null) {
			scriptParam = new ArrayList<ScriptParam>();
		}
		return this.scriptParam;
	}
}
