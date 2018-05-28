package uow.cmde.transim.util.xml;

import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


/**
 * 
 * @author Vu The Tran
 * @since 14/12/2011
 */
public class XMLReader {
	
		private String filePath;
		private XMLInputFactory inputFactory;
		private InputStream in;
		private XMLEventReader eventReader;
		private XMLEvent xmlEvent;
		
		/**
		 * XMLReader
		 * @param filePath
		 */
		public XMLReader(String filePath)
		{
			this.filePath = filePath;
			
			try
			{
				inputFactory = XMLInputFactory.newInstance();
				in = new FileInputStream(filePath);
				eventReader = inputFactory.createXMLEventReader(in);
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		
		}
		
		/**
		 * isStartNode
		 * @param nodeName
		 * @return
		 */
		public StartElement isStartNode(String nodeName)
		{
			StartElement startElement = null;
			if (xmlEvent.isStartElement()) {
				startElement = xmlEvent.asStartElement();
				if (startElement.getName().getLocalPart().equals(nodeName)) {
					return startElement;
				}
			}
			
			return null;
		}
		
		/**
		 * isEndNode
		 * @param nodeName
		 * @return
		 */
		public boolean isEndNode(String nodeName)
		{
			if (xmlEvent.isEndElement()) {
				EndElement endElement = xmlEvent.asEndElement();
				if (endElement.getName().getLocalPart().equals(nodeName)) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * nextEvent
		 * @throws Exception
		 */
		public void nextEvent() throws Exception
		{
			
			xmlEvent = eventReader.nextEvent();
			
		}
		
		/**
		 * hasNext
		 * @return
		 */
		public boolean hasNext()
		{
			return eventReader.hasNext();
		}
		
		/**
		 * XMLEventReader
		 * @return
		 */
		public XMLEventReader getEventReader()
		{
			return eventReader;
		}
}
