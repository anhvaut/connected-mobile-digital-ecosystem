package uow.cmde.transim.util.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.Attribute;

/**
 * 
 * @author Vu The Tran
 * @since 14/12/2011
 */
public class XMLAttributeList {
	
	private List<Attribute> attributeList;
	private XMLEventFactory eventFactory;
	
	/**
	 * XMLAttributeList
	 * @param eventFactory
	 */
	public XMLAttributeList(XMLEventFactory eventFactory)
	{
		attributeList = new ArrayList<Attribute>();
		this.eventFactory = eventFactory;
	}
	
	/**
	 * addAttribute
	 * @param name
	 * @param value
	 */
	public void addAttribute(String name, String value)
	{
		Attribute attribute = eventFactory.createAttribute(name, value);
		attributeList.add(attribute);
		
	}
	
	/**
	 * getListAttribute
	 * @return
	 */
	public List<Attribute> getListAttribute()
	{
		return attributeList;
	}

}
