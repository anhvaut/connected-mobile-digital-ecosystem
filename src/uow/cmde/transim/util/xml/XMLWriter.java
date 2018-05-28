package uow.cmde.transim.util.xml;

import java.io.FileOutputStream;
import java.util.*;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


/**
 * 
 * @author Vu The Tran
 * @since 14/12/2011
 */
public class XMLWriter {
	
	private String filePath ="";
	private XMLOutputFactory outputFactory;
	private XMLEventWriter eventWriter;
	private XMLEventFactory eventFactory;
	
	public XMLWriter()
	{
	
	}
	
	/**
	 * setFilePath
	 * @param filePath
	 */
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
	
	/**
	 * createStartDocument
	 */
	public void createStartDocument()
	{
		try
		{
			outputFactory = XMLOutputFactory.newInstance();
			eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(filePath));
			eventFactory = XMLEventFactory.newInstance();
			XMLEvent end = eventFactory.createDTD("\n");
	
			StartDocument startDocument = eventFactory.createStartDocument();
			eventWriter.add(startDocument);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * createEndDocument
	 */
	public void createEndDocument()
	{
		try
		{
			eventWriter.add(eventFactory.createEndDocument());
			eventWriter.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * createClosedNode
	 * @param name
	 * @param value
	 * @param xmlAttributeList
	 * @param numberOfNewLines
	 * @param numberOfTabs
	 */
	public void createClosedNode(String name,String value, XMLAttributeList xmlAttributeList, int numberOfNewLines, int numberOfTabs)
	{
		try
		{
	
			XMLEvent newLine = eventFactory.createDTD("\n");
			createStartNode(name,xmlAttributeList,numberOfNewLines,numberOfTabs);
			
			if(!value.equals(""))
			{
				Characters characters = eventFactory.createCharacters(value);
				eventWriter.add(characters);
			}
			
			createEndNode(name,numberOfNewLines,numberOfTabs);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * createStartNode
	 * @param name
	 * @param xmlAttributeList
	 * @param numberOfNewLines
	 * @param numberOfTabs
	 */
	public void createStartNode(String name, XMLAttributeList xmlAttributeList, int numberOfNewLines, int numberOfTabs)
	{
		try
		{
	
			XMLEventFactory eventFactory = XMLEventFactory.newInstance();
			XMLEvent newLine = eventFactory.createDTD("\n");
			XMLEvent tab = eventFactory.createDTD("\t");

			StartElement sElement = null;
			if(xmlAttributeList!=null && !xmlAttributeList.getListAttribute().isEmpty())
			{
		        List nsList = Arrays.asList();
				sElement = eventFactory.createStartElement("", "", name,xmlAttributeList.getListAttribute().iterator(), nsList.iterator());
			}
			else
			{
				sElement = eventFactory.createStartElement("", "", name);
			}
			
			for(int i=0;i<numberOfNewLines;i++)
			{
				eventWriter.add(newLine);
			}
			for(int i=0;i<numberOfTabs;i++)
			{
				eventWriter.add(tab);
			}
			eventWriter.add(sElement);
			
		
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * createEndNode
	 * @param name
	 * @param numberOfNewLines
	 * @param numberOfTabs
	 */
	public void createEndNode(String name, int numberOfNewLines, int numberOfTabs)
	{
		try
		{
			XMLEvent newLine = eventFactory.createDTD("\n");
			XMLEvent tab = eventFactory.createDTD("\t");
			
			EndElement eElement = eventFactory.createEndElement("", "", name);
			for(int i=0;i<numberOfNewLines;i++)
			{
				eventWriter.add(newLine);
			}
			for(int i=0;i<numberOfTabs;i++)
			{
				eventWriter.add(tab);
			}
			eventWriter.add(eElement);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public void newLine(XMLEventFactory eventFactory)
	{
		
	}
	
	public XMLEventFactory getEventFactory()
	{
		return eventFactory;
	}
}
