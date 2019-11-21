package basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XML {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//SAX解析
		//1、获取解析工厂
		SAXParserFactory factory=SAXParserFactory.newInstance();
		//2、从解析工厂获取解析器
		SAXParser parse=factory.newSAXParser();
		//3、编写处理器
		
		//4、加载文档 Document 注册处理器
		PersonHandler handler=new PersonHandler();
		//5、解析
		parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("basic/p.xml"),handler);
		//获取数据
		List<Person> persons=handler.getList();
		for(Person p:persons) {
			System.out.println(p.getName()+"->"+p.getAge());
		}
	}

}
class PersonHandler extends DefaultHandler{
	private List<Person> list;
	private Person person;
	private String tag;
	
	
	
	public List<Person> getList() {
		return list;
	}


	@Override
	public void startDocument() throws SAXException {
		list=new ArrayList<>();
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(null!=qName) {
			tag=qName;
			if(tag.equals("person")) {
				person=new Person();
			}
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		String contents = new String(ch,start,length).trim();
		if(null!=tag) {
			if(tag.equals("name")) {
				person.setName(contents);
			}else if(tag.equals("age")) {
				if(contents.length()>0) {
				person.setAge(Integer.parseInt(contents));
				}
			}
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(null!=qName) {
			if(qName.equals("person")) {
				list.add(person);
			}
		}
		tag=null;
	}
	public void endDocument() throws SAXException {
		
	}
}