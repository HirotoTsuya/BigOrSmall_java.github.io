
public class Card {
	private int mark; //0=スペード,1=ハート,2=ダイヤ,3=クローバー
	private int math; //13までの数字

	private String s = "スペード";
	private String h = "ハート";
	private String d = "ダイヤ";
	private String k = "クローバー";


	public Card(int math,int mark) {
	setMath(math);
	setMark(mark);
	}

	public String markTrance() {
		String i = "";
		if(this.mark == 0) {
			i = this.s;
		}else if(this.mark == 1){
			i = this.h;
		}else if(this.mark == 2) {
			i = this.d;
		}else if(this.mark == 3) {
			i = this.k;
		}
		return i;

	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}






}
