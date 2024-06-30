
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

public class NiftyOperator extends JPanel{

    private JPanel detailPan = new JPanel();

    private JPanel pan1 = new JPanel();
    private JPanel pan1A = new JPanel();
    private JPanel pan1B = new JPanel();

    private JLabel strikeL = new JLabel("Strike Price");
    private JTextField strikeTF = new JTextField();

    ButtonGroup radioGrp = new ButtonGroup();

    private JButton getLevelsButton = new JButton("Get Levels");

    private Border blackline = BorderFactory.createLineBorder(Color.BLACK);

    private JTextField topTF[] = new JTextField[15];
    private JTextField bottomTF[] = new JTextField[15];

    List<Integer> topLevels;
    private int[] bottomLevels = new int[15];

    public NiftyOperator() {
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

        for (int i = 0; i < 2; i++) {
            JLabel seperator = new JLabel("                    ");
            detailPan.add(seperator);
        }
        
        for (int i = 0; i < 2; i++) {
            JLabel seperator = new JLabel("                    ");
            detailPan.add(seperator);
        }

        getLevelsButton.setFont(new Font("Arial", Font.BOLD, 16));
        getLevelsButton.addActionListener(a -> {
            topLevels = calculateTopLevels(strikeTF.getText());
            for (int i = topLevels.size() - 1; i >= 0; i--) {
                int index = topLevels.size() - 1 - i;
                topTF[index].setText("          " + topLevels.get(i) + "      " + isSpecial(topLevels.get(i)));
            }

            bottomLevels = calculateBottomLevels(strikeTF.getText());
            for(int i=0; i<15; i++){
                bottomTF[i].setText("          " + bottomLevels[i] + "      " + isSpecial(bottomLevels[i]));
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

    private int initialLevel(int strike){
        int first2no = Integer.parseInt(String.valueOf(strike).substring(0, 2));
        int last2no = Integer.parseInt(String.valueOf(strike).substring(3, 5));

        int levelOne = 0;

        if(last2no <= first2no){
            levelOne = Integer.parseInt(String.valueOf(strike).substring(0, 3) + first2no);
        }
        else{
            int middleNo = Integer.parseInt(String.valueOf(strike).substring(2, 3)) + 1;
            levelOne = Integer.parseInt(String.valueOf(strike).substring(0, 2) + middleNo + first2no);
        }

        return levelOne;
    }

    private List<Integer> calculateTopLevels(String sp) {
        int strikePrice = 0;
        
        List<Integer> res = new ArrayList<>();

        if(sp.equals("")){
            JOptionPane.showMessageDialog(this, "Please fill both fields", "Error", 0);
            for (int i = 0; i < 15; i++) {
                res.add(0);
            }
        }
        else{
            try {
                strikePrice = (int) Double.parseDouble(sp);
            } 
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Number Format", "Error", 0);
                System.out.print(e.getStackTrace());
            }

            res.add(initialLevel(strikePrice));

            for (int i = 1; i < 15; i++) {
                res.add(initialLevel(strikePrice) + (i * 100));
            }
        }
        return res;
    }
    
    private int[] calculateBottomLevels(String sp) {
        int strikePrice = 0;

        int res[] = new int[15];

        if(sp.equals("")){
            return res;
        }
        else{
            try {
                strikePrice = (int) Double.parseDouble(sp);
            } 
            catch (NumberFormatException e) {
                System.out.print(e.getStackTrace());
            }

            for (int i = 0; i < 15; i++) {
                res[i] = initialLevel(strikePrice) - ((i + 1) * 100);
            }
        }
        return res;
    }

    protected static String isSpecial(int level){
        int sum = 0;

        for (String s : String.valueOf(level).split("")) {
            sum += Integer.parseInt(s);
        }

        if(sum > 9){
            do{
                sum = calcNumberSeries(sum);
            } while(sum > 9);
        }
        return sum == 3 || sum == 6 || sum == 9 ? "Special" : "";
    }

    private static int calcNumberSeries(int sum){
        int temp = 0;
        for (String s : String.valueOf(sum).split("")) {
            temp += Integer.parseInt(s);
        }
        return temp;
    }

}
