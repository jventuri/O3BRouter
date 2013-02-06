package vnd.virtualarmor.hybridrouter.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scriptParams")
public class ScriptParams {

	@XmlElement(required = false)
	protected List<ScriptParam> scriptParam;

	public List<ScriptParam> getScriptParam() {
		if (scriptParam == null) {
			scriptParam = new ArrayList<ScriptParam>();
		}
		return this.scriptParam;
	}
}
