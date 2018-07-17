package optimalLocation.clientProviderTxt.utils;

public enum ColumnsIndex {

	LATITUDE	(0),
	LONGITUDE	(1);
	
	private int index;
	
	private ColumnsIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
