package main;

public class RuleSet {
	//Field ******************************************
	Rule[] rules;
	int ruleNum;

	// ************************************************

	//Constructor *************************************
	public RuleSet(Rule[] _rules) {
		this.rules = _rules;
		this.ruleNum = _rules.length;
	}
	// ************************************************

	//Methods *****************************************

	public double calcY(Pattern _line) {
		double ans = 0;
		double[] memberships = new double[this.ruleNum];
		double memberSum = 0;

		//入力されたパターンに対して全ルールの適合度計算
		for(int rule_i = 0; rule_i < this.ruleNum; rule_i++) {
			memberships[rule_i] = this.rules[rule_i].memberMulPure(_line.getX());
			memberSum += memberships[rule_i];
		}
		//推論値計算
		for(int rule_i = 0; rule_i < this.ruleNum; rule_i++) {
			ans += this.rules[rule_i].getConclution() * memberships[rule_i];
		}
		ans /= memberSum;

		return ans;
	}


	// ************************************************
}
