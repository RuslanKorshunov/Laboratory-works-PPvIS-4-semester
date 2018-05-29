package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.*;
import Controller.*;

public class Menu extends JFrame
{
    private Table table;
    private JFileChooser jFileChooser;
    private FileNameExtensionFilter filter;
    private Operations operations;

    public Menu(Operations operations)
    {
        this.operations =operations;

        setTitle("Спортсмены");
        setLocation(400,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600,600));

        createJMenuBar();
        createJToolBar();

        table=new Table(this.operations.getSportsmen());
        add(table);

        setVisible(true);

        settingsSaveOpenDialogs();
    }

    private void createJMenuBar()
    {
        JMenuBar menuBar=new JMenuBar();
        menuBar.setBackground(Color.WHITE);

        JMenu menu=new JMenu("Меню");
        JMenuItem open=new JMenuItem("Открыть");
        open.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boolean result=openFileDialog();
            }
        });
        JMenuItem save=new JMenuItem("Сохранить");
        save.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boolean result=saveFileDialog();
            }
        });
        JMenuItem add=new JMenuItem("Добавить");
        add.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new PanelAdd();
            }
        });
        JMenuItem search=new JMenuItem("Поиск");
        search.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new searchInformation();
            }
        });
        JMenuItem delete=new JMenuItem("Удаление");
        delete.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new removeInformation();
            }
        });
        JMenuItem exit=new JMenuItem("Выход");
        exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                saveFileDialog();
                System.exit(0);
            }
        });

        menu.add(open);
        menu.add(save);
        menu.addSeparator();
        menu.add(add);
        menu.add(search);
        menu.add(delete);
        menu.addSeparator();
        menu.add(exit);

        menuBar.add(menu);

        setJMenuBar(menuBar);
    }

    private void createJToolBar()
    {
        JToolBar toolBar=new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(Color.WHITE);

        JButton jbCreate=createJButton("Добавить");
        jbCreate.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new PanelAdd();
            }
        });
        JButton jbSearch=createJButton("Поиск");
        jbSearch.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new searchInformation();
            }
        });
        JButton jbRemove=createJButton("Удалить");
        jbRemove.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new removeInformation();
            }
        });

        toolBar.add(jbCreate);
        toolBar.addSeparator();
        toolBar.add(jbSearch);
        toolBar.addSeparator();
        toolBar.add(jbRemove);

        add(toolBar, BorderLayout.NORTH);
    }

    private JButton createJButton(String name)
    {
        JButton button=new JButton(name);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        return button;
    }

    private boolean saveFileDialog()
    {
        boolean resultOfSave=false;
        jFileChooser.setDialogTitle("Сохранить");
        int dialog=jFileChooser.showSaveDialog(getContentPane());
        if(dialog==JFileChooser.APPROVE_OPTION)
        {
            SaveParser saveParser=new SaveParser(this);
            if(!saveParser.saveFile(jFileChooser.getSelectedFile()+""))
                createErrorMessage("Произошла ошибка сохранения.");
            else
                resultOfSave=true;
        }
        return resultOfSave;
    }

    private boolean openFileDialog()
    {
        boolean resultOfOpen=false;
        int dialog=JOptionPane.showConfirmDialog(getContentPane(),
                                                "Вы хотите сохранить данные?",
                                                "Сохранить",
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE);
        if(dialog==JOptionPane.YES_OPTION)
            if(!saveFileDialog())
                return resultOfOpen;
        operations.clearSportsmen();
        jFileChooser.setDialogTitle("Открыть");
        dialog=jFileChooser.showOpenDialog(null);
        String filePath=jFileChooser.getSelectedFile().getPath();
        if(dialog==JFileChooser.APPROVE_OPTION)
        {
            OpenParser openParser =new OpenParser(this);
            if(!openParser.openFile(filePath))
                createErrorMessage("Произошла ошибка открытия.");
            else
                resultOfOpen=true;
        }
        table.updateTable();
        return resultOfOpen;
    }

    private void createErrorMessage(String message)
    {
        JOptionPane.showMessageDialog(getContentPane(),
                message,
                "Ошибка",
                JOptionPane.ERROR_MESSAGE);
    }

    private void settingsSaveOpenDialogs()
    {
        UIManager.put("FileChooser.saveButtonText", "Сохранить");
        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.fileNameLabelText", "Имя файла:");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Типы файлов:");
        UIManager.put("FileChooser.lookInLabelText", "Директория:");
        UIManager.put("FileChooser.saveInLabelText", "Сохранить:");
        UIManager.put("FileChooser.folderNameLabelText", "Путь директории");
        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
        jFileChooser=new JFileChooser();
        filter=new FileNameExtensionFilter("Файл: .xml","xml");
        jFileChooser.setFileFilter(filter);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public Operations getOperations()
    {
        return operations;
    }

    //////////////////////////////////////////////////
    private class PanelAdd extends JFrame
    {
        private String[] labelNames={"фамилию", "имя", "отчество", "состав", "позицию", "количество титулов", "вид спорта", "разряд"};
        private String[] castList={"основной", "запасной"};
        private String[] dischargeList={"1 юношеский разряд", "2 разряд", "3 разряд", "кмс", "мастер спорта"};
        private JLabel[] labels=new JLabel[8];
        private JComboBox[] comboBoxes={new JComboBox(castList), new JComboBox(dischargeList)};
        private JTextField[] entryFields=new JTextField[8];
        private Sportsman sp=new Sportsman();

        PanelAdd()
        {
            setTitle("Анкета");
            setLocation(500,300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(new Dimension(400,300));
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

            JPanel SNP=new JPanel(new GridLayout(0,2,2,3));
            JPanel otherInf=new JPanel(new GridLayout(0,2, 2, 3));
            JPanel buttonPanel=new JPanel();
            SNP.setAlignmentX(Component.CENTER_ALIGNMENT);
            otherInf.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            Border SNPBorder=BorderFactory.createTitledBorder("ФИО");
            Border otherInfBorder=BorderFactory.createTitledBorder("Другая информация");
            SNP.setBorder(SNPBorder);
            otherInf.setBorder(otherInfBorder);

            for(int i=0; i<8; i++)
            {
                Dimension sizeOfComponents=new Dimension(20,14);
                labels[i]=new JLabel("Введите "+labelNames[i]);
                if(i>=0 && i<=2)
                    SNP.add(labels[i]);
                else
                    otherInf.add(labels[i]);
                if(i!=3 && i!=7)
                {
                    entryFields[i]=new JTextField();
                    entryFields[i].setPreferredSize(sizeOfComponents);
                    if(i>=0 && i<=2)
                        SNP.add(entryFields[i]);
                    else
                        otherInf.add(entryFields[i]);
                }
                else
                    if(i==3)
                    {
                        comboBoxes[0].setPreferredSize(sizeOfComponents);
                        otherInf.add(comboBoxes[0]);
                    }
                    else
                    {
                        comboBoxes[1].setPreferredSize(sizeOfComponents);
                        otherInf.add(comboBoxes[1]);
                    }
            }

            Dimension sizeOfButtons=new Dimension(97,25);
            JButton save=new JButton("Сохранить");
            save.setPreferredSize(sizeOfButtons);
            JButton cancel=new JButton("Отмена");
            cancel.setPreferredSize(sizeOfButtons);
            buttonPanel.add(save);
            save.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    boolean flag=true;
                    for(int i=0; i<8; i++)
                    {
                        if(i!=3 && i!=7 && !entryFields[i].getText().equals(""))
                        {
                            switch(i)
                            {
                                case 0:
                                    sp.setSurname(entryFields[i].getText());
                                    break;
                                case 1:
                                    sp.setName(entryFields[i].getText());
                                    break;
                                case 2:
                                    sp.setPatronymic(entryFields[i].getText());
                                    break;
                                case 3:
                                    sp.setCast(entryFields[i].getText());
                                    break;
                                case 4:
                                    sp.setPosition(entryFields[i].getText());
                                    break;
                                case 5:
                                    sp.setTitles(Integer.parseInt(entryFields[i].getText()));
                                    break;
                                case 6:
                                    sp.setView(entryFields[i].getText());
                                    break;
                                case 7:
                                    sp.setDischarge(entryFields[i].getText());
                                    break;
                            }
                            entryFields[i].setBackground(Color.GREEN);
                        }
                        else
                            if(i!=3 && i!=7)
                            {
                                entryFields[i].setBackground(Color.YELLOW);
                                flag=false;
                            }
                    }
                    if(flag)
                    {
                        sp.setCast((String) comboBoxes[0].getSelectedItem());
                        sp.setDischarge((String) comboBoxes[1].getSelectedItem());
                        operations.addSportsman(sp);
                        table.updateTable();
                        dispose();
                    }
                }
            });
            cancel.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    dispose();
                }
            });
            buttonPanel.add(cancel);

            add(SNP);
            add(otherInf);
            add(buttonPanel);
            setVisible(true);
        }
    }
    private class searchInformation extends JFrame
    {
        private JButton button1;
        private JButton button2;
        private JButton button3;
        private JButton search;
        private JPanel panel;
        private Sportsmen sportsmenSearch;
        private Table tableForSearch;

        searchInformation()
        {
            setTitle("Поиск");
            setLocation(400,100);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(new Dimension(600,600));
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

            createJToolBarSearch();

            sportsmenSearch=new Sportsmen();
            tableForSearch=new Table(sportsmenSearch);
            add(tableForSearch);

            panel=new JPanel();
            panel.setBackground(Color.WHITE);
            add(panel);

            panelForSearchBySNPAndKindOfSport();

            setVisible(true);
        }

        private void createJToolBarSearch()
        {
            JToolBar jToolBarSearch=new JToolBar();
            jToolBarSearch.setFloatable(false);

            button1=createJButton("По ФИО и виду спорта");
            button1.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    panelForSearchBySNPAndKindOfSport();
                }
            });
            button2=createJButton("По количеству титулов");
            button2.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    panelForSearchByNumberOfTitles();
                }
            });
            button3=createJButton("По ФИО и разряду");
            button3.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    panelForSearchBySNPAndDischarge();
                }
            });

            jToolBarSearch.add(button1);
            jToolBarSearch.add(button2);
            jToolBarSearch.add(button3);

            add(jToolBarSearch, Component.LEFT_ALIGNMENT);
        }

        private void panelForSearchBySNPAndKindOfSport()
        {
            panel.removeAll();
            panel.setLayout(new GridLayout(7,1));
            panel.add(new JLabel("Введите ФИО"));

            Dimension size=new Dimension(100, 20);

            JTextField tfSurname=new JTextField();
            tfSurname.setPreferredSize(size);
            panel.add(tfSurname);

            JTextField tfName=new JTextField();
            tfName.setPreferredSize(size);
            panel.add(tfName);

            JTextField tfPatronymic=new JTextField();
            tfPatronymic.setPreferredSize(size);
            panel.add(tfPatronymic);

            panel.add(new JLabel("Введите вид спорта:"));

            JTextField tfKind=new JTextField();
            tfKind.setPreferredSize(size);
            panel.add(tfKind);

            search=new JButton("Поиск");
            search.setPreferredSize(size);
            search.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    boolean flag=true;
                    if(tfSurname.getText().equals(""))
                    {
                        flag=false;
                        tfSurname.setBackground(Color.YELLOW);
                    }
                    else
                        tfSurname.setBackground(Color.WHITE);
                    if(tfName.getText().equals(""))
                    {
                        flag=false;
                        tfName.setBackground(Color.YELLOW);
                    }
                    else
                        tfName.setBackground(Color.WHITE);
                    if(tfPatronymic.getText().equals(""))
                    {
                        flag=false;
                        tfPatronymic.setBackground(Color.YELLOW);
                    }
                    else
                        tfPatronymic.setBackground(Color.WHITE);
                    if(tfKind.getText().equals(""))
                    {
                        flag=false;
                        tfKind.setBackground(Color.YELLOW);
                    }
                    else
                        tfKind.setBackground(Color.WHITE);
                    if(flag)
                    {
                        operations.searchBySNPAndKindOfSport(sportsmenSearch, tfSurname.getText(), tfName.getText(), tfPatronymic.getText(), tfKind.getText());
                        tableForSearch.updateTable();
                    }
                }
            });
            panel.add(search);
            revalidate();
            repaint();
        }

        private void panelForSearchByNumberOfTitles()
        {
            panel.removeAll();
            panel.setLayout(new GridLayout(7,1));
            panel.add(new JLabel("Введите\n диапазон:"));

            Dimension size=new Dimension(100, 20);

            JTextField tfLowerLevel=new JTextField();
            tfLowerLevel.setPreferredSize(size);
            panel.add(tfLowerLevel);

            JTextField tfTopLevel=new JTextField();
            tfTopLevel.setPreferredSize(size);
            panel.add(tfTopLevel);

            search=new JButton("Поиск");
            search.setPreferredSize(size);
            search.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    boolean flag=true;
                    if(tfLowerLevel.getText().equals(""))
                    {
                        flag=false;
                        tfLowerLevel.setBackground(Color.YELLOW);
                    }
                    else
                        tfLowerLevel.setBackground(Color.WHITE);
                    if(tfTopLevel.getText().equals(""))
                    {
                        flag=false;
                        tfTopLevel.setBackground(Color.YELLOW);
                    }
                    else
                        tfTopLevel.setBackground(Color.WHITE);
                    if(flag==false || Integer.parseInt(tfTopLevel.getText())<Integer.parseInt(tfLowerLevel.getText()))
                    {
                        flag=false;
                        tfTopLevel.setBackground(Color.YELLOW);
                        tfLowerLevel.setBackground(Color.YELLOW);

                    }
                    if(flag)
                    {
                        operations.searchByNumberOfTitles(sportsmenSearch, Integer.parseInt(tfLowerLevel.getText()), Integer.parseInt(tfTopLevel.getText()));
                        tableForSearch.updateTable();
                    }
                }
            });
            panel.add(search);
            revalidate();
            repaint();
        }

        private void panelForSearchBySNPAndDischarge()
        {
            panel.removeAll();
            panel.setLayout(new GridLayout(7,1));
            panel.add(new JLabel("Введите ФИО:"));

            Dimension size=new Dimension(100, 20);

            JTextField tfSurname=new JTextField();
            tfSurname.setPreferredSize(size);
            panel.add(tfSurname);

            JTextField tfName=new JTextField();
            tfName.setPreferredSize(size);
            panel.add(tfName);

            JTextField tfPatronymic=new JTextField();
            tfPatronymic.setPreferredSize(size);
            panel.add(tfPatronymic);

            panel.add(new JLabel("Введите разряд:"));

            JTextField tfDischarge=new JTextField();
            tfDischarge.setPreferredSize(size);
            panel.add(tfDischarge);

            search=new JButton("Поиск");
            search.setPreferredSize(size);
            search.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    boolean flag=true;
                    if(tfSurname.getText().equals(""))
                    {
                        flag=false;
                        tfSurname.setBackground(Color.YELLOW);
                    }
                    else
                        tfSurname.setBackground(Color.WHITE);
                    if(tfName.getText().equals(""))
                    {
                        flag=false;
                        tfName.setBackground(Color.YELLOW);
                    }
                    else
                        tfName.setBackground(Color.WHITE);
                    if(tfName.getText().equals(""))
                    {
                        flag=false;
                        tfName.setBackground(Color.YELLOW);
                    }
                    else
                        tfName.setBackground(Color.WHITE);
                    if(tfDischarge.getText().equals(""))
                    {
                        flag=false;
                        tfDischarge.setBackground(Color.YELLOW);
                    }
                    else
                        tfDischarge.setBackground(Color.WHITE);
                    if(flag)
                    {
                        operations.searchBySNPAndDischarge(sportsmenSearch, tfSurname.getText(), tfName.getText(), tfPatronymic.getText(), tfDischarge.getText());
                        tableForSearch.updateTable();
                    }
                }
            });
            panel.add(search);
            revalidate();
            repaint();
        }
    }
    private class removeInformation extends JFrame
    {
        private JButton button1;
        private JButton button2;
        private JButton button3;
        private JButton remove;
        private JPanel panelWithButtons;
        private JPanel panelWithCriteria;

        removeInformation()
        {
            setTitle("Удалить");
            setLocation(400,100);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(new Dimension(600,220));
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

            button1=new JButton("По ФИО и виду спорта");
            button1.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    panelForRemoveBySNPAndKindOfSport();
                }
            });
            button2=new JButton("По количеству титулов");
            button2.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    panelForRemoveByNumberOfTitles();
                }
            });
            button3=new JButton("По ФИО и разряду");
            button3.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    panelForRemoveBySNPAndDischarge();
                }
            });

            panelWithButtons=new JPanel();

            panelWithButtons.add(button1);
            panelWithButtons.add(button2);
            panelWithButtons.add(button3);

            panelWithCriteria=new JPanel();

            add(panelWithButtons);
            add(panelWithCriteria);

            setVisible(true);
        }

        private void panelForRemoveBySNPAndKindOfSport()
        {
            panelWithCriteria.removeAll();
            panelWithCriteria.setLayout(new BoxLayout(panelWithCriteria, BoxLayout.Y_AXIS));
            panelWithCriteria.add(new JLabel("Введите ФИО:"));

            JTextField tfSurname=new JTextField();
            panelWithCriteria.add(tfSurname);

            JTextField tfName=new JTextField();
            panelWithCriteria.add(tfName);

            JTextField tfPatronymic=new JTextField();
            panelWithCriteria.add(tfPatronymic);

            panelWithCriteria.add(new JLabel("Введите вид спорта:"));

            JTextField tfKind=new JTextField();
            panelWithCriteria.add(tfKind);

            remove=new JButton("Удалить");
            remove.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    boolean flag=true;
                    if(tfSurname.getText().equals(""))
                    {
                        flag=false;
                        tfSurname.setBackground(Color.YELLOW);
                    }
                    else
                        tfSurname.setBackground(Color.WHITE);
                    if(tfName.getText().equals(""))
                    {
                        flag=false;
                        tfName.setBackground(Color.YELLOW);
                    }
                    else
                        tfName.setBackground(Color.WHITE);
                    if(tfPatronymic.getText().equals(""))
                    {
                        flag=false;
                        tfPatronymic.setBackground(Color.YELLOW);
                    }
                    else
                        tfPatronymic.setBackground(Color.WHITE);
                    if(tfKind.getText().equals(""))
                    {
                        flag=false;
                        tfKind.setBackground(Color.YELLOW);
                    }
                    else
                        tfKind.setBackground(Color.WHITE);
                    if(flag)
                    {
                        int result= operations.removeBySNPAndKindOfSport(tfSurname.getText(), tfName.getText(), tfPatronymic.getText(), tfKind.getText());
                        showResult(result);
                    }
                }
            });
            panelWithCriteria.add(remove);
            revalidate();
            repaint();
        }

        private void panelForRemoveByNumberOfTitles()
        {
            panelWithCriteria.removeAll();
            panelWithCriteria.setLayout(new BoxLayout(panelWithCriteria, BoxLayout.Y_AXIS));
            panelWithCriteria.add(new JLabel("Введите диапазон:"));

            panelWithCriteria.add(new JLabel("От "));

            JTextField tfLowerLevel=new JTextField();
            panelWithCriteria.add(tfLowerLevel);

            panelWithCriteria.add(new JLabel("До "));

            JTextField tfTopLevel=new JTextField();
            panelWithCriteria.add(tfTopLevel);

            remove=new JButton("Удалить");
            remove.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    boolean flag=true;
                    if(tfLowerLevel.getText().equals(""))
                    {
                        flag=false;
                        tfLowerLevel.setBackground(Color.YELLOW);
                    }
                    else
                        tfLowerLevel.setBackground(Color.WHITE);
                    if(tfTopLevel.getText().equals(""))
                    {
                        flag=false;
                        tfTopLevel.setBackground(Color.YELLOW);
                    }
                    else
                        tfTopLevel.setBackground(Color.WHITE);
                    if(flag==false ||Integer.parseInt(tfTopLevel.getText())<Integer.parseInt(tfLowerLevel.getText()))
                    {
                        flag=false;
                        tfTopLevel.setBackground(Color.YELLOW);
                        tfLowerLevel.setBackground(Color.YELLOW);

                    }
                    if(flag)
                    {
                        int result= operations.removeByNumberOfTitles(Integer.parseInt(tfLowerLevel.getText()), Integer.parseInt(tfTopLevel.getText()));
                        showResult(result);
                    }
                }
            });
            panelWithCriteria.add(remove);
            revalidate();
            repaint();
        }

        private void panelForRemoveBySNPAndDischarge()
        {
            panelWithCriteria.removeAll();
            panelWithCriteria.setLayout(new BoxLayout(panelWithCriteria, BoxLayout.Y_AXIS));
            panelWithCriteria.add(new JLabel("Введите ФИО"));

            JTextField tfSurname=new JTextField();
            panelWithCriteria.add(tfSurname);

            JTextField tfName=new JTextField();
            panelWithCriteria.add(tfName);

            JTextField tfPatronymic=new JTextField();
            panelWithCriteria.add(tfPatronymic);

            panelWithCriteria.add(new JLabel("Введите разряд:"));

            JTextField tfDischarge=new JTextField();
            panelWithCriteria.add(tfDischarge);

            remove=new JButton("Удалить");
            remove.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    boolean flag=true;
                    if(tfSurname.getText().equals(""))
                    {
                        flag=false;
                        tfSurname.setBackground(Color.YELLOW);
                    }
                    else
                        tfSurname.setBackground(Color.WHITE);
                    if(tfName.getText().equals(""))
                    {
                        flag=false;
                        tfName.setBackground(Color.YELLOW);
                    }
                    else
                        tfName.setBackground(Color.WHITE);
                    if(tfPatronymic.getText().equals(""))
                    {
                        flag=false;
                        tfPatronymic.setBackground(Color.YELLOW);
                    }
                    else
                        tfPatronymic.setBackground(Color.WHITE);
                    if(tfDischarge.getText().equals(""))
                    {
                        flag=false;
                        tfDischarge.setBackground(Color.YELLOW);
                    }
                    else
                        tfDischarge.setBackground(Color.WHITE);
                    if(flag)
                    {
                        int result= operations.removeBySNPAndDischarge(tfSurname.getText(), tfName.getText(), tfPatronymic.getText(), tfDischarge.getText());
                        showResult(result);
                    }
                }
            });
            panelWithCriteria.add(remove);
            revalidate();
            repaint();
        }

        private void showResult(int numberOfRemove)
        {
            String message="";
            int messageType=JOptionPane.INFORMATION_MESSAGE;
            if(numberOfRemove!=0)
            {
                message="Было удалено "+numberOfRemove+" записей.";
            }
            else
            {
                message="Записи, соответствующие заданным критериям, не были найдены.";
                messageType=JOptionPane.ERROR_MESSAGE;
            }
            JOptionPane.showMessageDialog(getContentPane(),
                                          message,
                                     "Результат",
                                          messageType);
            dispose();
            table.updateTable();
        }
    }
}