package main;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		//読み込みファイル名 獲得
		String traFilesName[] = new String[45];
		String tstFilesName[] = new String[15];
		for(int i = 0; i < 45; i++) {
			traFilesName[i] = "DataSets/TrainingData/GameDataG" + (i + 1) + ".csv";
			if(i < 15) {
				tstFilesName[i] = "DataSets/TestingData/GameDataG" + (i + 46) + ".csv";
			}
		}
		//データセット読み込み
		DataSetInfo[] traDataInfos = new DataSetInfo[45];
		DataSetInfo[] tstDataInfos = new DataSetInfo[15];
		for(int i = 0; i < traDataInfos.length; i++) {
			traDataInfos[i] = new DataSetInfo(traFilesName[i]);
		}
		for(int i = 0; i < tstDataInfos.length; i++) {
			tstDataInfos[i] = new DataSetInfo(tstFilesName[i]);
		}

		startExperiment(traDataInfos, tstDataInfos);

	}

	static public void startExperiment(DataSetInfo[] _tra, DataSetInfo[] _tst) {
		int Ndim = _tra[0].getNdim();

		//don't care, 2分割, 3分割の三角型ファジィ集合を作成
		FuzzySet fuzzySet = new FuzzySet(3);

		//全探索ルールの作成
		int ruleNum = (int)Math.pow(6, Ndim);
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
			rules[i] = new Rule(setRule[i], Ndim);
		}

		//結論部の学習
		int generation = 10;
		for(int gene_i = 0; gene_i < generation; gene_i++) {
//			if(gene_i % 10 == 0) {
				System.out.println(".");
//			}
			for(int game_i = 0; game_i < _tra.length; game_i++) {
				FuzzySet.linearLearning(_tra[game_i], rules);
			}
		}
//		for(int gene_i = 0; gene_i < generation; gene_i++) {
////			if(gene_i % 10 == 0) {
//				System.out.print(". ");
////			}
//			FuzzySet.linearLearning(_tra[0], rules);
//		}
		System.out.println();
	}

}
