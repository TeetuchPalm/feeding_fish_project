/*6313131 Teetuch Siribunpitug
  6313176 Nattapong Jitrangsi
  6313172 Chavakan Yimmark*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.sampled.FloatControl;
import javax.swing.table.DefaultTableModel;

class Main extends JFrame {

    private String username, sortby, nowbutton;
    private JPanel contentpane, passwordpane, dialogpane;
    private JLabel drawpane, welcome, user, login, fText, fspeak, fishin, SortbyLabel;
    private MyImageIcon indoorImg;
    private JTextField passbox;
    private JButton  goButton, start, score, exist, backlogin;
    private JCheckBox sound;
    private JDialog highscore;
    private Main menu;
    private JToggleButton[] easyhard;
    private ButtonGroup Tgroup;
    private String[] items = {"VeryEasy", "Easy", "Normal", "Hard", "VeryHard"}, musiclist = {"Ocean", "Feeding Frenzy", "Mysterious"};
    private DefaultListModel listmodel;
    private JTextArea textscore;
    private JList list;
    private MySoundEffects soundbackground;
    private JSpinner selectmusic;
    private JSlider volume;
    private int song;
    
    ;
    public Main() {
        menu = this;
        setTitle("Feeding fish");
        setBounds(100, 100, 1300, 700);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());
        indoorImg = new MyImageIcon("resources/Background/sea.jpg").resize(1300, 700);

        drawpane = new JLabel();
        drawpane.setIcon(indoorImg);
        drawpane.setLayout(null);

        JLabel feedLabel = new JLabel(new MyImageIcon("resources/Text/feed.png").resize(600, 150));
        feedLabel.setBounds(345, 50, 600, 150);
        passwordpane = new JPanel();
        passwordpane.setBounds(400, 300, 500, 200);
        passwordpane.setLayout(null);
        passwordpane.setBackground(new Color(186, 241, 94));

        passbox = new JTextField(10);
        passbox.setText("New Player");
        passbox.setBounds(250, 60, 200, 50);
        passbox.setFont(new Font("Tahoma", Font.BOLD, 30));
        welcome = new JLabel("Welcome");
        welcome.setBounds(200, 0, 200, 50);
        welcome.setFont(new Font("Tahoma", Font.BOLD, 30));

        user = new JLabel("Who are you");
        user.setBounds(50, 55, 200, 50);
        user.setFont(new Font("Tahoma", Font.BOLD, 30));
        soundbackground = new MySoundEffects("resources/Sound/Ocean.wav");
        goButton = new JButton("Enter");
        goButton.setBounds(200, 125, 100, 50);
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                drawpane.remove(passwordpane);
                username = new String(passbox.getText());
                System.out.printf("%s", username);
                passbox.setText("");
                
                mainmenu();
                validate();
                repaint();
            }
        });
        passwordpane.add(goButton);
        passwordpane.add(welcome);
        passwordpane.add(user);
        passwordpane.add(passbox);

        drawpane.add(passwordpane);
        drawpane.add(feedLabel);
        
        
        contentpane.add(drawpane, BorderLayout.CENTER);
        validate();

    }

    public void mainmenu() {
        start = new JButton("Start");
        start.setBounds(500, 200, 300, 100);
        start.setBackground(new Color(186, 241, 94));
        start.setFont(new Font("Tahoma", Font.BOLD, 42));
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                setting b = new setting();
                b.setid(username);
                
            }
            
        });
                
                
                /*addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
                game_setting_project_213 b = new game_setting_project_213();
            });*/
        fishin = new JLabel(new MyImageIcon("resources/Sprite/fishin.png").resize(300, 250));
        fishin.setBounds(200,330,300,250);
        fishin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                
                fspeak.setText("<html>6313131 Teetuch Siribunpitug<br>6313176 Nattapong Jitrangsi<br>6313172 Chavakan Yimmark</html>"
                );
                validate();
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                
                fspeak.setText("<html>Use Click and Hold <br>your mouse to me!!</html>"
                );
                validate();
                repaint();
            }
        });
        
        fText =  new JLabel(new MyImageIcon("resources/Sprite/Textbox.png").resize(300, 250));
        fText.setBounds(30,125,300,250);
        fspeak = new JLabel("<html>Use Click and Hold <br>your mouse to me!!</html>");
        fspeak.setFont(new Font("Tahoma", Font.BOLD, 14));
        fspeak.setBounds(30,30,275,130);
        fText.add(fspeak);
        
        score = new JButton("Score");
        score.setBounds(500, 350, 300, 100);
        score.setBackground(new Color(186, 241, 94));
        score.setFont(new Font("Tahoma", Font.BOLD, 42));
        score.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                highscore = new JDialog(menu, "High score");
                highscore.setModal(false);
                highscore.setResizable(false);
                highscore.setBounds(300, 100, 700, 700);
                highscore.setVisible(true);
                highscore.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

                dialogpane = new JPanel();
                dialogpane.setBackground(new Color(255, 204, 102));
                dialogpane.setBounds(0, 0, 700, 700);
                dialogpane.setLayout(null);
                
                highscore.add(dialogpane);
                
                
                
                ArrayList<Highscore> HL = new ArrayList<Highscore>();
                int check = 1;
                Scanner input = new Scanner(System.in);
                String fileName = "resources/Savefile/Highscore.txt";
                while (check == 1) {

                    try {
                        Scanner scan = new Scanner(new File(fileName));
                        while (scan.hasNext()) {
                            String line = scan.nextLine();
                            try {
                                String[] buf = line.split(",");
                                String name = buf[0];
                                String diff = buf[1];
                                long time = Long.parseLong(buf[2].trim());
                                int total = Integer.parseInt(buf[3].trim());
                                int score = Integer.parseInt(buf[4].trim());
                                

                                Highscore h = new Highscore(name, diff, time, total, score);
                                HL.add(h);
                            } catch (Exception i) {
                                System.out.println(i);
                                System.out.println(line);
                            }
                        }
                        scan.close();
                        check = 0;
                    } catch (Exception i) {
                        try {
                            PrintWriter write = new PrintWriter("resources/Savefile/Highscore.txt");
                            write.printf(" ,VeryEasy, 0, 0, 0, 0");
                            write.flush();
                        } catch (Exception a) {
                        }
                    }
                }
                Tgroup = new ButtonGroup();
                easyhard = new JRadioButton[5];
                JPanel bpanel = new JPanel();
                for (int i = 0; i < 5; i++) {
                    easyhard[i] = new JRadioButton(items[i]);
                    easyhard[i].setFont(new Font("Serif", Font.PLAIN, 24));
                    if (i == 0) {
                        easyhard[0].setSelected(true);
                    }
                    easyhard[i].addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            textscore.setText("");
                            textscore.setText(" Player name" + "    " + "Difficult" + "          " + "Time" + "       " + "Total fish" + "      " + "Score" +" "+"\n");
                            JRadioButton temp = (JRadioButton) e.getItem();
                            if (temp.isSelected()) {
                                nowbutton = temp.getText();
                                

                                System.out.print(temp.getText());
                                System.out.print(HL.get(0).getDiff());
                                Stream<Highscore> mystream = HL.stream();
                                mystream.filter(arg -> arg.getDiff().equals(temp.getText()))
                                        .map(arg -> {

                                            if (sortby == "Time") {
                                                arg.setResult(arg.getTime());
                                            }
                                            if (sortby == "Total Fish") {
                                                arg.setResult(arg.getTotalfish());
                                            }
                                            if (sortby == "Score") {
                                                arg.setResult(arg.getScore());
                                            }
                                            return arg;
                                        })
                                        .sorted()
                                        .forEach(arg -> {
                                                textscore.append(String.format("%-15s", arg.getPlayername()) + "\t" + String.format("%-9s", arg.getDiff()) + "\t" + String.format("%04d", arg.getTime()) + "          "
                                                + String.format("%04d", arg.getTotalfish()) + "          " + String.format("%05d", arg.getScore()) + " "+"\n");
                                            

                                        }
                                        );

                            }

                        }

                    });
                    //if(i==0)easyhard[0].setSelected(true);

                    Tgroup.add(easyhard[i]);
                    bpanel.add(easyhard[i]);

                }
                listmodel = new DefaultListModel();
                String item = "Time";
                listmodel.addElement(item);
                listmodel.addElement("Total Fish");
                listmodel.addElement("Score");
                

                list = new JList(listmodel);
                list.setVisibleRowCount(3);
                list.setBounds(300, 50, 200, 70);
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list.addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        // ----- (2) try with & without condition
                        if (!e.getValueIsAdjusting()) {
                            Object[] items = list.getSelectedValues();
                            if (items.length > 0) {
                                sortby = items[0].toString();
                            } else {
                                sortby = "";
                            }
                            textscore.setText(" Player name" + "    " + "Difficult" + "          " + "Time" + "       " + "Total fish" + "      " + "Score" +" "+"\n");
                            System.out.print(nowbutton);
                            System.out.print(HL.get(0).getDiff());
                            Stream<Highscore> mystream = HL.stream();
                            mystream.filter(arg -> arg.getDiff().equals(nowbutton))
                                    .map(arg -> {

                                        if (sortby == "Time") {
                                            arg.setResult(arg.getTime());
                                        }
                                        if (sortby == "Total Fish") {
                                            arg.setResult(arg.getTotalfish());
                                        }
                                        if (sortby == "Score") {
                                            arg.setResult(arg.getScore());
                                        }
                                        
                                        return arg;
                                    })
                                    .sorted()
                                    .forEach(arg -> {

                                        textscore.append(String.format("%-15s", arg.getPlayername()) + "\t" + String.format("%-9s", arg.getDiff()) + "\t" + String.format("%04d", arg.getTime()) + "          "
                                                + String.format("%04d", arg.getTotalfish()) + "          " + String.format("%05d", arg.getScore()) + " "+"\n");

                                    }
                                    );
                        }
                    }
                });
                list.setFont(new Font("Serif", Font.PLAIN, 20));
                JScrollPane lsc = new JScrollPane(list);
                lsc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                lsc.setBounds(270, 30, 150, 70);

                dialogpane.add(lsc);
                //dialogpane.remove(lsc);
                //dialogpane.add(lsc);
                
                bpanel.setBounds(70, 110, 540, 60);
                SortbyLabel = new JLabel("Sortby");
                SortbyLabel.setBounds(160,30,150,70);
                SortbyLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
                textscore = new JTextArea(22, 20);
                JScrollPane tsc = new JScrollPane();
                tsc.setViewportView(textscore);
                tsc.setBounds(120, 200, 450, 300);
                textscore.setFont(new Font("Serif", Font.PLAIN, 18));
                textscore.setEditable(false);
                tsc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                textscore.setBounds(120, 200, 450, 300);
                dialogpane.add(tsc);
                dialogpane.add(bpanel);
                dialogpane.add(SortbyLabel);
                
                
                validate();
                repaint();
                dialogpane.revalidate();
            }
        });

        exist = new JButton("Exit");
        exist.setBounds(500, 500, 300, 100);
        exist.setBackground(new Color(186, 241, 94));
        exist.setFont(new Font("Tahoma", Font.BOLD, 42));
        exist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        login = new JLabel("You login as " + username);
        login.setBounds(0, 0, (300 + username.length() * 25), 50);
        //login.setForeground(Color.BLUE);
        login.setOpaque(true);
        login.setBackground(new Color(186, 241, 94));
        login.setFont(new Font("Tahoma", Font.BOLD, 42));
        //soundbackground = new MySoundEffect("resources/ocean.wav");
        sound = new JCheckBox("Music");
        sound.setBackground(new Color(186, 241, 94));
        sound.setBounds(1000, 580, 150, 50);
        sound.setFont(new Font("Tahoma", Font.BOLD, 42));
        sound.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            if(e.getStateChange()==ItemEvent.SELECTED)
                            {
                                
                                soundbackground.playOnce();
                                soundbackground.setvolume(volume.getValue());
                            }
                            else
                            {
                                soundbackground.stop();
                            }
                            
                        }
        });
        
        selectmusic = new JSpinner(new SpinnerListModel(musiclist));
        selectmusic.setBounds(1000,500,180,50);
        selectmusic.setFont(new Font("Tahoma", Font.BOLD, 18));
        selectmusic.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (selectmusic.getValue() == "Ocean") {
                    soundbackground.stop();
                    soundbackground.SetMusic("resources/Sound/Ocean.wav"); 
                    //= new MySoundEffect("resources/ocean.wav");
                    if(sound.isSelected())
                    {
                        soundbackground.playOnce();
                        soundbackground.setvolume(volume.getValue());
                    }
                    //soundbackground.playLoop();
                }
                else if(selectmusic.getValue() == "Feeding Frenzy")
                {
                    soundbackground.stop();
                    soundbackground.SetMusic("resources/Sound/FF.wav") ;
                            //= new MySoundEffects("resources/FF.wav");
                     if(sound.isSelected())
                    {
                        soundbackground.playOnce();
                        soundbackground.setvolume(volume.getValue());
                    }
                    //soundbackground.playLoop();
                }
                else if(selectmusic.getValue() == "Mysterious")
                {
                    soundbackground.stop();
                    soundbackground.SetMusic("resources/Sound/mysterious.wav") ;
                            //= new MySoundEffects("resources/FF.wav");
                     if(sound.isSelected())
                    {
                        soundbackground.playOnce();
                        soundbackground.setvolume(volume.getValue());
                    }
                    //soundbackground.playLoop();
                }

            }
        }
        );
        volume = new JSlider(JSlider.VERTICAL, 20, 100, 60);
        volume.setBackground(new Color(186, 241, 94));
        volume.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                if(sound.isSelected())
               soundbackground.setvolume(volume.getValue());
             
            }
         });   
        volume.setBounds(1200,500,50,150);
        //soundbackground.setvolume(volume.getValue());
            
            
        
        
        
        backlogin = new JButton();
        backlogin.setBounds(10, 500, 150, 150);
        backlogin.setOpaque(false);
        backlogin.setContentAreaFilled(false);
        backlogin.setBorderPainted(false);
        backlogin.setIcon(new MyImageIcon("resources/Sprite/back.png").resize(150, 150));
        backlogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawpane.remove(start);
                drawpane.remove(score);
                drawpane.remove(exist);
                drawpane.remove(login);
                drawpane.remove(backlogin);
                drawpane.remove(sound);
                drawpane.remove(selectmusic);
                drawpane.remove(volume);
                drawpane.remove(fishin);
                drawpane.remove(fText);
                username = "";
                drawpane.add(passwordpane);
                soundbackground.stop();
                validate();
                repaint();
            }
        });

        drawpane.add(start);
        drawpane.add(score);
        drawpane.add(exist);
        drawpane.add(login);
        drawpane.add(backlogin);
        drawpane.add(sound);
        drawpane.add(selectmusic);
        drawpane.add(volume);
        drawpane.add(fishin);
        drawpane.add(fText);

    }

    public static void main(String[] args) {
        new Main();
    }
    
};


class MyImageIcon extends ImageIcon {

    public MyImageIcon(String fname) {
        super(fname);
    }

    public MyImageIcon(Image image) {
        super(image);
    }

    public MyImageIcon resize(int width, int height) {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
};

class Highscore implements Comparable<Highscore> {

    private String playername, diff;
    private long time;
    private int totalfish, score;
    private long result;

    public Highscore(String pn, String di, long t, int tf, int sc) {
        playername = pn;
        diff = di;
        time = t;
        totalfish = tf;
        score = sc;
        
    }

    public void setPlayername(String na) {
        playername = na;
    }

    public void setDiff(String d) {
        diff = d;
    }

    public void setTime(long ye) {
        time = ye;
    }

    public void setTotalfish(int be) {
        totalfish = be;
    }

    public void setScore(int wi) {
        score = wi;
    }

    

    public void setResult(long re) {
        result = re;
    }

    public String getPlayername() {
        return playername;
    }

    public String getDiff() {
        return diff;
    }

    public long getTime() {
        return time;
    }

    public int getTotalfish() {
        return totalfish;
    }

    public int getScore() {
        return score;
    }

    

    public double getResult() {
        return result;
    }

    public boolean isDiff() {
        return diff == "Hard";
    }

    public int compareTo(Highscore other) { // general sorting by result & name
        int x = Double.compare(other.result, result);
        if (x != 0) {
            return x;
        } else {
            return playername.compareToIgnoreCase(other.playername);
        }
    }

};

class MySoundEffects {

    private Clip clip;
    public MySoundEffects(String filename) {
        try {
           
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream); 
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-40f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SetMusic(String filename) {
        System.out.print("dsffsdfs");
        try {
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playOnce() {
        clip.setMicrosecondPosition(0);
        
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playLoop() {
        System.out.print("dsffsdfs");
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(0f);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        
        
    }
    public Clip getClip()
    {
        return clip;
    }
    
    public void setvolume(int level){
        clip.stop();
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        if (volume != null) {
        Float result= (-(100-level)/100f)*100;
        volume.setValue(result);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    }
    
    public void stop() {
        clip.stop();
    }
   
   
};

