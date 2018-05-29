import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class Main
{
    final private static ImageIcon CreateIcon = new ImageIcon("images/create_small.png");
    final private static ImageIcon OpenIcon = new ImageIcon("images/open_small.png");
    final private static ImageIcon SaveIcon = new ImageIcon("images/save_small.png");
    final private static ImageIcon CloseIcon = new ImageIcon("images/close_small.png");
    final private static ImageIcon CutIcon = new ImageIcon("images/cut_small.png");
    final private static ImageIcon CopyIcon = new ImageIcon("images/copy_small.png");
    final private static ImageIcon PasteIcon = new ImageIcon("images/paste_small.png");
    final private static ImageIcon TypeOfFontIcon = new ImageIcon("images/T.png");
    final private static ImageIcon SizeOfFontIcon = new ImageIcon("images/size.png");
    final private static ImageIcon BoldIcon = new ImageIcon("images/bold.png");
    final private static ImageIcon CursiveIcon = new ImageIcon("images/cursive.png");

    private JFrame frame;
    private TextField textField;
    private JScrollPane scrollPane;
    private FileWorker fileWorker;

    ChangeStyleAction bold=new ChangeStyleAction("Жирный", BoldIcon, "Применение жирного начертания к тексту.");
    ChangeStyleAction cursive=new ChangeStyleAction("Курсив", CursiveIcon, "Применение курсивного начертания к тексту.");

    public JScrollPane getScrollPane()
    {
        return scrollPane;
    }

    Main()
    {
        frame=new JFrame("Текстовый редактор");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setFocusable(true);

        JMenuBar MenuBar=createMenuBar();
        frame.setJMenuBar(MenuBar);

        JToolBar ToolBar=createToolBar();
        frame.add(ToolBar,BorderLayout.NORTH);

        frame.add(new TextArea(), BorderLayout.CENTER);

        textField=new TextField();
        textField.requestFocusInWindow();

        scrollPane=new JScrollPane(textField, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent evt) {
                updateWindow();
            }
        });
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent evt) {
                updateWindow();
            }
        });
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.addKeyListener(new TextKeyListener(this));
        frame.addKeyListener(new DeleteKeyListener(this));
        frame.addKeyListener(new CarriageKeyListener(this));
        frame.addKeyListener(new CtrlKeyListener(this));
        textField.addMouseListener(new MouseListener(this));
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        fileWorker=new FileWorker(this);
    }

    private JMenuBar createMenuBar()
    {
        JMenuBar menuBar=new JMenuBar();
        menuBar.setBackground(new Color(44,137,99));


        JMenu file=new JMenu("Файл");
        file.setForeground(Color.WHITE);
        JMenuItem create=new JMenuItem("Создать", CreateIcon);
        create.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fileWorker.createFile();
                updateWindow();
            }
        });
        JMenuItem open=new JMenuItem("Открыть", OpenIcon);
        open.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fileWorker.openFile();
                updateWindow();
            }
        });
        JMenuItem save=new JMenuItem("Сохранить", SaveIcon);
        save.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fileWorker.saveFile();
                updateWindow();
            }
        });
        JMenuItem exit=new JMenuItem("Закрыть", CloseIcon);
        exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fileWorker.createFile();
                System.exit(0);
            }
        });

        file.add(create);
        file.add(open);
        file.add(save);
        file.addSeparator();
        file.add(exit);
        file.setEnabled(true);
        menuBar.add(file);

        JMenu instruments=new JMenu("Инструменты");
        instruments.setForeground(Color.WHITE);
        JMenuItem cut=new JMenuItem("Вырезать",CutIcon);
        cut.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textField.getTextFieldOperations().cutText();
            }
        });
        JMenuItem copy=new JMenuItem("Копировать", CopyIcon);
        copy.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textField.getTextFieldOperations().copyText();
            }
        });
        JMenuItem paste=new JMenuItem("Вставить", PasteIcon);
        paste.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textField.getTextFieldOperations().pasteText();
            }
        });
        instruments.add(cut);
        instruments.add(copy);
        instruments.add(paste);
        instruments.setEnabled(true);
        menuBar.add(instruments);

        JMenu font=new JMenu("Шрифт");
        font.setForeground(Color.WHITE);
        font.add(bold);
        font.add(cursive);
        font.setEnabled(true);
        menuBar.add(font);

        return menuBar;
    }

    private JToolBar createToolBar()
    {
        JToolBar ToolBar=new JToolBar("Панель инструментов", JToolBar.HORIZONTAL);
        ToolBar.setFloatable(false);
        ToolBar.setBackground(new Color(44,137,99));

        JButton jbCreate=new JButton(CreateIcon);
        jbCreate.setBackground(new Color(44,137,99));
        jbCreate.setBorderPainted(false);
        jbCreate.setFocusPainted(false);
        JButton jbOpen=new JButton(OpenIcon);
        jbOpen.setBackground(new Color(44,137,99));
        jbOpen.setBorderPainted(false);
        jbOpen.setFocusPainted(false);
        JButton jbSave=new JButton(SaveIcon);
        jbSave.setBackground(new Color(44,137,99));
        jbSave.setBorderPainted(false);
        jbSave.setFocusPainted(false);
        JButton jbCut=new JButton(CutIcon);
        jbCut.setBackground(new Color(44,137,99));
        jbCut.setBorderPainted(false);
        jbCut.setFocusPainted(false);
        jbCut.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textField.getTextFieldOperations().cutText();
            }
        });
        JButton jbCopy=new JButton(CopyIcon);
        jbCopy.setBackground(new Color(44,137,99));
        jbCopy.setBorderPainted(false);
        jbCopy.setFocusPainted(false);
        jbCopy.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textField.getTextFieldOperations().copyText();
            }
        });
        JButton jbPaste=new JButton(PasteIcon);
        jbPaste.setBackground(new Color(44,137,99));
        jbPaste.setBorderPainted(false);
        jbPaste.setFocusPainted(false);
        jbPaste.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textField.getTextFieldOperations().pasteText();
            }
        });
        JButton jbBold=new JButton();
        jbBold.setBackground(new Color(44,137,99));
        jbBold.setBorderPainted(false);
        jbBold.setFocusPainted(false);
        jbBold.setAction(bold);
        JButton jbCursive=new JButton();
        jbCursive.setBackground(new Color(44,137,99));
        jbCursive.setBorderPainted(false);
        jbCursive.setFocusPainted(false);
        jbCursive.setAction(cursive);

        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        String listOfFontStyle[]=ge.getAvailableFontFamilyNames();
        JComboBox<String> jcmFontNames=new JComboBox<>(listOfFontStyle);
        jcmFontNames.setBackground(new Color(142,192,171));
        jcmFontNames.setPreferredSize(new Dimension(30,30));
        jcmFontNames.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String newFontName=(String)jcmFontNames.getSelectedItem();
                textField.getTextFieldOperations().changeFontName(newFontName);
                updateWindow();
            }
        });


        Integer listOfFontSize[]=new Integer[120];
        for(int i=0;i<listOfFontSize.length;i++)
            listOfFontSize[i]=i+1;
        JComboBox<Integer> jcmFontSize=new JComboBox<Integer>(listOfFontSize);
        jcmFontSize.setBackground(new Color(142,192,171));
        jcmFontSize.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Integer newFontSize=(Integer)jcmFontSize.getSelectedItem();
                textField.getTextFieldOperations().changeFontSize(newFontSize.intValue());
                updateWindow();
            }
        });


        jbCreate.setToolTipText("Создать");
        jbOpen.setToolTipText("Открыть");
        jbSave.setToolTipText("Сохранить");
        //добавить для всех остальных

        ToolBar.add(jbCreate);
        ToolBar.add(jbOpen);
        ToolBar.add(jbSave);
        ToolBar.addSeparator();
        ToolBar.add(jbCut);
        ToolBar.add(jbCopy);
        ToolBar.add(jbPaste);
        ToolBar.addSeparator();
        ToolBar.add(jbBold);
        ToolBar.add(jbCursive);
        ToolBar.addSeparator();
        ToolBar.add(jcmFontNames);
        ToolBar.addSeparator();
        ToolBar.add(jcmFontSize);


        /*jbCreate.setEnabled(false);
        jbOpen.setEnabled(false);
        jbSave.setEnabled(false);
        jbCut.setEnabled(false);
        jbCopy.setEnabled(false);
        jbPaste.setEnabled(false);
        jbBold.setEnabled(false);
        jbCursive.setEnabled(false);*/

        return ToolBar;
    }

    public TextField getTextField()
    {
        return textField;
    }

    public void updateWindow()
    {
        scrollPane.repaint();
        scrollPane.revalidate();
    }

    public static void main(String args[])
    {
        new Main();

    }

    private class ChangeStyleAction extends AbstractAction
    {
        public ChangeStyleAction(String name, Icon icon/*, int mnem, int accel*/, String descr)
        {
            putValue(NAME, name);
            putValue(SMALL_ICON, icon);
            putValue(SHORT_DESCRIPTION, descr);
        }

        public void actionPerformed(ActionEvent ae)
        {
            String command=ae.getActionCommand();
            if(command.equals("Жирный"))
                textField.getTextFieldOperations().changeStyle(1);
            else if(command.equals("Курсив"))
                textField.getTextFieldOperations().changeStyle(2);
            updateWindow();
        }
    }

    private class ChangeTextAction extends AbstractAction
    {
        public ChangeTextAction(String name, Icon icon, String descr)
        {
            putValue(NAME, name);
            putValue(SMALL_ICON, icon);
            putValue(SHORT_DESCRIPTION, descr);
        }

        public void actionPerformed(ActionEvent ae)
        {
            String command=ae.getActionCommand();
            if(command.equals("Копировать"))
                textField.getTextFieldOperations().copyText();
            else if(command.equals("Вырезать"))
                textField.getTextFieldOperations().cutText();
            else
                textField.getTextFieldOperations().pasteText();
            updateWindow();
        }
    }

    public JFrame getFrame()
    {
        return frame;
    }
}