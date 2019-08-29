import java.util.Scanner;

public class BigOrSmall {
	Scanner scn = new Scanner(System.in);
	//BETしたチップ数
	int iBet;

	//Big or Small選択時の変数
	int boss;

	Tramp tramp;

	//Tip
	Tip tip;

	//初めから再開
	boolean b1 = false;
	//カード選択から再開
	boolean b2 = false;

	public void start() {
		Scanner scn = new Scanner(System.in);

		//1．ゲーム開始時、[10]チップを10枚、総計100点を保有している（
		//表示例、総計:100（[10]:10枚、[1]:0枚））。

		this.tip = new Tip();

		//2．カードが1枚、配られる(カード内容は表示される)
		//
		//チップ枚数と配られたカードを表示する
		while (this.b1 == false) {
			Tramp tramp = new Tramp();
			this.tramp = tramp;
			this.tramp.cardDeck();
			this.tramp.shuffle();

			System.out.println("*****チップ枚数とカード*****");
			System.out.println(
					"総計:" + this.tip.totalTip() + "([10]:" + this.tip.getTen() + "枚,[1]:" + this.tip.getOne() + "枚)");
			System.out.println("現在のカード:" + this.tramp.deck.get(0).markTrance() + this.tramp.deck.get(0).getMath());
			System.out.println("****************************");

			//3．「Big or Small」を始める前に、チップを何枚BETするのか、ユーザが決定する。
			//画面に適切な文言を表示する（表示例、■BET枚数選択）。
			//チップは最低1ポイントから最大20ポイントまでBETできる。
			//BETするチップポイントは画面から入力できる

			//4．入力したチップポイントをチェックする。
			//・入力したチップポイントが、もし半角数字の1〜20以外の文字が入力された場合、
			//エラーメッセージを表示させて（表示例、チップポイントは半角数字の1〜20を入力してください）
			//上記の3に戻る。
			//・入力したチップポイントが、もし総計のチップポイントより多く入力（BET）した場合、
			//エラーメッセーを表示させて（表示例、BETするチップポイントは総計のチップポイント以下で入力してください）
			//上記の3に戻る（例、現在のチップポイントの総計が15の場合、BETできるチップポイントは15までになる）。

			boolean b = false;
			while (b == false) {
				System.out.println("■BET枚数選択");
				System.out.println("BETするチップ数を入力して下さい(最低1～20)");
				try {
					//nextIntでString値を入力すると無限ループしたためnext()→Integerへ変換
					String bet = scn.next();
					this.iBet = Integer.parseInt(bet);
					this.tip.checkBet(this.iBet);

					//ベットしたチップを総数から減らす
					this.tip.betTip(this.iBet);

					break;
				} catch (NumberFormatException e) {
					System.out.println(e + "半角数字の1~20を入力してください");
				} catch (betErrorException | betOverException e) {
					System.out.println(e);
				}
			}

			//5．現在のカードに対し、次に配られるカードが大きい(Big)カードになるのか、
			//小さい(Small)カードになるのかを選択できる。
			//画面に適切な文言を表示する（表示例、■Big or Small選択）。
			//Big or Smallの選択を画面から入力できる 。
			//6．Big or Smallの選択が、もし半角数字の0あるいは1以外の文字が入力された場合、
			//エラーメッセージを表示させて（表示例、半角数字の0あるいは1のみ入力してください）
			//上記の5に戻る。

			//7．「Big or Small」の選択後に、カードが1枚、配られる。

			this.b2 = false;
			while (this.b2 == false) {

				bigOrSmall();

				//8．「Big or Small」に正解した場合はBETしたチップポイントが倍になり
				//外れた場合は没収される 。

				//現在のカード > 引いたカード かつ Bigを選択していた場合 もしくは現在のカード < 引いたカード かつ Smallを選択していた場合
				//正解した場合
				if (this.tramp.deck.get(0).getMath() < this.tramp.deck.get(1).getMath() && this.boss == 0
						|| this.tramp.deck.get(0).getMath() >= this.tramp.deck.get(1).getMath() && this.boss == 1) {
					System.out.println("Win");
					this.iBet = this.iBet * 2;
					System.out.println("チップ" + this.iBet + "枚を獲得しました");

					boolean bo = false;
					while (bo == false) {

						try {
							System.out.println("[獲得したチップ" + this.iBet + "枚でBig or Smallを続けますか?]: 0:Yes 1:No");

							String str = scn.next();
							int y = Integer.parseInt(str);
							bosCheck(y);

							if (y == 0) {

								this.tramp.listDelete();

							} else {

								tip.changeTip(this.iBet);
								this.iBet = 0;
								this.b2 = true;

								int i = gameRevenge();

								if (i == 0) {

									this.b1 = false;

								} else {

									endGame();

								}

							}
						} catch (Exception e) {
							System.out.println(e + "半角数字の0か1を入力してください");
						}
						break;
					}

					//引き分け、もしくは外れた場合
				} else {
					System.out.println("Lose");
					System.out.println("");

					System.out.println("チップ" + this.iBet + "枚が没収されました");

					System.out.println(this.tip.totalTip());

					int i = gameRevenge();

					if (i == 0) {

						this.b1 = false;
						break;

					} else {
						endGame();
					}

					//もっているチップが0だった場合終了する
					if (this.tip.totalTip() == 0) {
						System.out.println("チップがもうありません");
						endGame();
					}
				}
			}
		}
	}

	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////

	//選択入力、表示用メソッド
	public void bigOrSmall() {
		boolean i = false;

		while (i == false) {
			try {
				System.out.println("■Big or Small選択");
				System.out.println("現在のカード:" + this.tramp.deck.get(0).markTrance() + this.tramp.deck.get(0).getMath());
				System.out.println("[Big or Small]: 0:Big 1:Small");

				String bosc = scn.next();
				//nextIntでString値を入力すると無限ループしたためnext()→Integerへ変換
				this.boss = Integer.parseInt(bosc);
				bosCheck(this.boss);

				i = true;
			} catch (Exception e) {
				System.out.println(e + "半角数字の0か1を入力してください");
			}
		}

		System.out.println("********Big or Small********");
		System.out.println("BET数:" + iBet);
		System.out.println("あなたの選択:" + bosTrance());
		System.out.println("現在のカード" + tramp.deck.get(0).markTrance() + tramp.deck.get(0).getMath());
		System.out.println("引いたカード:" + tramp.deck.get(1).markTrance() + tramp.deck.get(1).getMath());
		System.out.println(tramp.deck.get(1).markTrance() + tramp.deck.get(1).getMath() + 'は'
				+ tramp.deck.get(0).markTrance() + tramp.deck.get(0).getMath() + "より"
				+ bosJudg(tramp.deck.get(1).getMath(), tramp.deck.get(0).getMath()));
	}

	//ゲームの続行用メソッド
	public int gameRevenge() {
		int i = 0;
		boolean b = false;
		while (b == false) {
			System.out.println("******現在のチップ枚数******");
			System.out.println("総計:" + this.tip.totalTip() + "([10]:" + tip.getTen() + ",[1]:" + tip.getOne() + ')');
			System.out.println("****************************");
			System.out.println("[ゲームを続けますか?]: 0:Yes 1:No");

			try {
				String str = scn.next();
				i = Integer.parseInt(str);
				bosCheck(i);
				break;
			} catch (Exception e) {
				System.out.println(e + "半角数字の0か1を入力してください");
			}
		}
		return (i);
	}

	//Big Or Small 選択時の1,2以外を選択した場合
	public void bosCheck(int i) throws differentNumberException {
		if (i < 0 || i > 1) {
			throw new differentNumberException("");
		}
	}

	//Big or Small選択の変換
	public String bosTrance() {
		String i = "";
		String s = "Small";
		String b = "Big";

		if (this.boss == 0) {
			i = b;
		} else if (this.boss == 1) {
			i = s;
		}
		return i;
	}

	//カードの比較表示用メソッド
	public String bosJudg(int i, int y) {
		String r = "";
		String s = "Small";
		String b = "Big";

		if (i > y) {
			r = b;
		} else {
			r = s;
		}
		return r;
	}

	//ゲーム終了メソッド
	public void endGame() {
		this.b1 = true;
		this.b2 = true;

		System.out.println("");
		System.out.println("END");
	}

	//9．「Big or Small」に正解した場合、倍になったチップポイントを、次のBETに利用し
	//連続してゲームを行うかどうか選択できる
	//（この場合、最大チップポイントの20を超えてBETすることが可能）
	//
	//画面に適切な文言を表示する
	//（表示例、[獲得したチップ30枚でBig or Smallを続けますか？]: 0:Yes 1:No ）。
	//Yes or No の選択を画面から入力できる 。

	//10．Yes or No の選択が、もし半角数字の0あるいは1以外の文字が入力された場合
	//エラーメッセージを表示させて（表示例、半角数字の0あるいは1のみ入力してください）
	//上記の9に戻る

	//11．Yes or No の選択で、「Yes」を選択した場合、再度、次に配られるカードが大きい(Big)カードになるのか
	//小さい(Small)カードになるのかを選択する（表示例は以下の画像を参照）。

	//12．上記の9で「Big or Smallを続けますか？」について「No」を選択した場合、あるいは「Big or Small」に外れた場合は
	//「Big or Small」ゲームを続けるかどうか選択する。
	//画面に適切な文言を表示する（表示メッセージの例は以下画像を参照）。
	//Yes or No の選択を画面から入力できる 。

	//13．Yes or No の選択が、もし半角数字の0あるいは1以外の文字が入力された場合
	//エラーメッセージを表示させて（例、半角数字の0あるいは1のみ入力してください）
	//上記の12に戻る。

	//14．上記の12で「ゲームを続けますか？」について、「Yes」を選択した場合
	//カードデッキについては初期状態に戻し
	//現在のチップ数を保持したままで初期状態（上記の2）に戻り、処理を繰り返す

	//15．上記の12で「ゲームを続けますか？」について、「No」を選択した場合
	//プログラムを終了する（表示例は以下の画像参照）。
	//
	//総計について
	//

	//16．チップの総計が0になった場合は、ゲームが終了する。

	//17．1回目の「Big or Small」に正解した場合、獲得したチップポイントが総計にプラスされる
	//（最初の総計：100、BETしたチップポイント：20、獲得したチップポイント：40、BET後の総計：120）

	//18．1回目と2回目の「Big or Small」に正解した場合、獲得したチップポイントが総計にプラスされる
	//（最初の総計：100、1回目のBETしたチップポイント：20、1回目で獲得したチップポイント：40、
	//2回目のBETしたチップポイント：40、2回目で獲得したチップポイント：80、2回目のBET後の総計：160）。

	//19．1回目の「Big or Small」に正解し、2回目の「Big or Small」に外れた場合、BETしたチップポイントが総計からマイナスされる
	//（最初の総計：100、1回目のBETしたチップポイント：20、2回目のBET後の総計：80）。

	//20．1回目の「Big or Small」に外れた場合、BETしたチップポイントが総計からマイナスされる
	//（最初の総計：100、BETしたチップポイント：20、BET後の総計：80）。

	//21．1回目と2回目の「Big or Small」に外れた場合、BETしたチップポイントが総計からマイナスされる
	//（最初の総計：100、1回目と2回目のBETしたチップポイント：20、2回目のBET後の総計：60）。

}
