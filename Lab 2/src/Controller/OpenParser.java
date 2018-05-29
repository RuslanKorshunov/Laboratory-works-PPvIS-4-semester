package Controller;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import Model.Sportsman;
import View.Menu;

public class OpenParser
{
    private Menu menu;

    public OpenParser(Menu menu)
    {
        this.menu=menu;
    }

    public boolean openFile(String fileName)
    {
        try
        {
            SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
            SAXParser saxParser=saxParserFactory.newSAXParser();

            DefaultHandler defaultHandler=new DefaultHandler()
            {
                boolean bSNP=false;
                boolean bCast=false;
                boolean bPosition=false;
                boolean bTitles=false;
                boolean bView=false;
                boolean bDischarge=false;
                Sportsman sportsman;

                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
                {
                    if(qName.equalsIgnoreCase("Sportsman"))
                        sportsman=new Sportsman();
                    if(qName.equalsIgnoreCase("SNP"))
                        bSNP=true;
                    if(qName.equalsIgnoreCase("Cast"))
                        bCast=true;
                    if(qName.equalsIgnoreCase("Position"))
                        bPosition=true;
                    if(qName.equalsIgnoreCase("Titles"))
                        bTitles=true;
                    if(qName.equalsIgnoreCase("View"))
                        bView=true;
                    if(qName.equalsIgnoreCase("Discharge"))
                        bDischarge=true;
                }

                public void endElement(String uri, String localName, String qName) throws SAXException
                {
                    if(qName.equalsIgnoreCase("Sportsman"))
                        menu.getOperations().addSportsman(sportsman);
                }

                public void characters(char ch[], int start, int length)
                {
                    if(bSNP)
                    {
                        sportsman.setSNP(new String(ch, start, length));
                        bSNP=false;
                    }
                    if(bCast)
                    {
                        sportsman.setCast(new String(ch, start, length));
                        bCast=false;
                    }
                    if(bPosition)
                    {
                        sportsman.setPosition(new String(ch, start, length));
                        bPosition=false;
                    }
                    if(bTitles)
                    {
                        sportsman.setTitles(Integer.parseInt(new String(ch, start, length)));
                        bTitles=false;
                    }
                    if(bView)
                    {
                        sportsman.setView(new String(ch, start, length));
                        bView=false;
                    }
                    if(bDischarge)
                    {
                        sportsman.setDischarge(new String(ch, start, length));
                        bDischarge=false;
                    }
                }

            };
            File file=new File(fileName);
            InputStream inputStream=new FileInputStream(file);
            Reader reader=new InputStreamReader(inputStream, "UTF-8");

            InputSource inputSource=new InputSource(reader);
            inputSource.setEncoding("UTF-8");

            saxParser.parse(inputSource, defaultHandler);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
