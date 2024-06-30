
import javax.swing.*;
import java.awt.*;

public class NiftyFixedCandle extends JPanel {

    private Font font = new Font("Arial", 1, 18);
    private Color fg = Color.BLACK;
    
    private JRadioButton[] candleColor;
    private JRadioButton[] upDown;
    private JLabel highLowStatusLbl;

    public NiftyFixedCandle() {
        setBounds(140, 120, 700, 700);
        setLayout(null);
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.blue, 3));
    
        JLabel prevCandleColorLbl = new JLabel("Previous Candle Color :");
        prevCandleColorLbl.setBounds(50, 50, 250, 50);
        prevCandleColorLbl.setFont(font);
        prevCandleColorLbl.setForeground(fg);
        add(prevCandleColorLbl);

        candleColor = new JRadioButton[2];
        String radioButtonNames[] = {"Red", "Green"};
        int x = 280;
        for (int i = 0; i < candleColor.length; i++) {
            candleColor[i] = new JRadioButton(radioButtonNames[i]);
            candleColor[i].setBounds(x, 60, 100, 30);
            candleColor[i].setFont(font);
            candleColor[i].setForeground(i == 0 ? Color.red : new Color(0, 171, 6));
            candleColor[i].setBackground(Color.white);
            add(candleColor[i]);
            x += 100;
        }

        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(candleColor[0]);
        buttonGroup1.add(candleColor[1]);

        JLabel todaysCandleStatusLbl =  new JLabel("Today's Candle Status :");
        todaysCandleStatusLbl.setBounds(50, 100, 250, 50);
        todaysCandleStatusLbl.setFont(font);
        todaysCandleStatusLbl.setForeground(fg);
        add(todaysCandleStatusLbl);

        upDown = new JRadioButton[3];
        String names[] = {"Gap Up", "Gap Down", "In Range"};
        int xaxis = 280;
        ButtonGroup buttonGroup2 = new ButtonGroup();
        for (int i = 0; i < upDown.length; i++) {
            upDown[i] = new JRadioButton(names[i]);
            upDown[i].setBounds(xaxis, 110, 120, 30);
            upDown[i].setFont(font);
            upDown[i].setForeground(fg);
            upDown[i].setBackground(Color.white);
            add(upDown[i]);
            xaxis += i==1 ? 150 : 130;
            buttonGroup2.add(upDown[i]);

            upDown[i].addActionListener(a -> {
                updateHighLowStatusLbl();
            });
        }
        
        JLabel priceLbl = new JLabel("Enter Previous Candle Price :");
        priceLbl.setBounds(50, 150, 260, 50);
        priceLbl.setForeground(fg);
        priceLbl.setFont(font);
        add(priceLbl);

        JTextField priceTf = new JTextField(10);
        priceTf.setBounds(320, 160, 150, 30);
        priceTf.setForeground(fg);
        priceTf.setBackground(Color.white);
        priceTf.setFont(font);
        add(priceTf);

        highLowStatusLbl = new JLabel();
        highLowStatusLbl.setBounds(480, 150, 100, 50);
        highLowStatusLbl.setForeground(fg);
        highLowStatusLbl.setFont(new Font("arial", 3, 17));
        add(highLowStatusLbl);
        
        JLabel todaysCandelHeading = new JLabel("Today's Important Candle For Nifty 50 Is");
        todaysCandelHeading.setBounds(200, 310, 400, 50);
        todaysCandelHeading.setForeground(fg);
        todaysCandelHeading.setFont(new Font(Font.SANS_SERIF, 3, 16));
        add(todaysCandelHeading);

        JTextField answerField = new JTextField();
        answerField.setBounds(250, 400, 200, 200);
        answerField.setFont(new Font("ArialBlack", 1, 80));
        answerField.setBackground(Color.white);
        answerField.setForeground(fg);
        answerField.setEditable(false);
        answerField.setHorizontalAlignment(SwingConstants.CENTER);
        add(answerField);

        JButton getCandle = new JButton("Get Important Number of Candle");
        getCandle.setBounds(150, 250, 400, 30);
        getCandle.setForeground(fg);
        getCandle.setBackground(Color.yellow);
        getCandle.setFont(font);
        getCandle.addActionListener(a -> {
            answerField.setText(getCandleFunc(priceTf.getText()));
        });
        add(getCandle);

    }

    private String getCandleFunc(String levelStr) {
        int sum = 0;
        int level;

        if(levelStr.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Price", "Error", 0);
        }
        else{
            try { 

                level = Integer.parseInt(levelStr);

                for (String s : String.valueOf(level).split("")) {
                    sum += Integer.parseInt(s);
                }
        
                if(sum > 9){
                    do{
                        sum = calcNumberSeries(sum);
                    } while(sum > 9);
                }
            }
            catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Invalid Number Format", "Error", 0);
            }
        }
        
        return String.valueOf(sum);
    }

    private static int calcNumberSeries(int n){
        int temp = 0;
        for (String s : String.valueOf(n).split("")) {
            temp += Integer.parseInt(s);
        }
        return temp;
    }

    private void updateHighLowStatusLbl() {
        JRadioButton selectedColor = null;
        JRadioButton selectedUpDown = null;
    
        for (JRadioButton colorButton : candleColor) {
            if (colorButton.isSelected()) {
                selectedColor = colorButton;
                break;
            }
        }
    
        for (JRadioButton upDownButton : upDown) {
            if (upDownButton.isSelected()) {
                selectedUpDown = upDownButton;
                break;
            }
        }

        //candleColor[0] = Red, candleColor[1] = Green
        //upDown[0] = Up, upDown[1] = Down, upDown[2] = Range,
    
        if (selectedColor != null && selectedUpDown != null) {
            if (selectedColor == candleColor[0]) {
                if (selectedUpDown == upDown[0]) {
                    highLowStatusLbl.setText("Low");
                } else if (selectedUpDown == upDown[1] || selectedUpDown == upDown[2]) {
                    highLowStatusLbl.setText("High");
                }
            } else if (selectedColor == candleColor[1]) {
                if (selectedUpDown == upDown[0] || selectedUpDown == upDown[2]) {
                    highLowStatusLbl.setText("Low");
                } else if (selectedUpDown == upDown[1]) {
                    highLowStatusLbl.setText("High");
                }
            }
        }
    }
    
}
