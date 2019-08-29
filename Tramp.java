import java.util.ArrayList;
import java.util.Collections;

public class Tramp {
	ArrayList<Card> deck = new ArrayList<Card>();
	final int math = 13;
	final int mark = 4;

	public void Tramp() {
	}

	public void cardDeck() {
		for(int i =1; i < this.math;i++) {
			for(int j = 0; j < this.mark;j++) {
				deck.add(new Card(i,j));
			}
		}
	}


	public void shuffle() {
		//deck内のインスタンスをシャッフル
		Collections.shuffle(deck);

	}

	public void listDelete() {
		deck.remove(0);
		deck.remove(1);
	}
}
