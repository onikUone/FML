package main;

public class FuzzySet {

	//Field *************************************
	static int KK[] = {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,6,6,6,6,6,6,7,7,7,7,7,7,7};					//メンバシップの時のK
	static int kk[] = {1,1,2,1,2,3,1,2,3,4,1,2,3,4,5,1,2,3,4,5,6,1,2,3,4,5,6,7};					//メンバシップの時のk

	int LargeK[];
	int SmallK[];

	static double eta = 0.5;

	static double w[] = {100.0, 1.0, 1.0};	//荷重和重み
	// ******************************************

	//Constructor *******************************
	public FuzzySet() {}

	public FuzzySet(int _maxFnum) {
		this.KKkk(_maxFnum);
	}
	// ******************************************

	//Method ************************************

	//ファジィ分割の初期化
	public void KKkk(int _maxFnum) {
		int arrayNum = 0;
		for(int i = 0; i < _maxFnum; i++) {
			arrayNum += (i + 1);
		}
		LargeK = new int[arrayNum];
		SmallK = new int[arrayNum];

		int num = 0;
		for(int i = 0; i < _maxFnum; i++) {
			for(int j = 0; j < (i + 1); j++) {
				LargeK[num] = i + 1;
				SmallK[num] = j + 1;
				num++;
			}
		}
	}

	//メンバシップ関数
	public static double calcMembership(int _num, double _x) {
		double uuu = 0.0;
		if(_x < 0) {
			return 1.0;
		}

		if(_num == 0) {	//don't care
			uuu = 1.0;
		} else if(_num > 0) {
			double a = (double)(kk[_num] - 1) / (double)(KK[_num] - 1);
			double b = 1.0 / (double)(KK[_num] - 1);

			uuu = 1.0 - ( Math.abs(_x - a) / b );

			if(uuu < 0.0) {
				uuu = 0.0;
			}
		} else {
			if(_num == _x) {
				uuu = 1.0;
			} else {
				uuu = 0.0;
			}
		}

		return uuu;
	}

	//適合度
	public static double memberMulPure(Pattern _line, int[] _rule) {
		double ans = 1.0;
		int Ndim = _rule.length;
		for(int i = 0; i < Ndim; i++) {
			try {
				//積和 適合度
				ans *= calcMembership(_rule[i], _line.getDimValue(i));
			} catch(Exception e) {

			}
		}
		return ans;
	}

	//ルールの学習
	public static void linearLearning(DataSetInfo _tra, Rule[] _rules) {
		double[] memberships = new double[_rules.length];
		double memberSum = 0;
		Pattern line;
		double y;
		double diff;
		double error;

		//全パターンに対して学習
		for(int move_i = 0; move_i < _tra.getPatterns().size(); move_i++) {
//			if(move_i == _tra.getPatterns().size() -1) {
//				System.out.println();
//			}
			memberSum = 0;
			line = _tra.getPattern(move_i);
			//入力パターンに対する全ルールの適合度計算
			for(int rule_i = 0; rule_i < _rules.length; rule_i++) {
				memberships[rule_i] = _rules[rule_i].memberMulPure(line);
				memberSum += memberships[rule_i];
			}
			//推論値計算
			y = 0;
			for(int rule_i = 0; rule_i < _rules.length; rule_i++) {
				y += _rules[rule_i].getConclution() * memberships[rule_i];
			}
			y /= memberSum;
			diff = line.getY() - y;
			//修正値計算
			for(int rule_i = 0; rule_i < _rules.length; rule_i++) {
				error = _rules[rule_i].getConclution() + eta * diff * memberships[rule_i] / memberSum;
				_rules[rule_i].setConclution(error);
			}
		}

	}


	//ルールの学習
	public static void linearLearning2(DataSetInfo[] _tra, Rule[] _rules) {
		double[] memberships = new double[_rules.length];
//		double[] error = new double[_rules.length];
		double error;
		Pattern line;
		double sum;	//全ルールメンバシップ値合計
		double diff;
		double y = 0;

		//全学習用データを学習
		for(int game_i = 0; game_i < _tra.length; game_i++) {
			//全てのMoveを順に学習
			for(int move_i = 0; move_i < _tra[game_i].getPatterns().size(); move_i++) {
				sum = 0;
				line = _tra[game_i].getPattern(move_i);

				//全ルールメンバシップ値計算
				for(int rule_i = 0; rule_i < _rules.length; rule_i++) {
					memberships[rule_i] = _rules[rule_i].memberMulPure(line);
					sum += memberships[rule_i];
				}
				//推論値計算
				for(int rule_i = 0; rule_i < _rules.length; rule_i++) {
					y += memberships[rule_i] * _rules[rule_i].getConclution();
				}


				//修正量計算
				for(int rule_i = 0; rule_i < _rules.length; rule_i++) {
					diff = line.getY() - (memberships[rule_i] * _rules[rule_i].getConclution());
//					diff = line.getY() - _rules[rule_i].output(line);	//上と同義のメソッド
					error = _rules[rule_i].getConclution() + (eta * diff) * (memberships[rule_i] / sum);
//					_rules[rule_i].learnConclution(error);
					_rules[rule_i].setConclution(error);
				}

			}
		}

	}

	// ******************************************
}
