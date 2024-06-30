
import java.awt.*;
import java.text.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

public class VixScale extends JPanel {

    private JPanel detailPan = new JPanel();

    private JPanel pan1 = new JPanel();
    private JPanel pan1A = new JPanel();
    private JPanel pan1B = new JPanel();

    private JLabel strikeL = new JLabel("Strike Price");
    private JTextField strikeTF = new JTextField();

    private JLabel vixL = new JLabel("India Vix");
    private JTextField vixTF = new JTextField();

    private Border blackline = BorderFactory.createLineBorder(Color.BLACK);

    private JButton getLevelsButton = new JButton("Get Levels");

    private JTextField topTF[] = new JTextField[15];
    private JTextField bottomTF[] = new JTextField[15];

    private double[] bottomLevels = new double[15];

    DecimalFormat df = new DecimalFormat("#");

    public VixScale() {
        setBounds(0, 31, 984, 880);
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        add(createDetailPanel(), BorderLayout.NORTH);
        add(createPanels(), BorderLayout.CENTER);
    }

    private JPanel createDetailPanel() {
        detailPan.setLayout(new FlowLayout());
        detailPan.setBackground(new Color(255, 163, 246));
        detailPan.setBorder(blackline);
    
        strikeL.setFont(new Font("Calibri", Font.BOLD, 18));
        detailPan.add(strikeL);
        
        strikeTF.setFont(new Font("Calibri", Font.BOLD, 18));
        strikeTF.setColumns(10);
        detailPan.add(strikeTF);

        for (int i = 0; i < 3; i++) {
            JLabel seperator = new JLabel("                    ");
            detailPan.add(seperator);
        }
    
        vixL.setFont(new Font("Calibri", Font.BOLD, 18));
        detailPan.add(vixL);
        
        vixTF.setFont(new Font("Calibri", Font.BOLD, 18));
        vixTF.setColumns(10);
        detailPan.add(vixTF);

        for (int i = 0; i < 2; i++) {
            JLabel seperator = new JLabel("                    ");
            detailPan.add(seperator);
        }

        getLevelsButton.setFont(new Font("Arial", Font.BOLD, 16));
        getLevelsButton.addActionListener(a -> {
            List <Double> topLevels = calculateTopLevels(strikeTF.getText(), vixTF.getText());
            for (int i = topLevels.size() - 1; i >= 0; i--) {
                int index = topLevels.size() - 1 - i;
                topTF[index].setText("                 " + df.format(topLevels.get(i)));
            }

            bottomLevels = calculateBottomLevels(strikeTF.getText(), vixTF.getText());
            for(int i=0; i<15; i++){
                bottomTF[i].setText("                 " + df.format(bottomLevels[i]));
            }
        });
        detailPan.add(getLevelsButton);

        return detailPan;
    }

    private JPanel createPanels() {
        pan1.setLayout(new GridLayout(1, 2));

        pan1A.setLayout(new GridLayout(15, 1));
        pan1A.setBackground(new Color(206, 255, 181));
        pan1A.setBorder(blackline);

        for (int i = 0; i < 15; i++) {
            JLabel label = new JLabel("                 TOP Level - " + (15 - i));
            label.setFont(new Font("Calibri", Font.BOLD, 18));
            
            topTF[i] = new JTextField();
            topTF[i].setFont(new Font("Calibri", Font.BOLD, 20));
            topTF[i].setColumns(10);
            topTF[i].setEditable(false);
            
            pan1A.add(label);
            pan1A.add(topTF[i]);
        }
        pan1.add(pan1A);
        
        pan1B.setLayout(new GridLayout(15, 1));
        pan1B.setBackground(new Color(252, 204, 204));
        pan1B.setBorder(blackline);
        for (int i = 0; i < 15; i++) {
            JLabel label = new JLabel("                 BOTTOM Level - " + (i+1));
            label.setFont(new Font("Calibri", Font.BOLD, 18));
            
            bottomTF[i] = new JTextField();
            bottomTF[i].setFont(new Font("Calibri", Font.BOLD, 20));
            bottomTF[i].setColumns(10);
            bottomTF[i].setEditable(false);
            
            pan1B.add(label);
            pan1B.add(bottomTF[i]);
        }
        pan1.add(pan1B);


        return pan1;
    }

    private List<Double> calculateTopLevels(String sp, String v) {
        double strikePrice = 0.0;   double vix = 0.0;
        
        List<Double> res = new ArrayList<>();

        if(sp.equals("") || v.equals("")){
            JOptionPane.showMessageDialog(this, "Please fill both fields", "Error", 0);
            for (int i = 0; i < 15; i++) {
                res.add(0.0);
            }
        }
        else{
            try {
                strikePrice = Double.parseDouble(sp);
                vix = Double.parseDouble(v) / 19.1;
            } 
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Number Format", "Error", 0);
                System.out.print(e.getStackTrace());
            }

            res.add(strikePrice + (strikePrice * vix) / 100);

            for (int i = 1; i < 15; i++) {
                res.add(res.get(i - 1) + (res.get(i - 1) * vix) / 100);
            }
        }
        return res;
    }
    
    private double[] calculateBottomLevels(String sp, String v) {
        double strikePrice = 0.0;    double vix = 0.0;

        double res[] = new double[15];

        if(sp.equals("") || v.equals("")){
            return res;
        }
        else{
            try {
                strikePrice = Double.parseDouble(sp);
                vix = Double.parseDouble(v) / 19.1;
            } 
            catch (NumberFormatException e) {
                System.out.print(e.getStackTrace());
            }

            res[0] = strikePrice - ((strikePrice * vix) / 100);

            for (int i = 1; i < 15; i++) {
                res[i] = res[i-1] - ((res[i-1] * vix) / 100);
            }
        }
        return res;
    }
}
