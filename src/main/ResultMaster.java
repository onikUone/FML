package main;

import java.io.IOException;
import java.util.ArrayList;

public class ResultMaster {
	//Field ******************************************
	String nameDir;
	ArrayList<Double> mseTra = new ArrayList<Double>();
	ArrayList<Double> mseTst = new ArrayList<Double>();
	// ************************************************

	//Constructor *************************************
	public ResultMaster(String _nameDir) {
		this.nameDir = _nameDir;
	}
	// ************************************************

	//Methods *****************************************
	public String getDirName() {
		return this.nameDir;
	}

	public void setMSE(RuleSet _ruleSet, DataSetInfo _tra, DataSetInfo _tst) {
		int mTra = _tra.getDataSize();
		int mTst = _tst.getDataSize();
		double x, y, diff;
		double mseTra = 0.0;
		double mseTst = 0.0;

		//学習用データMSE
		for(int i = 0; i < mTra; i++) {
			x = _ruleSet.calcY(_tra.getPattern(i));
			y = _tra.getPattern(i).getY();
			diff = x - y;
			mseTra += diff * diff;
		}
		mseTra /= mTra;

		//評価用データMSE
		for(int i = 0; i < mTst; i++) {
			x = _ruleSet.calcY(_tst.getPattern(i));
			y = _tst.getPattern(i).getY();
			diff = x - y;
			mseTst += diff * diff;
		}
		mseTst /= mTst;

		//結果保持
		this.mseTra.add(mseTra);
		this.mseTst.add(mseTst);
	}

	public void writeMSE(int _nowGene) throws IOException {
		String fileNameTra = this.nameDir + "gene" + _nowGene + "_mseTra" + ".csv";
		String fileNameTst = this.nameDir + "gene" + _nowGene + "_mseTst" + ".csv";
		Output.writeList(this.mseTra, fileNameTra);
		Output.writeList(this.mseTst, fileNameTst);

	}

	public void writeConclusion(int _nowGene, RuleSet _ruleSet) throws IOException {
		String fileNameConc = this.nameDir + "conclusion/gene_" + _nowGene + ".csv";
		Output.writeConc(_ruleSet, fileNameConc);
	}

	// ************************************************
}
