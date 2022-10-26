/*6313131 Teetuch Siribunpitug
  6313176 Nattapong Jitrangsi
  6313172 Chavakan Yimmark*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class setting extends JDialog {

    private JPanel contentpane, radio_panel;
    private MyImageIcon background;
    private ButtonGroup radio_button_group;
    private JRadioButton[] radio;
    private JButton ok_button, ok_button2;
    private JComboBox color_combo, bg_combo;
    private JLabel game_label, difficu_label, cha_color_label, bg_label, jelly_label, timer_label, heart_label, Item_label, dummy;
    private JLabel drawpane, jellypane, timerpane, heartpane;
    private String[] items = {"Very Easy", "Easy", "Normal", "Hard", "Very Hard"};
    private String id = "New Player ", difficult;
    private int intdiff = 0, color = 0, bg = 1, okcount = 0;
    private MyImageIcon L1, L2, L3, L4, L5, E1, E2, E3, E4, E5, E6;
    private MyImageIcon jelly, heart, timer;

    public void setid(String x) {
        id = x;
    };
    public String getid() {
        return id;
    };
 
    
    public setting() {
        setTitle("Game Settings");
        setModal(false);
        setBounds(300, 200, 800, 500);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        contentpane = (JPanel) getContentPane();
        background = new MyImageIcon("resources/Background/Setting_bg.jpg").resize(800, 500);

        drawpane = new JLabel();
        drawpane.setIcon(background);
        drawpane.setLayout(null);

        game_label = new JLabel("Game Settings");
        game_label.setFont(new Font("Tahoma", 1, 18));
        game_label.setBounds(300, 20, 200, 50);

        difficu_label = new JLabel("Difficulty");
        difficu_label.setFont(new Font("Tahoma", 1, 14));
        difficu_label.setBounds(100, 100, 200, 50);

        difficult = new String("Very Easy");
        radio = new JRadioButton[5];
        radio_panel = new JPanel();
        radio_button_group = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            radio[i] = new JRadioButton(items[i].toString(), true);
            radio_button_group.add(radio[i]);
            radio_panel.add(radio[i]);
            radio[i].addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    JRadioButton temp = (JRadioButton) e.getSource();
                    if (temp.isSelected()) {
                        difficult = temp.getText();
                        switch (difficult) {
                            case "Very Easy":
                                intdiff = 0;
                                break;
                            case "Easy":
                                intdiff = 1;
                                break;
                            case "Normal":
                                intdiff = 2;
                                break;
                            case "Hard":
                                intdiff = 3;
                                break;
                            case "Very Hard":
                                intdiff = 4;
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
        }
        radio_panel.setBounds(100, 150, 400, 30);

        cha_color_label = new JLabel("Character Color");
        cha_color_label.setFont(new Font("Tahoma", 1, 14));
        cha_color_label.setBounds(100, 180, 200, 50);

        color_combo = new JComboBox(new String[]{"Orange", "Blue", "Green", "Yellow", "Pink"});
        color_combo.setFont(new Font("Tahoma", 0, 14));
        color_combo.setBounds(100, 230, 100, 30);
        color_combo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                color = color_combo.getSelectedIndex();

            }
        });

        bg_label = new JLabel("Background");
        bg_label.setFont(new Font("Tahoma", 1, 14));
        bg_label.setBounds(100, 260, 200, 50);

        bg_combo = new JComboBox(new String[]{"Background 1", "Background 2"});
        bg_combo.setFont(new Font("Tahoma", 0, 14));
        bg_combo.setBounds(100, 310, 130, 30);
        bg_combo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                bg = bg_combo.getSelectedIndex();
            }
        });

        ok_button = new JButton("OK");
        ok_button.setFont(new Font("Tahoma", 1, 14));
        ok_button.setBounds(330, 365, 70, 30);
        ok_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawpane.remove(game_label);
                drawpane.remove(difficu_label);
                drawpane.remove(radio_panel);
                drawpane.remove(cha_color_label);
                drawpane.remove(color_combo);
                drawpane.remove(bg_label);
                drawpane.remove(bg_combo);
                drawpane.remove(ok_button);
                drawpane.setIcon(null);
                setTitle("Tutorial");
                table(color, bg + 1, intdiff, id);
                validate();
                repaint();
            }
        });

        drawpane.add(game_label);
        drawpane.add(difficu_label);
        drawpane.add(radio_panel);
        drawpane.add(cha_color_label);
        drawpane.add(color_combo);
        drawpane.add(bg_label);
        drawpane.add(bg_combo);
        drawpane.add(ok_button);

        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();
        repaint();

    }

    public void table(int color, int bg, int difficult, String id) {
        //{"Orange", "Blue", "Green", "Yellow", "Pink"}
        switch (color) {
            case 0:
                L1 = new MyImageIcon("resources/Sprite/L1/O_right.png").resize(70, 70);
                L2 = new MyImageIcon("resources/Sprite/L2/O_right.png").resize(70, 70);
                L3 = new MyImageIcon("resources/Sprite/L3/O_right.png").resize(70, 70);
                L4 = new MyImageIcon("resources/Sprite/L4/O_right.png").resize(70, 70);
                L5 = new MyImageIcon("resources/Sprite/L5/O_right.png").resize(70, 70);
                break;
            case 1:
                L1 = new MyImageIcon("resources/Sprite/L1/B_right.png").resize(70, 70);
                L2 = new MyImageIcon("resources/Sprite/L2/B_right.png").resize(70, 70);
                L3 = new MyImageIcon("resources/Sprite/L3/B_right.png").resize(70, 70);
                L4 = new MyImageIcon("resources/Sprite/L4/B_right.png").resize(70, 70);
                L5 = new MyImageIcon("resources/Sprite/L5/B_right.png").resize(70, 70);
                break;
            case 2:
                L1 = new MyImageIcon("resources/Sprite/L1/G_right.png").resize(70, 70);
                L2 = new MyImageIcon("resources/Sprite/L2/G_right.png").resize(70, 70);
                L3 = new MyImageIcon("resources/Sprite/L3/G_right.png").resize(70, 70);
                L4 = new MyImageIcon("resources/Sprite/L4/G_right.png").resize(70, 70);
                L5 = new MyImageIcon("resources/Sprite/L5/G_right.png").resize(70, 70);
                break;
            case 3:
                L1 = new MyImageIcon("resources/Sprite/L1/Y_right.png").resize(70, 70);
                L2 = new MyImageIcon("resources/Sprite/L2/Y_right.png").resize(70, 70);
                L3 = new MyImageIcon("resources/Sprite/L3/Y_right.png").resize(70, 70);
                L4 = new MyImageIcon("resources/Sprite/L4/Y_right.png").resize(70, 70);
                L5 = new MyImageIcon("resources/Sprite/L5/Y_right.png").resize(70, 70);
                break;
            case 4:
                L1 = new MyImageIcon("resources/Sprite/L1/P_right.png").resize(70, 70);
                L2 = new MyImageIcon("resources/Sprite/L2/P_right.png").resize(70, 70);
                L3 = new MyImageIcon("resources/Sprite/L3/P_right.png").resize(70, 70);
                L4 = new MyImageIcon("resources/Sprite/L4/P_right.png").resize(70, 70);
                L5 = new MyImageIcon("resources/Sprite/L5/P_right.png").resize(70, 70);
                break;
            default:
                break;
        }

        E1 = new MyImageIcon("resources/Sprite/fish_1_left.png").resize(70, 70);
        E2 = new MyImageIcon("resources/Sprite/fish_2_left.png").resize(70, 70);
        E3 = new MyImageIcon("resources/Sprite/fish_3_left.png").resize(70, 70);
        E4 = new MyImageIcon("resources/Sprite/fish_4_left.png").resize(70, 70);
        E5 = new MyImageIcon("resources/Sprite/fish_5_left.png").resize(70, 70);
        E6 = new MyImageIcon("");

        timer = new MyImageIcon("resources/Sprite/addTime.png").resize(70, 70);
        jelly = new MyImageIcon("resources/Sprite/jelly.png").resize(120, 120);
        heart = new MyImageIcon("resources/Sprite/heart.png").resize(70, 70);

        timerpane = new JLabel();
        timerpane.setIcon(timer);
        timerpane.setBounds(50, 60, 100, 100);

        jellypane = new JLabel();
        jellypane.setIcon(jelly);
        jellypane.setBounds(20, 160, 100, 100);

        heartpane = new JLabel();
        heartpane.setIcon(heart);
        heartpane.setBounds(50, 270, 100, 100);

        Item_label = new JLabel("Item");
        Item_label.setFont(new Font("Tahoma", 1, 20));
        Item_label.setBounds(50, 0, 200, 100);

        timer_label = new JLabel("+15 Second");
        timer_label.setFont(new Font("Tahoma", 1, 18));
        timer_label.setBounds(250, 60, 200, 100);

        jelly_label = new JLabel("-1 Heart and -10 Score");
        jelly_label.setFont(new Font("Tahoma", 1, 18));
        jelly_label.setBounds(250, 160, 300, 100);

        heart_label = new JLabel("+1 Heart");
        heart_label.setFont(new Font("Tahoma", 1, 18));
        heart_label.setBounds(250, 270, 200, 100);

        dummy = new JLabel("");

        String[] columnNames = {"Your fish", "Fish", "that", "can", "be", "eaten"};
        Object[][] data
                = {
                    {L1, E1, E6, E6, E6, E6},
                    {L2, E1, E2, E6, E6, E6},
                    {L3, E1, E2, E3, E6, E6},
                    {L4, E1, E2, E3, E4, E6},
                    {L5, E1, E2, E3, E4, E5},};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(70);
        table.getTableHeader().setFont(new Font("Tahoma", 1, 14));
        JScrollPane scrollPane = new JScrollPane(table);

        ok_button2 = new JButton("OK");
        ok_button2.setFont(new Font("Tahoma", 1, 14));
        ok_button2.setBounds(355, 400, 70, 30);
        ok_button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (okcount == 0) {
                    contentpane.remove(scrollPane);
                    contentpane.add(timerpane);
                    contentpane.add(jellypane);
                    contentpane.add(heartpane);
                    contentpane.add(Item_label);
                    contentpane.add(timer_label);
                    contentpane.add(jelly_label);
                    contentpane.add(heart_label);
                    contentpane.add(dummy);
                    okcount++;
                } else if (okcount == 1) {
                    Game gogame = new Game(id, difficult, color, bg);
                    dispose();

                }
                validate();
                repaint();
            }
        });

        contentpane.add(ok_button2);
        contentpane.add(scrollPane);
        contentpane.setBackground(new Color(249, 212, 156));
    }
/*
    public static void main(String[] args) {
        new setting();
    }
*/
    
}
