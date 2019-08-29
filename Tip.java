public class Tip {
	private int ten = 0;
	private int one = 0;

	public Tip() {
		this.ten = 10;
	}



	public int totalTip() {
		int tip = 0;
		conversion();
		tip =this.ten * 10 + this.one;
		return tip;
	}


	public void conversion() {
		int t = 0;
		int o = 0;
		if(this.one > 9){
			t = one / 10;
			o = one % 10;

			setTen(ten + t);
			setOne(o);
		}
	}


	//ベットした際に持ちコインから減らす
	public void betTip(int i) {
		int bet = totalTip() - i;
		setOne(bet);
		this.ten =0;
		totalTip();
	}


	//
	public void changeTip(int i) {
		if(i > 0) {
			int w = getOne() + i;
			setOne(w);
			totalTip();
		}else {
			int l = totalTip() - i;
			setOne(l);
			this.ten = 0;
			totalTip();
		}
	}







	public void checkBet(int bet) throws NumberFormatException,betErrorException,betOverException{
		if(bet < 1 || bet > 20) {
			throw new betErrorException ("チップポイントは半角数字1～20を入力してください");
		}else if(bet > totalTip()) {
			throw new betOverException("BETするチップポイントは総計のチップポイント以下で入力してください");
		}
	}


	public int getTen() {
		return ten;
	}

	public void setTen(int ten) {
		this.ten = ten;
	}

	public int getOne() {
		return one;
	}

	public void setOne(int one) {
		this.one = one;
	}
}
