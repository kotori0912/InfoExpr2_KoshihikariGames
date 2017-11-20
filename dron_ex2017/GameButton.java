// ==================================================================================
// このクラスはメニューのボタン用のものである(JButtonを継承したカスタムボタン)
// ==================================================================================
package dron_ex2017;

// ==================================================================================
// ライブラリ
// ==================================================================================
import java.applet.AudioClip;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;


public class GameButton extends JButton implements MouseListener, ActionListener {
    /* 変数宣言 */
    Sounds sounds = new Sounds();    // 音オブジェクトの生成

    // ==================================================================================
    // コンストラクタ
    // ==================================================================================
    public GameButton(String s, ImageIcon i) {
        super(s, i);                                   // 親クラス(JButton)のコンストラクタに名前と画像をセット
        //System.out.println("GameStartButtonメソッド");  // デバッグメッセージ
        addMouseListener(this);                        // マウスの入力を受け付ける
    }

    // ==================================================================================
    // マウスイベント
    // ==================================================================================
    @Override
    public void mouseClicked(MouseEvent ev) {       // マウスがクリックされた時
    }

    @Override
    public void mouseEntered(MouseEvent ev) {       // マウスポインタがボタン内に入った時
        sounds.buttonHighlight();                   // 音再生
    }

    @Override
    public void mouseExited(MouseEvent ev) {
    }

    @Override
    public void mousePressed(MouseEvent ev) {        // マウスが押された時
        sounds.buttonPushed();                       // プッシュ音再生
    }

    @Override
    public void mouseReleased(MouseEvent ev) {

    }

    @Override
    public void actionPerformed(ActionEvent ae) {}
}
