package dron_ex2017;

//----------------------------------------------------
//ライブラリの追加
//----------------------------------------------------
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.util.Random;
import javax.swing.ImageIcon;

//====================================================
//クラス
//====================================================

public class DronEx extends JPanel implements Runnable, KeyListener,ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

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
	private int countR, countL, countD;  //　勝敗分カウント
	private Font font, fontE, fontItem, fontCt;       // フォント
	private int margin = 100;           // 余白
	private int flag = 0;
	private int wid1, wid2;

	// -- 追加(2017.11.15 by s15t212) --
	private GameButton btnC, btnQ, btnQuit;                      // 音が出るボタン

	private ImageIcon backToMenu_n, backToMenu_h, backToMenu_p;  // メニューに戻るボタンの画像
	private ImageIcon replay_n, replay_h, replay_p;              // メニューに戻るボタンの画像
	private ImageIcon pause_n, pause_h, pause_p;                 // ゲームのポーズボタン
	private Main m;             // Main型オブジェクト(これがないと画面遷移できない)
	private int buttonW = 160;  // ボタンの幅
	private int buttonH = 40;   // ボタンの高さ
	private Sounds sounds;

	//Randomクラスのインスタンス化
	Random rnd = new Random();
	private int ranX_1, ranY_1;
	private int ranX_2, ranY_2;

	File file = new File("/Users/itoutakumi/Desktop/InfoExpr2_KoshihikariGames-s15t212/src/dron_ex2017/test.txt");
	// File file = new File("C:\\Users\\guite\\Desktop\\InfoExpr2_KoshihikariGames-s15t212\\src\\dron_ex2017\\test.txt"); //自分の環境で変える必要あり
	String[] Count = new String[3];

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
	public DronEx(Main const_main, String s, Sounds soundObject) throws IOException {

		int k = 0;
		if (checkBeforeReadfile(file)){
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str;
			while((str = br.readLine()) != null){	 //一行ずつ見ていく
				Count[k] = str;    //文字列を配列に格納
				k++;
			}
			br.close();
       	}

		countL = Integer.parseInt(Count[0]);
		countR = Integer.parseInt(Count[1]);
		countD = Integer.parseInt(Count[2]);

		// -- ↓追加↓ --
		m = const_main;                  // Main型オブジェクトの格納(これがないと画面遷移できない)
		sounds = soundObject;            // サウンドオブジェクト取得
		sounds.stageBgm();               // ステージBGM再生
		this.setName(m.PanelNames[1]);   // オブジェクト名のセット(画面遷移に必要)
		// -- ↑追加↑ --

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
		font = new Font("Monospaced", Font.PLAIN, 24);
		setFocusable(true);
		addKeyListener(this);
		Dimension size = getPreferredSize();
		width = size.width; height = size.height;

		this.setLayout(null);

		// -- ゲームの中断 --
		// サイズ変更OK?
		pause_n = new ImageIcon("images/pauseButton/pauseButton_n.png");
		pause_h = new ImageIcon("images/pauseButton/pauseButton_h.png");
	    pause_p = new ImageIcon("images/pauseButton/pauseButton_p.png");
		btnQuit = new GameButton("中断", pause_n);
		btnQuit.setPressedIcon(pause_p);                                      // 押された時の状態
        btnQuit.setRolloverIcon(pause_h);                                     // ロールオーバー時の状態
        btnQuit.setContentAreaFilled(false);                                  // デフォルトボタンの中身を消す
        btnQuit.setBorderPainted(false);                                      // デフォルトボタンの枠線を消す
		btnQuit.addActionListener(this);
		btnQuit.setBounds( 17 * (block + 10), block * (ySize + 5),buttonW, buttonH);
		this.add(btnQuit);                                                    // ゲーム開始時から中断ボタンを表示

		// -- ゲームを続ける --
		// サイズ変更OK?
		replay_n = new ImageIcon("images/replayButton/replayButton_n.png");
		replay_h = new ImageIcon("images/replayButton/replayButton_h.png");
		replay_p = new ImageIcon("images/replayButton/replayButton_p.png");
		btnC = new GameButton("続行", replay_n);
		btnC.setPressedIcon(replay_p);                                  // 押された時の状態
        btnC.setRolloverIcon(replay_h);                                 // ロールオーバー時の状態
        btnC.setContentAreaFilled(false);                                  // デフォルトボタンの中身を消す
        btnC.setBorderPainted(false);                                      // デフォルトボタンの枠線を消す
		btnC.addActionListener(this);
		btnC.setBounds( 10 * (block + 10), block * (ySize + 5),buttonW, buttonH);
		this.add(btnC);

		// -- ゲームを終了してメニューに戻る --
		// サイズ変更OK?
		backToMenu_n = new ImageIcon("images/backMenuButton_game/backToMenu_g_n.png");
		backToMenu_h = new ImageIcon("images/backMenuButton_game/backToMenu_g_h.png");
		backToMenu_p = new ImageIcon("images/backMenuButton_game/backToMenu_g_p.png");
		btnQ = new GameButton("中止", backToMenu_n);
		btnQ.setPressedIcon(backToMenu_p);                                  // 押された時の状態
        btnQ.setRolloverIcon(backToMenu_h);                                 // ロールオーバー時の状態
        btnQ.setContentAreaFilled(false);                                  // デフォルトボタンの中身を消す
        btnQ.setBorderPainted(false);                                      // デフォルトボタンの枠線を消す
		btnQ.addActionListener(this);
		btnQ.setBounds( 23 * (block + 10), block * (ySize + 5),buttonW, buttonH);
		this.add(btnQ);

		startThread();
	}
	/* ファイルがあるか確認 */
	private static boolean checkBeforeReadfile(File file){
		if (file.exists()){
			if (file.isFile() && file.canRead()){
				return true;
			}
		}
		return false;
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
		g.setColor(Color.GREEN.darker());
		g.fillRect(50, 100, 250, 20);    //障害物の設置
		g.fillRect(100, 250, 250, 20);    //障害物の設置

		String Item1 = "速"; //速い
		String Item2 = "遅"; //遅い

		fontItem = new Font("Monospaced", Font.PLAIN, 100);    //フォント設定
		FontMetrics fm = g.getFontMetrics(fontItem);
		wid1 = fm.stringWidth(Item1);
		wid2 = fm.stringWidth(Item2);

		g.setColor(Color.BLACK);
		g.setFont(fontItem);
		g.drawRect(ranX_1, ranY_1 - wid1 + 10, wid1, wid1);    //"速"の周りを囲む四角形を描画
		g.drawString(Item1, ranX_1, ranY_1);    //"速"を描画
		g.drawRect(ranX_2, ranY_2 - wid2 + 10, wid2, wid2);    //"遅"の周りを囲む四角形を描画
		g.drawString(Item2, ranX_2, ranY_2);    //"遅"を描画

		g.setFont(font);
		g.setColor(Color.GREEN.darker());
		g.drawString(message, 2 * block, block * (ySize + 10));

		fontE = new Font("Monospaced", Font.PLAIN, 12);    //フォント設定
		g.setColor(Color.RED.darker());
		g.setFont(fontE);
		g.drawString("赤:  A(←), Z(↓), W(↑), S(→)", 2 * block, block * (ySize + 18));
		g.setColor(Color.BLUE.darker());
		g.drawString("青:  J(←), M(↓), I(↑), K(→)", 2 * block, block * (ySize + 21));

		fontCt = new Font("Monospaced", Font.PLAIN, 14);
		g.setColor(Color.BLACK);
		g.setFont(fontCt);
		g.drawString(countL + "対" + countR + ":"+ countD + "分", block * (xSize + 2), ySize / block);

	}

	//-- オーバーライド(これらがないとコンパイルエラー) --
	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();

		while (thisThread == thread) {
			this.remove(btnC);        // 継続ボタンを消す
			this.remove(btnQ);        // ゲームを終了するボタンを消す
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

				if(yL >= (100 / block) && yL <= (120 / block) && xL >= (50 / block) && xL <= (300 / block)) {
					sounds.explosion();
					liveL = false;
				}
				else if(yL >= (250 / block) && yL <= (270 / block) && xL >= (100 / block) && xL <= (350 / block)) {
					sounds.explosion();
					liveL = false;
				}
				else if(yR >= (100 / block) && yR <= (120 / block) && xR >= (50 / block) && xR <= (300 / block)) {
					sounds.explosion();
					liveR = false;
				}
				else if(yR >= (250 /block) && yR <= (270 / block) && xR >= (100 / block) && xR <= (350 /block)) {
					sounds.explosion();
					liveR = false;
				}

				if (!liveL) {
					if (!liveR) {
						countD++;
						message = "Draw!";
						this.add(btnC);
						this.add(btnQ);
						this.remove(btnQuit);
					} else {
						countR++;
						message = "青 won!";
						this.add(btnC);
						this.add(btnQ);
						this.remove(btnQuit);
					}
				} else if (!liveR) {
					countL++;
					message = "赤 won!";
					this.add(btnC);
					this.add(btnQ);
					this.remove(btnQuit);
				}

				repaint();

				try {

					if(yL >= ((ranY_1 - wid1 + 10) / block) && yL <= ((ranY_1 + 10)/ block) && xL >= (ranX_1 / block) && xL <= ((ranX_1 + wid1) / block)) {
						Thread.sleep(50);
					}
					else if(yL >= ((ranY_2 - wid2 + 10) / block) && yL <= ((ranY_2 + 10) / block) && xL >= (ranX_2 / block) && xL <= ((ranX_2 + wid2) / block)) {
						Thread.sleep(500);
					}
					else if(yR >= ((ranY_2 - wid2 + 10) / block) && yR <= ((ranY_2 + 10) / block) && xR >= (ranX_2 / block) && xR <= ((ranX_2 + wid2) / block)) {
						Thread.sleep(500);
					}
					else if(yR >= ((ranY_1 - wid1 + 10) / block) && yR <= ((ranY_1 + 10) / block) && xR >= (ranX_1 / block) && xR <= ((ranX_1 + wid1) / block)) {
						Thread.sleep(50);
					}
					else {
						Thread.sleep(250);    // ここの数値を小さくすれば難易度が上がる
					}

				} catch(InterruptedException e) {}
			}
			try {
				sounds.stageBgmStop();
				stopThread();
				Thread.sleep(1);
				message = "ゲームを続けます!";

			} catch(InterruptedException e) {

			}
			// message = "ゲームを続けます!";  // ← 必要がなくなった...
		}


		// ==================================================================================
		// txtファイルへの書き込み
		// ==================================================================================

		try{
			if (checkBeforeWritefile(file)){
				PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(file)));

				p.println(countL);
				p.println(countR);
				p.println(countD);

				p.close();
			}else{
				System.out.println("ファイルに書き込めません");
			}
		}catch(IOException e){
			System.out.println(e);
		}
	}

	/*ファイルに書き込めるか否か*/
	private static boolean checkBeforeWritefile(File file){
		if (file.exists()){
			if (file.isFile() && file.canWrite()){
				return true;
			}
		}
		return false;
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

	// ↓↓↓↓↓↓↓↓↓↓↓↓↓追加(2017.11.15 by s15t212)↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// ==================================================================================
    // ボタン操作
    // ==================================================================================
	@Override
    public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnQuit) {           // 中断してメニューに戻る
			System.out.println("中断してメニューに戻る");
			sounds.stageBgmStop();                    // ステージBGM停止
			pc(m.PanelNames[0]);                      // Mainオブジェクトにメニュー画面遷移を要求
		} else if (event.getSource() == btnC) {       // ゲームを続ける
			System.out.println("ゲームを続ける");
			sounds.stageBgmStop();				      // ステージBGM停止
			pc(m.PanelNames[1]);                      // Mainオブジェクトにゲーム起動を要求
		} else if (event.getSource() == btnQ) {       // ゲームを終了してメニューに戻る
			System.out.println("ゲームを終了してメニューに戻る");
            System.out.println("スコアをリセットします");

			PrintWriter p;
			try {
				p = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				p.println(0);
				p.println(0);
				p.println(0);
                p.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sounds.stageBgmStop();                    // ステージBGM停止
			pc(m.PanelNames[0]);                      // Mainオブジェクトにメニュー画面遷移を要求
		}
	}

	// ==================================================================================
    // 画面の遷移
    // ==================================================================================
    public void pc(String str) {                             // 画面遷移の要求を出す
        m.PanelChange((JPanel)this, str);                      // 画面遷移の要求
    }
}
