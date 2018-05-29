package Controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

import Model.Sportsman;
import View.Menu;

public class SaveParser
{
    Menu menu;

    public SaveParser(Menu menu)
    {
        this.menu=menu;
    }

    public boolean saveFile(String fileName)
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();

            Document document=documentBuilder.newDocument();
            Element rooElement=document.createElement("Sportsmen");
            document.appendChild(rooElement);

            for(Sportsman sp: menu.getOperations().getSportsmenList())
            {
                Element sportsman=document.createElement("Sportsman");
                rooElement.appendChild(sportsman);

                Element snp=document.createElement("SNP");
                snp.appendChild(document.createTextNode(sp.getSNP()));
                sportsman.appendChild(snp);

                Element cast=document.createElement("Cast");
                cast.appendChild(document.createTextNode(sp.getCast()));
                sportsman.appendChild(cast);

                Element position=document.createElement("Position");
                position.appendChild(document.createTextNode(sp.getPosition()));
                sportsman.appendChild(position);

                Element titles=document.createElement("Titles");
                titles.appendChild(document.createTextNode(sp.getTitles()));
                sportsman.appendChild(titles);

                Element view=document.createElement("View");
                view.appendChild(document.createTextNode(sp.getView()));
                sportsman.appendChild(view);

                Element discharge=document.createElement("Discharge");
                discharge.appendChild(document.createTextNode(sp.getDischarge()));
                sportsman.appendChild(discharge);
            }

            TransformerFactory transformerFactory=TransformerFactory.newInstance();
            Transformer transformer=transformerFactory.newTransformer();
            DOMSource domSource=new DOMSource(document);
            File file=new File(fileName+".xml");
            StreamResult streamResult=new StreamResult(file);

            transformer.transform(domSource, streamResult);
            return true;
        }
        catch(ParserConfigurationException pce)
        {
            return false;
        }
        catch(TransformerException te)
        {
            return false;
        }
    }
}
