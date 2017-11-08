// ==================================================================================
// パッケージ
// ==================================================================================
package dron_ex2017;

// ==================================================================================
// ライブラリ
// ==================================================================================
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.lang.Thread;
import java.applet.AudioClip;
import java.applet.Applet;

// ==================================================================================
// Menuクラス
// ==================================================================================
public class Menu extends JPanel implements KeyListener, Runnable, ActionListener, MouseListener {
    // ==================================================================================
    // 変数宣言
    // ==================================================================================
    private JLabel title;
    private Main m;
    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    private String str;
    private BufferedImage image;
    Timer timer;
    Thread thread;
    JButton exit, staffCredits;
    GameButton startGame;
    Sounds bgm = new Sounds();
    ImageIcon gameStart_n, gameStart_h, gameStart_p; // n = normal h = highlight p = push
    ImageIcon gameExit_n, gameExit_h, gameExit_p;

    // ==================================================================================
    // コンストラクタ
    // ==================================================================================
    public Menu(Main const_main, String s) {
        /* 変数宣言 */

        m = const_main;                                      // Main型オブジェクトの格納
        str = s;                                             // 文字列の取得
        this.setName("Menu");                                // オブジェクト名を設定
        this.setLayout(null);                                // レイアウトは使わない

        /* これがないとキーボードの入力を受け付けない */
        setFocusable(true);                                  // パネルのキーボード入力受け付け
        addKeyListener(this);                                // 本オブジェクトをkeyListenerの対象に加える

        /* マウスイベントの受け付け */
        addMouseListener(this);

        /* 背景画像の読み込み */
        try {
            image = ImageIO.read(new File("images/menu.png"));
        } catch (IOException e) {
            System.out.println("画像読み込み不可");
        }

        // ==== ボタン関係 ====
        /* ゲーム開始 */
        gameStart_n = new ImageIcon("images/gameStartButton/gameStart_n.png");  // 通常状態のボタン
        gameStart_h = new ImageIcon("images/gameStartButton/gameStart_h.png");  // ロールオーバー時のアイコン
        gameStart_p = new ImageIcon("images/gameStartButton/gameStart_p.png");  // 押された時のアイコン
        startGame = new GameButton("Game Start!!!", gameStart_n);               // ゲームスタートボタン
        startGame.setPressedIcon(gameStart_p);                                  // 押された時の状態
        startGame.setRolloverIcon(gameStart_h);                                 // ロールオーバー時の状態
        startGame.setContentAreaFilled(false);                                  // デフォルトボタンの中身を消す
        startGame.setBorderPainted(false);                                      // デフォルトボタンの枠線を消す
        startGame.addActionListener(this);                                      // これがないとボタン操作を受け付けない
        startGame.setBounds(90, 180, 320, 80);                                  // ボタンの配置および大きさ
        this.add(startGame);                                                    // パネルにボタンを追加

        /* ゲーム終了 */
        /*


        exit = new GameButton("Exit");
        exit.addActionListener(this);
        exit.setBounds(90, 270, 320, 80);
        this.add(exit);
        */

        /* スタッフクレジット */
        /*
        staffCredits = new GameButton("Staff Credits");
        staffCredits.addActionListener(this);
        staffCredits.setBounds(90, 360, 320, 80);
        this.add(staffCredits);
        */
    }

    // ==================================================================================
    // ボタン操作の受け付け
    // ==================================================================================
    public void actionPerformed(ActionEvent event) {
        /* ゲーム開始 */
        if (event.getSource() == startGame) {
            System.out.println("ボタン押された");  // デバックメッセージ
            pc(m.PanelNames[1]);                 // ゲームスタート
        }
        /* ゲームの終了 */
        if (event.getSource() == exit) {
            System.exit(0);
        }
        if (event.getSource() == staffCredits) {
            pc(m.PanelNames[2]);
        }
    }

    // ==================================================================================
    // 背景画像の表示
    // ==================================================================================
    @Override
    protected void paintComponent(Graphics g) {

        double panelWidth = getWidth();              // ウィンドウの幅取得
        double panelHeight = getHeight();            // ウィンドウの高さ取得
        double imageWidth = image.getWidth(this);    // イメージの幅取得
        double imageHeight = image.getHeight(this);  // イメージの高さ取得

        double sw = panelWidth / imageWidth;         // 幅をウィンドウにあわせる(sw = scaling width)
        double sh = panelHeight / imageHeight;       // 高さをウィンドウにあわせる(sh = scaling height)
        sw = sw * imageWidth;                        // 画像をスケーリング
        sh = sh * imageHeight;                       // 画像をスケーリング

        super.paintComponent(g);                     // アッ
        g.drawImage(image, 0, 0, (int)sw, (int)sh, this);  // 画像を表示
    }


    // ==================================================================================
    // ゲームスタート
    // ==================================================================================
    @Override
    public void keyPressed(KeyEvent e) {
        int cnt = 0;
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {              // エンターキーでゲームスタート
            //System.out.println("Enterキー押されたお");
            pc(m.PanelNames[1]);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    // ==================================================================================
    // 画面の遷移
    // ==================================================================================
    public void pc(String str) {                             // 画面遷移の要求を出す
        m.PanelChange((JPanel)this, str);                      // 画面遷移の要求
    }

    // ==================================================================================
    // スレッドの開始
    // ==================================================================================
    public void startThread() {
        if (thread == null) {
            thread = new Thread();
            thread.start();
        }
    }

    // ==================================================================================
    // スレッドの停止
    // ==================================================================================
    public void stopThread() {
		if (thread != null) {
			thread = null;
		}
	}

    @Override
    public void run() {}

    // ==================================================================================
    // マウスイベント
    // ==================================================================================
    @Override
    public void mouseClicked(MouseEvent ev) {
    }

    @Override
    public void mouseEntered(MouseEvent ev) {
    }

    @Override
    public void mouseExited(MouseEvent ev) {
    }

    @Override
    public void mousePressed(MouseEvent ev) {
    }

    @Override
    public void mouseReleased(MouseEvent ev) {
    }
}
