package main;
import java.io.IOException;
import java.util.Arrays;

public class Main {
	//Field ******************************************
	// ************************************************

	//Constructor *************************************
	// ************************************************

	//Methods *****************************************
	// ************************************************

	public static void main(String[] args) throws IOException {
		//読み込みファイル名 獲得
		String traFileName = "DataSets/FixedData/train_data_fixed1.csv";
		String tstFileName = "DataSets/FixedData/test_data_fixed1.csv";

		//データセット読み込み
		DataSetInfo traDataInfo = new DataSetInfo(traFileName);
		DataSetInfo tstDataInfo = new DataSetInfo(tstFileName);

		//結論部リスト読み込み
//		String concFileName = "DataSets/ConclusionList/choice_rule1.csv";
		double[] concList = new double[(int)Math.pow(6, traDataInfo.getNdim())];
//		DataSetInfo.inputConcList(concFileName, concList);
		Arrays.fill(concList, 0.5);

		startExperiment(traDataInfo, tstDataInfo, concList);

	}

	static public void startExperiment(DataSetInfo _tra, DataSetInfo _tst, double[] _concList) throws IOException {
//		int Ndim = _tra.getNdim() - 1; 	//(MoveNoは固定のメンバシップ関数を使用する)
		int Ndim = _tra.getNdim();	//(MoveNo に対しても全種類のメンバシップ関数を割り当てることに変更)

		String resultFileName = "result/new_fixed_0.5/";
		Output out = new Output(resultFileName);
		ResultMaster resultMaster = new ResultMaster(resultFileName);
		RuleSet ruleSet;

		//don't care, 2分割, 3分割の三角型ファジィ集合を作成
		FuzzySet fuzzySet = new FuzzySet(3);

		//全探索ルールの作成
		int ruleNum = (int)Math.pow(6, Ndim);	//6種類のファジィ集合 の Ndim乗
		Rule[] rules = new Rule[ruleNum];
		int[][] setRule = new int[ruleNum][Ndim];
		int index = 0;
		for(int Ndim_i = 0; Ndim_i < Ndim; Ndim_i++) {
			while(index < ruleNum) {
				for(int rule_i = 0; rule_i < 6; rule_i++) {
					if(Ndim_i == 0) {
						setRule[index][Ndim_i] = rule_i;
						index++;
					} else {
						for(int j = 0; j < (int)Math.pow(6, Ndim_i); j++) {
							setRule[index][Ndim_i] = rule_i;
							index++;
						}
					}
				}
			}
			index = 0;
		}
		for(int i = 0; i < ruleNum; i++) {
			rules[i] = new Rule(setRule[i], Ndim, _concList[i]);
		}

		//結論部の学習
		int generation = 1;
		for(int gene_i = 0; gene_i < generation; gene_i++) {
			//10世代ごとに途中経過表示
			if(gene_i % 10 == 0) {
				System.out.print(".");
			}

			//学習
			FuzzySet.linearLearning(_tra, rules);

//			//100世代ごとに結果出力
//			if(gene_i % 100 == 0) {
//				ruleSet = new RuleSet(rules);
//				resultMaster.setMSE(ruleSet, _tra, _tst);
//				resultMaster.writeMSE(gene_i);
//				resultMaster.writeConclusion(gene_i, ruleSet);
//				System.out.println();
//			}
		}

		//最終世代の結論部リスト出力
		ruleSet = new RuleSet(rules);
		resultMaster.writeConclusion(generation, ruleSet);
		//最終世代のMSE出力
		resultMaster.setMSE(ruleSet, _tra, _tst);
		resultMaster.writeMSE(generation);
		//推論値リスト保持用
		double[] yTra = new double[_tra.getDataSize()];
		double[] yTst = new double[_tst.getDataSize()];
		FuzzySet.reasoning(_tra, rules, yTra);
		FuzzySet.reasoning(_tst, rules, yTst);
		resultMaster.writeY(yTra, yTst);

	}

}
