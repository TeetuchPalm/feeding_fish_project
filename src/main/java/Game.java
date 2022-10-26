/*6313131 Teetuch Siribunpitug
  6313176 Nattapong Jitrangsi
  6313172 Chavakan Yimmark*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.sound.sampled.*;     // for sounds

import java.util.ArrayList;
import java.util.Random;
import java.io.*;


public class Game extends JFrame{
    private JPanel             contentpane;
    private JLabel             drawpane;
    private JLabel             playerLabel;
    private JLabel             scoreLabel,lifeLabel,lifeCountLabel,timeTextLabel,colocLabel;
    private MyImageIcon        bgImg,playerImgLeft,playerImgRight,scoreTxt,lifeText,timeText,colon,jelly,addTime,heart;
    ;
    private ArrayList<Fish> fishArray = new ArrayList<Fish>();
    private ArrayList<MyImageIcon> numArray = new ArrayList<MyImageIcon>();
    private ArrayList<JLabel> numLabel = new ArrayList<JLabel>();
    private JLabel timeLabel[] = new JLabel[3];


    private final int frameWidth = 1300, frameHeight = 700;
    private int playerWidth = frameWidth/15,  playerHeight = frameHeight/15; 
    private int playerCurX  = 800,  playerCurY   = frameHeight - playerHeight - 50;
    private int textWidth = frameWidth/20, textHeight = frameHeight/20;
    private final int dx = 2;
    private final int dy = 1;
    private boolean playerLeft;
    private int score = 0;
    private int playerLevel = 1;
    private boolean gameOn = false;
    private int fishNumber[] = {5, 1, 0, 0, 0};
    private int playerLife;
    private boolean playerHitable = true;


    private int difficulty = 0;
    private int colorCode = 0;
    private int bgMode = 1;
    private int spawnVar[] = {0, 0, 1, 2, 3};
    private int timeMinDiff[] = {1,1,1,1,0};
    private int timeSecDiff[] = {45,30,15,0,45};
    private int heartStart[] = {5,5,4,3,3};
    private String color[] = {"O","B","G","Y","P"};
    private int totalTime = 0, totalEat = 0;

    private int timeMin, timeSec;

    //Set name of player
    private Player p = new Player("Untitled");


    public static void main(String[] args) {
        //new Game("Sun",4,0,1);
    }
    
    public Game(String pln, int diff, int col, int bg){

        p.setName(pln);
        difficulty = diff;
        colorCode = col;
        timeMin = timeMinDiff[difficulty];
        timeSec = timeSecDiff[difficulty];
        bgMode = bg;
        playerLife = heartStart[difficulty];

        fishArray.add(new Fish("fish_1", frameWidth/25, frameHeight/25));
        fishArray.add(new Fish("fish_2", frameWidth/10, frameHeight/10));
        fishArray.add(new Fish("fish_3", frameWidth/8, frameHeight/8));
        fishArray.add(new Fish("fish_4", frameWidth/7, frameHeight/7));
        fishArray.add(new Fish("fish_5", frameWidth/5, frameHeight/5));

        setTitle("Feeding Fish");
        setBounds(50, 50, frameWidth, frameHeight);
        setResizable(false);
	    setVisible(true);
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        //exit
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                gameOn = false;
                System.exit(0);
            }
        });
        //Player Movement
        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W ){p.setVelY(-dy);}
            else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S ){p.setVelY(dy);}
            else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A ){p.setVelX(-dx);playerLabel.setIcon(playerImgLeft);playerLeft = true;}
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D ){p.setVelX(dx);playerLabel.setIcon(playerImgRight);playerLeft = false;}
            else if (e.getKeyCode() == KeyEvent.VK_SPACE){playerDash(p);}
            }
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){p.setVelY(0);}
                else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S ){p.setVelY(0);}
                else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A ){p.setVelX(0);}
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D ){p.setVelX(0);}
                }
        });


        contentpane = (JPanel)getContentPane();
	    contentpane.setLayout( new BorderLayout());   
        AddComponents();
    }

    public void AddComponents(){


        playerImgLeft = new MyImageIcon("resources/Sprite/L1/"+color[colorCode]+"_left.png").resize(playerWidth, playerHeight);
        playerImgRight = new MyImageIcon("resources/Sprite/L1/"+color[colorCode]+"_right.PNG").resize(playerWidth, playerHeight);
        bgImg = new MyImageIcon("resources/Background/bg"+bgMode+".jpg").resize(frameWidth, frameHeight);
        scoreTxt = new MyImageIcon("resources/Text/score.png").resize(textWidth, textHeight);
        lifeText = new MyImageIcon("resources/Text/life.png").resize(textWidth, textHeight);
        timeText = new MyImageIcon("resources/Text/time.png").resize(textWidth, textHeight);
        colon = new MyImageIcon("resources/Text/colon.png").resize(textWidth/4, textHeight);
        jelly = new MyImageIcon("resources/Sprite/jelly.png").resize(textWidth*2, textWidth*2);
        addTime = new MyImageIcon("resources/Sprite/addTime.png").resize(textWidth, textWidth);
        heart = new MyImageIcon("resources/Sprite/heart.png").resize(textWidth/2, textWidth/2);

        //Set picture of numArray
        for(int i = 0; i < 10 ; i++){
            MyImageIcon num = new MyImageIcon("resources/Text/"+i+".png").resize(textWidth/2, textHeight);
            numArray.add(num);
        }

        drawpane = new JLabel();
	    drawpane.setIcon(bgImg);
        drawpane.setLayout(null);

        //Player
        playerLabel = new JLabel(playerImgLeft);
        playerLabel.setBounds(playerCurX, playerCurY, playerWidth, playerHeight);
        drawpane.add(playerLabel);

        //Scoreboard
        scoreLabel = new JLabel(scoreTxt);
        scoreLabel.setBounds(10, 10, textWidth, textHeight);
        drawpane.add(scoreLabel);
        for(int i = 0; i < 5 ; i++){
            JLabel n = new JLabel(numArray.get(0));
            n.setBounds(10+(i*textWidth/2)+textWidth, 10, textWidth/2, textHeight);
            numLabel.add(n);
            drawpane.add(numLabel.get(i));
        }

        lifeLabel = new JLabel(lifeText);
        lifeLabel.setBounds(10+(2*textWidth)+(4*textWidth/2),10,textWidth,textHeight);
        drawpane.add(lifeLabel);

        lifeCountLabel = new JLabel(numArray.get(playerLife));
        lifeCountLabel.setBounds(10+(3*textWidth)+(4*textWidth/2),10,textWidth/2,textHeight);
        drawpane.add(lifeCountLabel);

        timeTextLabel = new JLabel(timeText);
        timeTextLabel.setBounds(frameWidth-(3*textWidth/2)-textWidth/4-textWidth-20,10,textWidth,textHeight);
        drawpane.add(timeTextLabel);
        timeLabel[0] = new JLabel(numArray.get(timeMin));
        timeLabel[0].setBounds(frameWidth-(3*textWidth/2)-textWidth/4-20,10,textWidth/2,textHeight);
        drawpane.add(timeLabel[0]);
        colocLabel = new JLabel(colon);
        colocLabel.setBounds(frameWidth-(2*textWidth/2)-textWidth/4-20,10,textWidth/4,textHeight);
        drawpane.add(colocLabel);
        timeLabel[1] = new JLabel(numArray.get((timeSec/10)%10));
        timeLabel[1].setBounds(frameWidth-(2*textWidth/2)-20,10,textWidth/2,textHeight);
        drawpane.add(timeLabel[1]);
        timeLabel[2] = new JLabel(numArray.get(timeSec%10));
        timeLabel[2].setBounds(frameWidth-textWidth/2-20,10,textWidth/2,textHeight);
        drawpane.add(timeLabel[2]);

        drawpane.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                playerDash(p);
            }
        });

        contentpane.add(drawpane, BorderLayout.CENTER);      
        validate();
        StartGameThread(p);
    }

    public void playerDash(Player p){
        if(!gameOn){return;}
        playerCurX = playerCurX + 50*p.getVelX();
        playerCurY = playerCurY + 50*p.getVelY();
        if(playerCurY <= textHeight+10){playerCurY = textHeight+10;}
        else if(playerCurY+playerHeight >= frameHeight-30){playerCurY = frameHeight-playerHeight-30;}
        else if(playerCurX <= 0){playerCurX = 0;}
        else if(playerCurX+playerWidth >= frameWidth){playerCurX = frameWidth-playerWidth;}
        MySoundEffect dashSound = new MySoundEffect("resources/Sound/dash.wav");
        dashSound.playOnce();
        playerLabel.setLocation(playerCurX,playerCurY);repaint();
    }

    public void StartGameThread(Player p){
        Thread playerThread = new Thread(){
            public void run(){
                long lastSec = 0;
                while(gameOn){
                    long sec = System.currentTimeMillis()/10;
                    if (sec != lastSec) {
                        playerCurX = playerCurX + p.getVelX();
                        playerCurY = playerCurY + p.getVelY();
                        if(playerCurY <= textHeight+10){playerCurY = textHeight+10;}
                        else if(playerCurY+playerHeight >= frameHeight-30){playerCurY = frameHeight-playerHeight-30;}
                        else if(playerCurX <= 0){playerCurX = 0;}
                        else if(playerCurX+playerWidth >= frameWidth){playerCurX = frameWidth-playerWidth;}
                        playerLabel.setLocation(playerCurX,playerCurY);repaint();
                        //System.out.println("Player Y = "+playerCurY+"  Player X = "+playerCurX);
                        lastSec = sec;
                    }

                }
                p.setVelX(0);
                p.setVelY(0);
                try{Thread.sleep(3000);}catch(Exception e){e.printStackTrace();}
                while(playerLife > 0){
                    playerLife = playerLife - 1;
                    lifeCountLabel.setIcon(numArray.get(playerLife));
                    lifeCountLabel.repaint();
                    MySoundEffect addScore = new MySoundEffect("resources/Sound/upheart.wav");
                    addScore.playOnce();
                    updateScore(100);
                    try{Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
                }

                
                try {
                    PrintWriter write = new PrintWriter(new FileWriter("resources/Savefile/Highscore.txt", true));
                    String dif[] = {"VeryEasy","Easy","Normal","Hard","VeryHard"}; 
                    write.printf("%s,%s, %d, %d, %d\n",p.getName(),dif[difficulty],totalTime,totalEat,score);
                    write.flush();
                } catch (Exception e) {e.printStackTrace();}

                Frame[] f = Frame.getFrames();
                f[0].setVisible(true);
                closeThis();
                //Send 
            }
        };
        Random rand = new Random();
        for(int i = 0; i < rand.nextInt(2)+5; i++){
            addFish(fishArray, 0);
        }
        gameOn = true;
        playerThread.start();
        fishSpawn();
        timer();
    }

    public void closeThis(){
        this.dispose();
    }

    public void timer(){
        Thread timerThread = new Thread(){
            public void run(){
                while(gameOn){
                    try{Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
                    timeSec = timeSec - 1;
                    totalTime = totalTime + 1;
                    if(timeSec == 0 && timeMin == 0){
                        gameOn = false;
                        MySoundEffect runoutTime = new MySoundEffect("resources/Sound/runouttime.wav");
                        runoutTime.playOnce();
                    }
                    else if(timeSec == 10 & timeMin == 0){
                        MySoundEffect outOfTime = new MySoundEffect("resources/Sound/outoftime.wav");
                        outOfTime.playOnce();
                    }
                    else if(timeSec == -1){
                        timeSec = 59;
                        timeMin = timeMin - 1;
                    }
                    timeLabel[0].setIcon(numArray.get(timeMin));
                    timeLabel[1].setIcon(numArray.get((timeSec/10)%10));
                    timeLabel[2].setIcon(numArray.get(timeSec%10));
                    for(int i = 0; i < 3 ;i++){
                        timeLabel[i].repaint();
                    }
                }
            }
        };
        timerThread.start();
    }

    public void fishSpawn(){
        Thread spawnThread = new Thread(){
            public void run(){
                while(gameOn){
                    Random rand = new Random();
                    for(int i = 0; i < rand.nextInt(3)+fishNumber[0]+spawnVar[difficulty]; i++){
                        addFish(fishArray, 0);
                    }
                    for(int i = 0; i < rand.nextInt(2)+fishNumber[1]+spawnVar[difficulty]; i++){
                        addFish(fishArray, 1);
                    }
                    for(int i = 0; i < rand.nextInt(2)+fishNumber[2]+spawnVar[difficulty]; i++){
                        addFish(fishArray, 2);
                    }
                    for(int i = 0; i < rand.nextInt(2)+fishNumber[3]+spawnVar[difficulty]; i++){
                        addFish(fishArray, 3);
                    }
                    for(int i = 0; i < rand.nextInt(1)+fishNumber[4]+spawnVar[difficulty]; i++){
                        addFish(fishArray, 4);
                    }
                    for(int i = 0; i < rand.nextInt(2)+2+spawnVar[difficulty]; i++){
                        addSpeacialObject();
                    }
                    try{Thread.sleep(rand.nextInt(7000)+3000);}catch(Exception e){e.printStackTrace();}
                }
            }
        };
        spawnThread.start();
    }

    public void addSpeacialObject(){
        Thread specialObjThread = new Thread(){
            public void run(){
                JLabel specialObjLabel;
                Random rand = new Random();
                int dy = -(rand.nextInt(3)+1)/(rand.nextInt(2)+1);
                int condition = rand.nextInt(10);
                if(condition <= 1){specialObjLabel = new JLabel(addTime);}
                else if(condition == 2){specialObjLabel = new JLabel(heart);}
                else{specialObjLabel = new JLabel(jelly);}
                int objX = rand.nextInt(frameWidth-4*textWidth)+2*textWidth;
                int objY = frameHeight;
                specialObjLabel.setBounds(objX, objY, textWidth, textWidth);
                drawpane.add(specialObjLabel);
                while(objY > textHeight && gameOn){
                    objY = objY + dy;
                    if(objY <= textHeight){objY = textHeight;}
                    if(playerLeft){
                        if(playerCurX <= objX+textWidth && playerCurX + (playerWidth/2) >= objX && objY+(textWidth/2) >= playerCurY && objY+(textWidth/2) <= playerCurY+playerHeight){
                            touch(condition);
                            if(playerHitable || condition <= 2){break;}
                        }
                    }
                    else{
                        if(playerCurX+playerWidth >= objX && playerCurX+(playerWidth/2) <= objX+textWidth && objY+(textWidth/2) >= playerCurY && objY+(textWidth/2)<= playerCurY+playerHeight){
                            touch(condition);
                            if(playerHitable || condition <= 2){break;}
                        }
                    }
                    specialObjLabel.setLocation(objX,objY);
                    repaint();
                    try { Thread.sleep(10);} catch (InterruptedException e) { e.printStackTrace(); }
                }
                drawpane.remove(specialObjLabel);
                repaint(); 
            }

            public void touch(int condition){
                if(condition <= 1){
                    MySoundEffect timeSound = new MySoundEffect("resources/Sound/uptime.wav");
                    timeSound.playOnce();
                    timeSec = timeSec + 15;
                    if(timeSec >= 60){
                        timeSec = timeSec - 60;
                        timeMin = timeMin + 1;
                    }
                }
                else if(condition == 2){
                    MySoundEffect heartSound = new MySoundEffect("resources/Sound/upheart.wav");
                    heartSound.playOnce();
                    addLife();
                }
                else{
                    if(playerHitable){
                        updateLife();
                        updateScore(-10);
                    }
                }
            }
        };
        specialObjThread.start();
    }

    public void addFish(ArrayList<Fish> f, int fL){
        Thread fishThread = new Thread(){
            public void run(){
                boolean eaten = false;
                int fScore[] = {1, 3, 5, 10, 15};
                //System.out.println("Fish born!");
                int fishWidth = f.get(fL).getFishWidth();
                int fishHeight = f.get(fL).getFishHeight();
                //System.out.println(fishWidth+"  "+fishHeight);
                Random rand = new Random();
                int fishDx = (rand.nextInt(2)+1)/(rand.nextInt(2)+1);
                int fishDy = 0;
                int OutOfBound;
                MyImageIcon fishImg;
                int fishCurX;
                if(rand.nextInt(100)%2 == 0){fishCurX = frameWidth-1; fishImg = f.get(fL).getfishLeft();fishDx = -fishDx;OutOfBound = -fishWidth;}
                else {fishCurX = -fishWidth;fishImg = f.get(fL).getfishRight();OutOfBound = frameWidth-1;}
                int temp = rand.nextInt(3);
                if (temp == 1){fishDy = -(rand.nextInt(2)+1)/(rand.nextInt(2)+1);}
                else if(temp == 2){fishDy = (rand.nextInt(2)+1)/(rand.nextInt(2)+1);}
                int fishCurY = rand.nextInt(frameHeight-fishHeight-textHeight)+fishHeight+textHeight;

                JLabel fishLabel = new JLabel(fishImg);
                fishLabel.setBounds(fishCurX, fishCurY, fishWidth, fishHeight);
                drawpane.add(fishLabel);

                while(fishCurX != OutOfBound && gameOn){
                    fishCurX = fishCurX + fishDx;
                    fishCurY = fishCurY + fishDy;
                    if(fishCurY <= textHeight){fishCurY = textHeight; fishDy = -fishDy;}
                    else if(fishCurY >= frameHeight-fishHeight-textHeight){fishCurY = frameHeight-fishHeight-textHeight;fishDy = -fishDy;}
                    
                    if(playerLeft){
                        if(playerCurX <= fishCurX+fishWidth && playerCurX + (playerWidth/2) >= fishCurX && fishCurY+(fishHeight/2) >= playerCurY && fishCurY+(fishHeight/2) <= playerCurY+playerHeight){
                            //Score and size condition
                            if(playerLevel > fL){
                                updateScore(fScore[fL]);
                                eaten = true;
                                totalEat = totalEat + 1;
                                break;
                            }
                        }
                    }
                    else{
                        if(playerCurX+playerWidth >= fishCurX && playerCurX+(playerWidth/2) <= fishCurX+fishWidth && fishCurY+(fishHeight/2) >= playerCurY && fishCurY+(fishHeight/2)<= playerCurY+playerHeight){
                            //Score and size condition
                            if(playerLevel > fL){
                                updateScore(fScore[fL]);
                                eaten = true;
                                totalEat = totalEat + 1;
                                break;
                            }
                        }
                    }
                    

                    if(fL >= playerLevel && playerHitable){
                        if(playerCurX+(playerWidth/2) >= fishCurX && playerCurX + (playerWidth/2) <= fishCurX + fishWidth && playerCurY + (playerHeight/2) >= fishCurY && playerCurY + (playerHeight/2) <= fishCurY + fishHeight){
                            updateLife();
                        }
                    }

                    fishLabel.setLocation(fishCurX, fishCurY);
                    //System.out.println("Fish Y = "+fishCurY+"  Fish X = "+fishCurX);
                    repaint();
                    try { Thread.sleep(10);} catch (InterruptedException e) { e.printStackTrace(); }
                }
                //Effect
                if(eaten){
                    MyImageIcon effectChomp = new MyImageIcon("resources/Effect/chomp.png").resize(fishWidth, fishHeight);
                    JLabel effectLabel = new JLabel(effectChomp);
                    effectLabel.setBounds(fishCurX, fishCurY, fishWidth, fishHeight);
                    drawpane.add(effectLabel);
                    drawpane.remove(fishLabel);
                    MySoundEffect chompSound = new MySoundEffect("resources/Sound/chomp.wav"); 
                    chompSound.playOnce();
                    repaint();
                    try { Thread.sleep(200);} catch (InterruptedException e) { e.printStackTrace(); }
                    drawpane.remove(effectLabel);
                    repaint();
                }
                else{
                    drawpane.remove(fishLabel);
                    repaint();
                }
            }
        };
        fishThread.start();
    }

    public synchronized void addLife(){
        playerLife = playerLife + 1;
        lifeCountLabel.setIcon(numArray.get(playerLife));
        lifeCountLabel.repaint();
    }

    public synchronized void updateLife(){
        playerLife = playerLife - 1;
        playerHitable = false;
        if(playerLife != 0){
            MySoundEffect hitSound = new MySoundEffect("resources/Sound/hit.wav");
            hitSound.playOnce();
        }
        Thread hitablThread = new Thread(){
            public void run(){                
                for(int i = 0; i< 3; i++){
                    playerLabel.setVisible(false);
                    playerLabel.repaint();
                    try{Thread.sleep(500);}catch(Exception e){e.printStackTrace();}
                    playerLabel.setVisible(true);
                    playerLabel.repaint();
                    try{Thread.sleep(500);}catch(Exception e){e.printStackTrace();}
                }
                playerHitable = true;
            }
        };
        hitablThread.start();
        lifeCountLabel.setIcon(numArray.get(playerLife));
        lifeCountLabel.repaint();
        if(playerLife == 0){
            MySoundEffect diedSound = new MySoundEffect("resources/Sound/died.wav");
            diedSound.playOnce();
            gameOn = false;
            return;
        }


    }

    public synchronized void updateScore(int fScore){
        score = score + fScore;
        if(score <= 0){score = 0;}
        if(score >= 99999){gameOn = false; return;}
        numLabel.get(4).setIcon(numArray.get(score%10));
        numLabel.get(3).setIcon(numArray.get(0));
        numLabel.get(2).setIcon(numArray.get(0));
        numLabel.get(1).setIcon(numArray.get(0));
        numLabel.get(0).setIcon(numArray.get(0));
        if(score>= 10){numLabel.get(3).setIcon(numArray.get((score/10)%10));}
        if(score>= 100){numLabel.get(2).setIcon(numArray.get((score/100)%10));}
        if(score>= 1000){numLabel.get(1).setIcon(numArray.get((score/1000)%10));}
        if(score>= 10000){numLabel.get(0).setIcon(numArray.get((score/10000)%10));}
        repaint();

        if(score >= 15 && playerLevel == 1){
            playerWidth = frameWidth/12;
            playerHeight = frameHeight/12;
            playerImgLeft = new MyImageIcon("resources/Sprite/L2/"+color[colorCode]+"_left.png").resize(playerWidth, playerHeight);
            playerImgRight = new MyImageIcon("resources/Sprite/L2/"+color[colorCode]+"_right.PNG").resize(playerWidth, playerHeight);
            playerLabel.setSize(playerWidth, playerHeight);
            playerLabel.repaint();
            playerLevel = 2;
            MySoundEffect levelSound = new MySoundEffect("resources/Sound/level_up.wav"); 
            levelSound.playOnce();
            fishNumber[0] = 2;
            fishNumber[1] = 3;
            fishNumber[2] = 1;
            fishNumber[3] = 0;
            fishNumber[4] = 0;
        }
        else if(score >= 50 && playerLevel == 2){
            playerWidth = frameWidth/10;
            playerHeight = frameHeight/10;
            playerImgLeft = new MyImageIcon("resources/Sprite/L3/"+color[colorCode]+"_left.png").resize(playerWidth, playerHeight);
            playerImgRight = new MyImageIcon("resources/Sprite/L3/"+color[colorCode]+"_right.PNG").resize(playerWidth, playerHeight);
            playerLabel.setSize(playerWidth, playerHeight);
            playerLabel.repaint();
            playerLevel = 3;
            MySoundEffect levelSound = new MySoundEffect("resources/Sound/level_up.wav"); 
            levelSound.playOnce();
            fishNumber[0] = 1;
            fishNumber[1] = 2;
            fishNumber[2] = 3;
            fishNumber[3] = 1;
            fishNumber[4] = 0;
        }

        else if(score >= 100 && playerLevel == 3){
            playerWidth = frameWidth/9;
            playerHeight = frameHeight/9;
            playerImgLeft = new MyImageIcon("resources/Sprite/L4/"+color[colorCode]+"_left.png").resize(playerWidth, playerHeight);
            playerImgRight = new MyImageIcon("resources/Sprite/L4/"+color[colorCode]+"_right.PNG").resize(playerWidth, playerHeight);
            playerLabel.setSize(playerWidth, playerHeight);
            playerLabel.repaint();
            playerLevel = 4;
            MySoundEffect levelSound = new MySoundEffect("resources/Sound/level_up.wav"); 
            levelSound.playOnce();
            fishNumber[0] = 1;
            fishNumber[1] = 1;
            fishNumber[2] = 2;
            fishNumber[3] = 2;
            fishNumber[4] = 1;
        }

        else if(score >= 250 && playerLevel == 4){
            playerWidth = frameWidth/7;
            playerHeight = frameHeight/7;
            playerImgLeft = new MyImageIcon("resources/Sprite/L5/"+color[colorCode]+"_left.png").resize(playerWidth, playerHeight);
            playerImgRight = new MyImageIcon("resources/Sprite/L5/"+color[colorCode]+"_right.PNG").resize(playerWidth, playerHeight);
            playerLabel.setSize(playerWidth, playerHeight);
            playerLabel.repaint();
            playerLevel = 5;
            MySoundEffect levelSound = new MySoundEffect("resources/Sound/level_up.wav"); 
            levelSound.playOnce();
            fishNumber[0] = 1;
            fishNumber[1] = 1;
            fishNumber[2] = 1;
            fishNumber[3] = 2;
            fishNumber[4] = 2;
        }
    }


    public boolean getGameState(){return gameOn;}
}



class Player{
    private String name;
    private int velX, velY;
    public Player(String name){
        this.name = name;
        velX = 0;
        velY = 0;
    }

    public void setVelX(int x){velX = x;}
    public void setVelY(int y){velY = y;}
    public int getVelX(){return velX;}
    public int getVelY(){return velY;}
    public String getName(){return name;}
    public void setName(String n){this.name = n;}
}

class Fish{
    private int fishWidth, fishHeight;
    private String name;
    private MyImageIcon fishImgLeft, fishImgRight;
    public Fish(String name, int w, int h){
        this.name = name;
        fishWidth = w;
        fishHeight = h;
        fishImgLeft = new MyImageIcon("resources/Sprite/"+name+"_left.png").resize(fishWidth, fishHeight);
        fishImgRight = new MyImageIcon("resources/Sprite/"+name+"_right.png").resize(fishWidth, fishHeight);
    }

    public int getFishWidth(){return fishWidth;}
    public int getFishHeight(){return fishHeight;}
    public MyImageIcon getfishLeft(){return fishImgLeft;}
    public MyImageIcon getfishRight(){return fishImgRight;}
    public String getName(){return name;}
}

// Auxiliary class to play sound effect (support .wav or .mid file)
class MySoundEffect
{
    private Clip clip;

    public MySoundEffect(String filename)
    {
	try
	{
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
	}
	catch (Exception e) { e.printStackTrace(); }
    }
    public void playOnce()   { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop()   { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop()       { clip.stop(); }

}