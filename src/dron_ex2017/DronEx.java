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
    //private final int WIDTH  = 500;
    //private final int HEIGHT = 500;
    private Color state[][];
	private int xSize, ySize;
	private int block;
	private int xL, yL, xR, yR;
	private int dxL, dyL, dxR, dyR;
	private boolean liveL, liveR;
	private Thread thread;
	private String message;
	private Font font;

	private int width, height;
	private int countR, countL;

	private void initialize() {
		int i,j;

		for(j = 0; j < ySize; j++) {
			state[0][j] = state[xSize - 1][j] = Color.BLACK;
		}
		for (i = 1; i < xSize - 1; i++) {
			state[i][0] = state[i][ySize - 1] = Color.BLACK;
			for (j = 1; j < ySize - 1; j++) {
				state[i][j] = Color.WHITE;
			}
		}
		xL = yL = 2;
		xR = xSize - 3; yR = ySize - 3;
		dxL = dxR = 0;
		dyL = 1; dyR = -1;
		liveL = liveR = true;
	}

    //-- コンストラクタ --
    public DronEx() {
        setPreferredSize(new Dimension(320, 360));

		xSize = ySize = 80;
		block = 4;
		state = new Color[xSize][ySize];
		message = "Game started!";
		font = new Font("Monospaced", Font.PLAIN, 12);
		setFocusable(true);
		addKeyListener(this);
		Dimension size = getPreferredSize();
		width = size.width; height = size.height;

		startThread();
    }

    public void startThread() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void stopThread() {
		if (thread != null) {
			thread = null;
		}
	}

    @Override
	public void paintComponent(Graphics g) {

		g.clearRect(0, 0, width, height);

		int i, j;
		for (i = 0; i < xSize; i++) {
			for (j = 0; j < ySize; j++) {
				g.setColor(state[i][j]);
				g.fillRect(i * block, j * block, block, block);
			}
		}
		g.setFont(font);
		g.setColor(Color.GREEN.darker());
		g.drawString(message, 2 * block, block * (ySize+3));
		g.setColor(Color.RED.darker());
		g.drawString("Left:  A(L), S(D), D(U), F(R)", 2 * block, block * (ySize + 6));
		g.setColor(Color.BLUE.darker());
		g.drawString("Right: H(L), J(D), K(U), L(R)", 2 * block, block * (ySize + 9));
	}

    //-- オーバーライド(これらがないとコンパイルエラー) --
    @Override
    public void run() {
        Thread thisThread = Thread.currentThread();
		while (thisThread == thread) {
			initialize();
			requestFocus();
			while (liveL && liveR) {
				xL += dxL; yL += dyL;
				if (state[xL][yL] != Color.WHITE) {
					liveL = false;
				} else {
					state[xL][yL] = Color.RED;
				}
				xR += dxR; yR += dyR;
				if (state[xR][yR] != Color.WHITE) {
					liveR = false;
					if(xR == xL && yR == yL) {
						liveL = false;
						state[xL][yL] = Color.MAGENTA.darker();
					}
				} else {
					state[xR][yR] = Color.BLUE;
				}
				if (!liveL) {
					if (!liveR) {
						message = "Draw!";
					} else {
						countR++;
						message = "R won!";
					}
				} else if (!liveR) {
					countL++;
					message = "L won!";
				}
				repaint();
				try{
					Thread.sleep(250);
				} catch(InterruptedException e) {}
			}
			try{
				Thread.sleep(1750);
			} catch(InterruptedException e) {}
		}
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
		switch (key) {
		    case 'A':  dxL = -1; dyL =  0; break;
		    case 'S':  dxL =  0; dyL =  1; break;
		    case 'D':  dxL =  0; dyL = -1; break;
		    case 'F':  dxL =  1; dyL =  0; break;
		    case 'H':  dxR = -1; dyR =  0; break;
		    case 'J':  dxR =  0; dyR =  1; break;
		    case 'K':  dxR =  0; dyR = -1; break;
		    case 'L':  dxR =  1; dyR =  0; break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
