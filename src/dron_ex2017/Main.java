// ==================================================================================
// パッケージ
// ==================================================================================
package dron_ex2017;

// ==================================================================================
// ライブラリ
// ==================================================================================
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Dimension;

// ==================================================================================
// Mainクラス
// ==================================================================================
public class Main extends JFrame {
    // ==================================================================================
    // 変数宣言
    // ==================================================================================
    private int WIDTH = 500;
    private int HEIGHT = 500;
    public String[] PanelNames = {"Menu","Game","Credits", "GameResult"};
    Menu m      = new Menu(this, PanelNames[0]);
    DronEx game;
    Credits c   = new Credits(this, PanelNames[2]);
    JFrame frame;
    Sounds sounds = new Sounds();

    // ==================================================================================
    // 起動直後にメニューを表示 コンストラクタ
    // ==================================================================================
    public Main() {
        this.add(m);                  // 本オブジェクトにMenu mを加える
        this.setSize(WIDTH, HEIGHT);  // Menu mはthisに属してるのでこれでウィンドウサイズを変更する
        this.setResizable(false);     // サイズ変更禁止
        m.setVisible(true);           // メニューの表示
    }

    // ==================================================================================
    // メインオブジェクトの生成
    // ==================================================================================
    public static void main(String[] args) {
        Main main = new Main();                         // Main型オブジェクトの生成
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);   // ☓ボタンで閉じる用に設定
        main.setVisible(true);                          // Mainオブジェクトを表示
    }

    // ==================================================================================
    // 画面遷移 (閉じたいもの, 開始したいもの)
    // ==================================================================================
    public void PanelChange(JPanel jp, String str) {
        System.out.println("閉じたいもの : " + jp.getName());               // デバック出力
        System.out.println("開きたいもの : " + str);                        // デバック出力
        String name = jp.getName();                     // オブジェクトの名前を保存

        // ==============================================================================
        // ウィンドウを閉じる処理
        // ==============================================================================
        if(name == PanelNames[0]) {                     // メニューを閉じる時
            m = (Menu)jp;                               // Menu型にキャスト
            m.setVisible(false);                        // メニューを見えなくする(メモリ上には残る)
        } else if (name == PanelNames[1]) {             // ゲームを閉じたい時
            System.out.println("ゲームウィンドウを閉じた"); // デバック
            game = (DronEx)jp;                          // DronEx型にキャスト
            game.setVisible(false);                     // gameを見えなくする
            frame.setVisible(false);
            game = null;
        } else if (name == PanelNames[2]) {             // 閉じたいものがクレジットウィンドウの時
            c = (Credits)jp;                            // Credits型にキャスト
            c.setVisible(false);                        // クレジットウィンドウを見えなくする
            c = null;                                   // クレジットオブジェクトをリセット
        }

        // ==============================================================================
        // ウィンドウを開く処理
        // ==============================================================================
        if(str == PanelNames[0]) {                      // Menu型オブジェクトを表示する要求を受けたら
            m.setVisible(true);                           // Menu型オブジェクトを表示する
        } else if (str == PanelNames[1]) {              // ゲームを起動する要求を受けたら
            //sounds.stageBgm();
            SwingUtilities.invokeLater(() -> {            // ゲーム開始の処理↓
			    frame = new JFrame("DronEx!");
			    frame.add( new DronEx(this, PanelNames[1], sounds) );
			    frame.pack();
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			    frame.setVisible(true);
            });
        } else if (str == PanelNames[2]) {               // スタッフクレジット
            c = new Credits(this, PanelNames[2]);
            this.add(c);                  // 本オブジェクトにスタッフクレジットを追加
            this.setSize(WIDTH, HEIGHT);  // thisに属してるのでこれでウィンドウサイズを変更する
            this.setResizable(false);     // サイズ変更禁止
            c.setVisible(true);           // メニューの表示
        }
    }

    // ==================================================================================
    // 画面遷移(ゲームの勝敗結果専用 オーバーロードで実装)
    // ==================================================================================
    /*
    public void PanelChange(JPanel jp, String str, String w, String l) {
        // -- デバック --
        System.out.println("閉じたいもの : " + jp.getName());               // デバック出力
        System.out.println("開きたいもの : " + str);                        // デバック出力
        String name = jp.getName();                                       // オブジェクトの名前を保存

        // ==============================================================================
        // ウィンドウを閉じる処理
        // ==============================================================================
        // -- ゲームウィンドウを閉じる --
        if (name == PanelNames[1]) {                    // ゲーム画面を閉じる
            sounds.stageBgmStop();                      // BGM停止
            System.out.println("ゲームウィンドウを閉じた"); // デバック
            game = (DronEx)jp;                          // DronEx型にキャスト
            game.setVisible(false);                     // gameを見えなくする
            frame.setVisible(false);
            game = null;
        }

        // ==============================================================================
        // ウィンドウを開く処理
        // ==============================================================================
        // -- 勝敗結果画面を開く --
        if (str == PanelNames[3]) {                     // 勝敗結果画面を開く
            rs = new ResultScreen(this, w, l);          // オブジェクトの生成
            this.add(rs);                               // オブジェクトの追加
            this.setSize(WIDTH, HEIGHT);                // thisに属してるのでこれでウィンドウサイズを変更する
            this.setResizable(false);                   // サイズ変更禁止
            rs.setVisible(true);
            System.out.println("ウィンドウを開く処理");
        }
    }
    */
}
