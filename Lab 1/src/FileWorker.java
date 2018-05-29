import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;

public class FileWorker
{
    TextFieldOperations textFieldOperations;
    Main main;
    JFileChooser fileChooser;
    FileNameExtensionFilter filter;

    FileWorker(Main main)
    {
        this.main=main;
        this.textFieldOperations=main.getTextField().getTextFieldOperations();
        createFileChooser();
    }

    private void createFileChooser()
    {
        UIManager.put("FileChooser.openButtonText", "Открыть");
        fileChooser=new JFileChooser();
        filter=new FileNameExtensionFilter("Файл: .txt, .xml","txt", "xml");
        fileChooser.setFileFilter(filter);
    }

    public void createFile()
    {
        int START_Y_COORDINATE=6;
        int answer=JOptionPane.showConfirmDialog(null,
                "Вы хотите сохранить измения в файле?",
                "Сохранить",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(answer==JOptionPane.YES_OPTION)
            saveFile();
        textFieldOperations.clearLines();
        textFieldOperations.getLines().add(new Line(START_Y_COORDINATE));
        textFieldOperations.setIndexOfActiveLine(0);
        textFieldOperations.setIndexOfActiveCh(-1);
    }

    public void openFile()
    {
        fileChooser.setDialogTitle("Открыть");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result=fileChooser.showOpenDialog(null);
        String filePath=fileChooser.getSelectedFile().getPath();
        if(result==JFileChooser.APPROVE_OPTION)
        {
            String extension=getExtension(fileChooser.getSelectedFile().getName());
            if(extension.equals("txt"))
                openTXTFile(filePath);
            if(extension.equals("xml"))
                openXMLFile(filePath);
        }
    }

    public void saveFile()
    {
        fileChooser.setDialogTitle("Сохранить");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result=fileChooser.showSaveDialog(null);
        if(result==JFileChooser.APPROVE_OPTION)
            try
            {
                XMLOutputFactory output=XMLOutputFactory.newInstance();
                XMLStreamWriter writer=output.createXMLStreamWriter
                        (new FileWriter(fileChooser.getSelectedFile()+".xml"));

                writer.writeStartDocument("UTF-8", "1.0");
                writer.writeStartElement("TextDoc");

                for(Line line:textFieldOperations.getLines())
                {
                    writer.writeStartElement("Line");
                    writer.writeAttribute("MaxHeight", Integer.toString(line.getMaxHeight()));
                    for(Char ch:line.getChars())
                    {
                        writer.writeStartElement("Char");
                        writer.writeAttribute("FontStyle", Integer.toString(ch.getFontStyle()));
                        writer.writeAttribute("FontSize", Integer.toString(ch.getFontSize()));
                        writer.writeAttribute("FontName", ch.getFontName());
                        writer.writeCharacters(ch.getStringCh());
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                }
                writer.writeEndElement();
                writer.writeEndDocument();
                writer.flush();
            }
            catch(Exception e)
            {

            }
    }

    private String getExtension(String name)
    {
        int index=name.indexOf(".");
        return index==-1? null:name.substring(index+1);
    }

    private void openTXTFile(String filePath)
    {
        try(BufferedReader reader =new BufferedReader(new FileReader(filePath));)
        {
            textFieldOperations.clearLines();
            String line;
            while((line=reader.readLine())!=null)
            {
                Line newLine;
                if(textFieldOperations.getLines().isEmpty())
                    newLine=new Line(6);
                else
                    newLine=new Line(textFieldOperations.getLines().get(textFieldOperations.getLines().size()-1).getCoordinateY());
                for(int i=0;i<line.length();i++)
                {
                    Char ch=new Char(line.charAt(i));
                    newLine.add(ch);
                }
                textFieldOperations.getLines().add(newLine);
            }
            textFieldOperations.setIndexOfActiveLine(0);
            textFieldOperations.setIndexOfActiveCh(-1);
        }
        catch(Exception e)
        {
            System.out.println(1);
        }
    }

    private void openXMLFile(String filePath)
    {
        try
        {
            Line line=new Line();
            textFieldOperations.clearLines();
            XMLStreamReader reader= XMLInputFactory.newInstance().createXMLStreamReader(filePath, new FileInputStream(filePath));
            while(reader.hasNext())
            {
                reader.next();
                if(reader.isStartElement())
                {
                    if(reader.getLocalName().equals("Line"))
                    {
                        int maxHeigth=Integer.parseInt(reader.getAttributeValue(null,"MaxHeight"));
                        line=new Line();
                        line.setMaxHeight(maxHeigth);
                    }
                    else if(reader.getLocalName().equals("Char"))
                    {
                        int fontStyle=Integer.parseInt(reader.getAttributeValue(null,"FontStyle"));
                        int fontSize=Integer.parseInt(reader.getAttributeValue(null, "FontSize"));
                        String fontName=reader.getAttributeValue(null, "FontName");
                        reader.next();
                        Char ch=new Char(reader.getText().charAt(0));
                        ch.setFontStyle(fontStyle);
                        ch.setFontSize(fontSize);
                        ch.setFontName(fontName);
                        line.add(ch);
                    }
                }
                else if(reader.isEndElement())
                    if(reader.getLocalName().equals("Line"))
                        textFieldOperations.getLines().add(line);
            }
            textFieldOperations.setIndexOfActiveLine(0);
            textFieldOperations.setIndexOfActiveCh(-1);
        }
        catch(Exception e)
        {}
    }
}