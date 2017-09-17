package com.rn.tools;

import java.util.ArrayList;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class readXMLTools
{
  private static final String FILE_PATH = "config/cfg.xml";

  public static ArrayList<String> readXMLCompanyType(String eleName, String textName)
  {
    ArrayList com = new ArrayList();

    SAXReader saxReader = new SAXReader();
    try {
      Document document = saxReader.read("config/cfg.xml");

      Element rootEle = document.getRootElement();

      Iterator iter = rootEle.elementIterator(eleName);

      while (iter.hasNext()) {
        Element recordEle = (Element)iter.next();
        String name = recordEle.elementTextTrim(textName);
        com.add(name);
      }
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    return com;
  }
}