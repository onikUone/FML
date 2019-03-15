package main;

import rnd.MersenneTwisterFast;

public class Rule {

	//Field
	MersenneTwisterFast uniqueRnd;

	int Ndim;
//	int DataSize;
//	int TstDataSize;

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
		this.conclution = 0.5;
//		this.DataSize = _DataSize;
//		this.TstDataSize = _TstDataSize;
		for(int i = 0; i < this.rule.length; i++) {
			if(this.rule[i] != 0) {
				this.ruleLength++;
			}
		}
	}

	public Rule(MersenneTwisterFast _rnd, int _Ndim, int _DataSize, int _TstDataSize) {
		this.uniqueRnd = new MersenneTwisterFast( _rnd.nextInt() );
		this.Ndim = _Ndim;
//		this.DataSize = _DataSize;
//		this.TstDataSize = _TstDataSize;
	}

	//Method

	//適合度
	public double memberMulPure(Pattern _line) {
		double ans = 1.0;
		for(int i = 0; i < this.Ndim; i++) {
			try {
				ans *= FuzzySet.calcMembership(this.rule[i], _line.getDimValue(i));
			} catch(Exception e) {
			}
		}
		return ans;
	}

	//ルール出力値
	public double output(Pattern _line) {
		double ans = memberMulPure(_line);
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
