
package dron_ex2017;
//----------------------------------------------------
//ライブラリの追加
//----------------------------------------------------
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.Random;

//====================================================
//クラス
//====================================================

public class DronEx extends JPanel implements Runnable, KeyListener {
	//-- 変数宣言 --
	private final int WIDTH  = 500;     // ここは変更しないこと(ただしゲーム表示部の大きさは変えて良い)
	private final int HEIGHT = 500;     // ここは変更しないこと(ただしゲーム表示部の大きさは変えて良い)
	private Color state[][];
	private int xSize, ySize;           // 黒い枠の大きさ
	private int block;
	private int xL, yL, xR, yR;
	private int dxL, dyL, dxR, dyR;
	private boolean liveL, liveR;       // 右プレーヤーと左プレーヤーの勝敗判定
	private Thread thread;              // スレッド
	private String message;             // メッセージ
	private int width, height;
	private int countR, countL;
	private Font font, fontItem;       // フォント
	private int margin = 110;           // 余白
	private int flag = 0;
	private int wid1, wid2;

	//Randomクラスのインスタンス化
	Random rnd = new Random();
	private int ranX_1, ranY_1;
	private int ranX_2, ranY_2;


	/* 黒い枠の作成 */
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

	/* コンストラクタ */
	public DronEx() {

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		xSize = ySize = 100;
		block = 4;

		int randomWid = (xSize * block) - (margin * 2) + 1;    //ボックスの縦横ともにmarginだけ狭めた距離

		ranX_1 = rnd.nextInt(randomWid) + margin;    //乱数の生成 ranX_1
		ranY_1 = rnd.nextInt(randomWid) + margin;    //乱数の生成 ranY_1

		while(flag == 0) {
			ranX_2 = rnd.nextInt(randomWid) + margin;    //乱数の生成 ranX_2
			ranY_2 = rnd.nextInt(randomWid) + margin;    //乱数の生成 ranY_2
			if((Math.abs(ranX_1 - ranX_2)) >= margin && (Math.abs(ranY_1 - ranY_2)) >= margin) {//アイテム同士の重複防止
				flag = 1;
			}
		}

		state = new Color[xSize][ySize];
		message = "Game started!";
		font = new Font("Monospaced", Font.PLAIN, 12);
		setFocusable(true);
		addKeyListener(this);
		Dimension size = getPreferredSize();
		width = size.width; height = size.height;
		startThread();
	}

	/* スレッドのスタート */
	public void startThread() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	/* スレッドの停止 */
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

		String Item1 = "速";
		String Item2 = "遅";

		fontItem = new Font("Monospaced", Font.PLAIN, 100);    //フォント設定
		FontMetrics fm = g.getFontMetrics(fontItem);

		wid1 = fm.stringWidth(Item1);
		wid2 = fm.stringWidth(Item2);

		g.setFont(fontItem);
		g.drawRect(ranX_1, ranY_1 - wid1 + 10, wid1, wid1);    //"速"の周りを囲む四角形を描画
		g.drawString(Item1, ranX_1, ranY_1);    //"速"を描画

		g.drawRect(ranX_2, ranY_2 - wid2 + 10, wid2, wid2);    //"遅"の周りを囲む四角形を描画
		g.drawString(Item2, ranX_2, ranY_2);    //"遅"を描画

		g.setFont(font);
		g.setColor(Color.GREEN.darker());
		g.drawString(message, 2 * block, block * (ySize+3));
		g.setColor(Color.RED.darker());
		g.drawString("赤:  A(←), Z(↓), W(↑), S(→)", 2 * block, block * (ySize + 6));
		g.setColor(Color.BLUE.darker());
		g.drawString("青: J(←), M(↓), I(↑), K(→)", 2 * block, block * (ySize + 9));
		g.setColor(Color.BLACK);
		g.drawString(countL+ "対" +countR, 2 * block, block * (ySize + 12));
		/*String ran = Double.toString(ranX_2);
		String ran1 = Double.toString(ranY_2);
		String wid = Double.toString(wid2);

		g.drawString("ranX_2 = "+ran, 2 * block, block * (ySize + 12));
		g.drawString("ranY_2 = "+ran1, 2 * block, block * (ySize + 15));
		g.drawString("wid2 = "+wid, 2 * block, block * (ySize + 18));
		 */
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
						message = "青 won!";
					}
				} else if (!liveR) {
					countL++;
					message = "赤 won!";
				}

				repaint();

				try {

					if(yL >= ((ranY_1 - wid1) / block) && yL <= ((ranY_1 + 10)/ block) && xL >= (ranX_1 / block) && xL <= ((ranX_1 + wid1) / block)) {
						Thread.sleep(50);
					}
					else if(yL >= ((ranY_2 - wid2) / block) && yL <= ((ranY_2 + 10) / block) && xL >= (ranX_2 / block) && xL <= ((ranX_2 + wid2) / block)) {
						Thread.sleep(500);
					}
					else if(yR >= ((ranY_2 - wid2) / block) && yR <= ((ranY_2 + 10) / block) && xR >= (ranX_2 / block) && xR <= ((ranX_2 + wid2) / block)) {
						Thread.sleep(500);
					}
					else if(yR >= ((ranY_1 - wid1) / block) && yR <= ((ranY_1 + 10) / block) && xR >= (ranX_1 / block) && xR <= ((ranX_1 + wid1) / block)) {
						Thread.sleep(50);
					}
					else {
						Thread.sleep(250);    // ここの数値を小さくすれば難易度が上がる
					}

				} catch(InterruptedException e) {}
			}
			try {
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
		case 'Z':  dxL =  0; dyL =  1; break;
		case 'W':  dxL =  0; dyL = -1; break;
		case 'S':  dxL =  1; dyL =  0; break;
		case 'J':  dxR = -1; dyR =  0; break;
		case 'M':  dxR =  0; dyR =  1; break;
		case 'I':  dxR =  0; dyR = -1; break;
		case 'K':  dxR =  1; dyR =  0; break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
