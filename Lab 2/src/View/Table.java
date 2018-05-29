package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import Model.*;

public class Table extends JPanel
{
    private JTable jTable;
    private Sportsmen sportsmen;
    private int pageNumber=0;
    private int recordNumber=21;
    private LinkedList<Sportsman> listOfSportsmen;
    private ImageIcon forwardIcon=new ImageIcon("images/forward.png");
    private ImageIcon backwardIcon=new ImageIcon("images/backward.png");
    private ImageIcon firstPageIcon=new ImageIcon("images/first_page.png");
    private ImageIcon lastPageIcon=new ImageIcon("images/last_page.png");
    private JPanel paging;
    private JPanel infPanel;
    private JLabel totalNumberOfSportsmen;
    private SportsmenTableModel stm;
    private JTextField page;

    public Table(Sportsmen sportsmen)
    {
        listOfSportsmen=new LinkedList<>();
        this.sportsmen=sportsmen;

        setLayout(new BorderLayout());

        createInfPanel();
        createJTable();
        createPaging();
    }

    private void createJTable()
    {
        jTable=new JTable();
        jTable.setRowHeight(20);

        listOfSportsmen=new LinkedList<>();
        stm=new SportsmenTableModel(listOfSportsmen, recordNumber);

        toGoToNewPage(0);

        JScrollPane jScrollPane=new JScrollPane(jTable);

        add(jScrollPane, BorderLayout.CENTER);
    }

    private void createPaging()
    {
        paging=new JPanel();
        paging.setBackground(Color.WHITE);

        Dimension size=new Dimension(20,20);

        JButton firstPage=createJButton(firstPageIcon);
        firstPage.setPreferredSize(size);
        firstPage.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                toGoToNewPage(0);
                page.setText(Integer.toString(pageNumber+1));
            }
        });
        JButton backward=createJButton(backwardIcon);
        backward.setPreferredSize(size);
        backward.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pagingToLeft();
            }
        });
        JButton forward=createJButton(forwardIcon);
        forward.setPreferredSize(size);
        forward.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pagingToRight();
            }
        });
        JButton lastPage=createJButton(lastPageIcon);
        lastPage.setPreferredSize(size);
        lastPage.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                toGoToNewPage(getTotalNumberOfPages()-1);
                page.setText(Integer.toString(pageNumber+1));
            }
        });
        page=new JTextField();
        page.setPreferredSize(size);
        page.setText(Integer.toString(pageNumber+1));
        page.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                    try
                    {
                        int number=Integer.parseInt(page.getText())-1;
                        if(number<getTotalNumberOfPages())
                            toGoToNewPage(number);
                    }
                    catch(Exception ex) { }
                    finally
                    {
                        page.setText(Integer.toString(pageNumber+1));
                    }
            }
        });
        JLabel numberOfPages=new JLabel("/"+getTotalNumberOfPages());

        paging.add(firstPage);
        paging.add(backward);
        paging.add(page);
        paging.add(numberOfPages);
        paging.add(forward);
        paging.add(lastPage);

        add(paging, BorderLayout.SOUTH);
    }

    private void createInfPanel()
    {
        infPanel=new JPanel();
        infPanel.setBackground(Color.WHITE);

        JLabel numberOfSportsmen=new JLabel("Число записей на странице:");
        totalNumberOfSportsmen=new JLabel("Общее число записей: "+sportsmen.getSize());

        JTextField choiceNumberOfSportsmen=new JTextField();
        choiceNumberOfSportsmen.setPreferredSize(new Dimension(20,20));
        choiceNumberOfSportsmen.setText(Integer.toString(recordNumber));
        choiceNumberOfSportsmen.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                    try
                    {
                        int number=Integer.parseInt(choiceNumberOfSportsmen.getText());
                        if(number>0 && number<=21)
                        {
                            recordNumber=number;
                            stm.setRowCount(number);
                            updateTable();
                        }
                    }
                    catch(Exception ex) { }
                    finally
                    {
                        toGoToNewPage(0);
                        choiceNumberOfSportsmen.setText(Integer.toString(recordNumber));
                    }
            }
        });

        infPanel.add(numberOfSportsmen);
        infPanel.add(choiceNumberOfSportsmen);
        infPanel.add(totalNumberOfSportsmen);

        add(infPanel, BorderLayout.NORTH);
    }

    private int getTotalNumberOfPages()
    {
        int number=(int) Math.floor(sportsmen.getSize()/recordNumber);
        if(sportsmen.getSize()%recordNumber!=0)
            number++;
        if(number==0)
            return 1;
        return number;
    }

    private void pagingToRight()
    {
        boolean answer=(pageNumber+1)*recordNumber<sportsmen.getSize()?
                true:false;
        if(answer)
        {
            pageNumber++;
            int lastIndex=sportsmen.getSize()-(pageNumber)*recordNumber<recordNumber?
                    sportsmen.getSize():(pageNumber+1)*recordNumber;
            for(int i=pageNumber*recordNumber; i<lastIndex;i++)
                listOfSportsmen.add(sportsmen.get(i));
            stm=new SportsmenTableModel(listOfSportsmen, recordNumber);
            jTable.setModel(stm);
            listOfSportsmen.clear();
            page.setText(Integer.toString(pageNumber+1));
        }
    }

    private void pagingToLeft()
    {
        if(pageNumber>0)
        {
            pageNumber--;
            for(int i=pageNumber*recordNumber; i<(pageNumber+1)*recordNumber; i++)
                listOfSportsmen.add(sportsmen.get(i));
            stm=new SportsmenTableModel(listOfSportsmen, recordNumber);
            jTable.setModel(stm);
            listOfSportsmen.clear();
            page.setText(Integer.toString(pageNumber+1));
        }
    }

    private void toGoToNewPage(int pageNumber)
    {
        boolean answer=(pageNumber-1)*recordNumber<sportsmen.getSize()?
                true:false;
        if(answer)
        {
            int lastIndex=sportsmen.getSize()-(pageNumber)*recordNumber<recordNumber?
                    sportsmen.getSize():(pageNumber+1)*recordNumber;
            for(int i=pageNumber*recordNumber; i<lastIndex;i++)
                listOfSportsmen.add(sportsmen.get(i));
            stm=new SportsmenTableModel(listOfSportsmen, recordNumber);
            jTable.setModel(stm);
            listOfSportsmen.clear();
            this.pageNumber=pageNumber;
        }
    }

    private JButton createJButton(ImageIcon icon)
    {
        JButton button=new JButton(icon);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        return button;
    }

    public void updateTable()
    {
        removeAll();
        updateUI();
        createInfPanel();
        createJTable();
        createPaging();
        revalidate();
        repaint();
    }
}
