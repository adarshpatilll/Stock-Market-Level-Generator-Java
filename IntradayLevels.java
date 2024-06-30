
import java.awt.*;
import javax.swing.*;

public class IntradayLevels extends JFrame {
    
    private VixScale vs = new VixScale();
    private BankNiftyOperator bnop = new BankNiftyOperator();
    private NiftyOperator nop = new NiftyOperator();
    private CheckPrice cp = new CheckPrice(); 
    private NiftyFixedCandle nfc = new NiftyFixedCandle();

    private JLabel gifLabel;

    private IntradayLevels() {

        header();
        
        ImageIcon gifIcon = new ImageIcon("images/bearbullfight.gif");
        gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(0, 30, 985, 940);
        add(gifLabel);
        
        setTitle("Intraday Levels");
        setBounds(200, 40, 1000, 970);
        getContentPane().setBackground(Color.white);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void header() {
        JPanel topBar = new JPanel();
        topBar.setBounds(0, 0, 985, 30);
        topBar.setLayout(new GridLayout());

        JButton btn[] = new JButton[5];
        
        String btnNames[] = {"VIX Scale", "Bank Nifty Operator", "Nifty Operator", "Price Checker", "Nifty Fixed Candle"};
        for (int i=0; i<btn.length; i++) {
            btn[i] = new JButton();
            btn[i].setBackground(Color.BLACK);
            btn[i].setForeground(Color.YELLOW);
            btn[i].setFont(new Font("Arial", 1, 16));
            btn[i].setText(btnNames[i]);
            topBar.add(btn[i]);
        }
        
        JMenuBar mb = new JMenuBar();

        JMenuItem onTop = new JMenuItem("Always On Top");
        onTop.setForeground(Color.RED);
        onTop.setFont(new Font("Arial", 1, 14));

        onTop.addActionListener(a -> {
            setAlwaysOnTop(!isAlwaysOnTop());
            if(isAlwaysOnTop()){
                onTop.setForeground(new Color(3, 184, 0));
            }
            else{
                onTop.setForeground(Color.RED);
            }
        });

        mb.add(onTop); 
        setJMenuBar(mb);

        btn[0].addActionListener(a -> showPanel(vs));
        btn[1].addActionListener(a -> showPanel(bnop));
        btn[2].addActionListener(a -> showPanel(nop));
        btn[3].addActionListener(a -> showPanel(cp));
        btn[4].addActionListener(a -> showPanel(nfc));

        add(topBar);
    }

    private void showPanel(JPanel panel) {
        panel.setVisible(true);
        gifLabel.setVisible(false);

        hideOtherPanels(panel);

        add(panel);
        revalidate();
        repaint();
    }

    private void hideOtherPanels(JPanel currentPanel) {
        JPanel[] panels = {vs, bnop, nop, cp, nfc};
        for (JPanel panel : panels) {
            if (panel != currentPanel) {
                panel.setVisible(false);
            }
        }
    }

    public static void main(String[] args) {
        new IntradayLevels();
    }
}