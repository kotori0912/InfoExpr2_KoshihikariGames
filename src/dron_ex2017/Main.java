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
    public String[] PanelNames = {"Menu","Game","Credits"};
    Menu m      = new Menu(this, PanelNames[0]);
    DronEx game = new DronEx(this, PanelNames[1]);

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
    // 画面遷移
    // ==================================================================================
    public void PanelChange(JPanel jp, String str) {
        System.out.println(jp.getName());               // デバック出力
        System.out.println(str);                        // デバック出力
        String name = jp.getName();                     // オブジェクトの名前を保存

        /* ウィンドウを閉じる処理 */
        if(name == PanelNames[0]) {                     // メニューから抜ける
            m = (Menu)jp;                               // Menu型にキャスト
            //System.out.println("ウィンドウを閉じる");
            m.setVisible(false);                        // メニューを見えなくする(メモリ上には残る)
        } else if (name == PanelNames[1]) {             // Gameから抜ける
            game = (DronEx)jp;                          // DronEx型にキャスト
            game.setVisible(false);                     // gameを見えなくする
        } 

        /* ウィンドウを開く処理 */
        if(str == PanelNames[0]) {                      // Menu型オブジェクトを表示する要求を受けたら
            m.setVisible(true);                           // Menu型オブジェクトを表示する
        } else if (str == PanelNames[1]) {              // ゲームを起動する要求を受けたら
            //System.out.println("ゲーム起動");               // デバック出力
            SwingUtilities.invokeLater(() -> {            // ゲーム開始の処理↓
			    JFrame frame = new JFrame("DronEx!");
			    frame.add( new DronEx(this, PanelNames[1]) );
			    frame.pack();
			    frame.setVisible(true);
            });
        } else if (str == PanelNames[2]) {               // スタッフクレジット

        }
    }
}
