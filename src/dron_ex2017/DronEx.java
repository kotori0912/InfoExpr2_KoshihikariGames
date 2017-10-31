//----------------------------------------------------
// パッケージ
//----------------------------------------------------
package dron_ex2017;

//----------------------------------------------------
// ライブラリの追加
//----------------------------------------------------
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//====================================================
// クラス
//====================================================
public class DronEx extends JPanel implements Runnable, KeyListener {
    //-- 変数宣言 --
    private final int WIDTH  = 500;
    private final int HEIGHT = 500;

    //-- コンストラクタ --
    public DronEx() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setFocusable(true);
		addKeyListener(this);
    }

    //-- オーバーライド(これらがないとコンパイルエラー) --
    @Override
    public void run() {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
