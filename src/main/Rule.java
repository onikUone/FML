package main;

import rnd.MersenneTwisterFast;

public class Rule {

	//Field
	MersenneTwisterFast uniqueRnd;

	int Ndim;

	//rule
	int rule[];	//ルール前件部
	double conclution;	//ルール結論部クラス
	double cf;	//ルール重み
	int ruleLength;	//ルール長
	int fitness;	//ルール使用回数
	//***************

	//Constructor
	public Rule() {}

	public Rule(int[] _rule, int _Ndim) {
		this.rule = _rule;
		this.Ndim = _Ndim;
		this.conclution = 0.5;	//0.5で一律指定
		for(int i = 0; i < this.rule.length; i++) {
			if(this.rule[i] != 0) {
				this.ruleLength++;
			}
		}
	}

	//結論部リスト
	public Rule(int[] _rule, int _Ndim, double _concValue) {
		this.rule = _rule;
		this.Ndim = _Ndim;
		this.conclution = _concValue;
		for(int i = 0; i < this.rule.length; i++) {
			if(this.rule[i] != 0) {
				this.ruleLength++;
			}
		}
	}

	public Rule(MersenneTwisterFast _rnd, int _Ndim, int _DataSize, int _TstDataSize) {
		this.uniqueRnd = new MersenneTwisterFast( _rnd.nextInt() );
		this.Ndim = _Ndim;
	}

	//Method

	//適合度
	public double memberMulPure(double[] _x) {
		double ans = FuzzySet.moveNoMembership(_x[0]);
		if(ans == 0) {
			return ans;
		}
		for(int i = 0; i < this.rule.length; i++) {
			ans *= FuzzySet.calcMembership(this.rule[i], _x[i + 1]);
			if(ans == 0) {
				return ans;
			}
		}
		return ans;
	}

//	public double memberMulPureOld(Pattern _line) {
//		double ans = 1.0;
//		for(int i = 0; i < this.Ndim; i++) {
//			try {
//				ans *= FuzzySet.calcMembership(this.rule[i], _line.getDimValue(i));
//			} catch(Exception e) {
//			}
//		}
//		return ans;
//	}

	public int[] getRules() {
		return this.rule;
	}

	//ルール出力値
	public double output(Pattern _line) {
		double ans = memberMulPure(_line.getX());
		ans *= this.conclution;
		return ans;
	}

	public void learnConclution(double _error) {
		this.conclution += _error;
	}

	public void setConclution(double _newConc) {
		this.conclution = _newConc;
	}

	//ルール作成
	public void setMichigan() {
		rule = new int[this.Ndim];
	}

	public void setMichigan(int _dim) {
		this.Ndim = _dim;
		rule = new int[_dim];
	}

	public double getConclution() {
		return this.conclution;
	}

}
