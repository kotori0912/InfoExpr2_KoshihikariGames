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
import java.awt.geom.AffineTransform;

import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.lang.Thread;

// ==================================================================================
// Menuクラス
// ==================================================================================
public class Menu extends JPanel implements KeyListener {
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

        /* ゲーム開始催促ラベルの生成 */
        title = new JLabel("Press Enter key!!");
        title.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 30));
        title.setBounds(120, 200, 400, 400);
        this.add(title);
        /* ラベルの点滅 */
        timer = new Timer(600, new ActionListener() {
            boolean flag;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag) {
                    title.setForeground(Color.BLACK);
                    flag = false;
                } else {
                    title.setForeground(Color.WHITE);
                    flag = true;
                }
            }
        });
        timer.start();

        /* 背景画像の読み込み */
        try {
            image = ImageIO.read(new File("images/menu.png"));
        } catch (IOException e) {
            System.out.println("画像読み込み不可");
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
    public void keyPressed(KeyEvent e) {
        int cnt = 0;
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {              // エンターキーでゲームスタート
            //System.out.println("Enterキー押されたお");
            timer = new Timer(100, new ActionListener() {
                boolean flag;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (flag) {
                        title.setForeground(Color.BLACK);
                        flag = false;
                    } else {
                        title.setForeground(Color.WHITE);
                        flag = true;
                    }
                }
            });
            timer.start();
            pc(m.PanelNames[1]);
        }
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    // ==================================================================================
    // 画面の遷移
    // ==================================================================================
    public void pc(String str) {                             // 画面遷移の要求を出す
        m.PanelChange((JPanel)this, str);                      // 画面遷移の要求
    }
}
