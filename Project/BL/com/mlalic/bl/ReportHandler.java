package com.mlalic.bl;

import java.util.List;

import javafx.util.Pair;

public class ReportHandler extends HandlerBase {
	public List<Pair<Integer, Integer>> getFollowUpPatients() throws Exception {
		return repository.getFollowUpPatients();
	}
	
	public List<Pair<Integer, Integer>> getNewPatients() throws Exception {
		return repository.getNewPatients();
	}
	
	public float getAveragePatientsHandled() {
		return repository.getAveragePatientsHandled();
	}
	
	public List<Pair<Integer, Float>> getChargedSummaryToday() {
		return repository.getChargedSummaryToday();
	}
}
