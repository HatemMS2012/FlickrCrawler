package hm.flickr.tag.crawler;

public class MyTag {

	
	private int order ;
	private String text;
	private String concatText ;
	private boolean isMachineTag ;
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getConcatText() {
		return concatText;
	}
	public void setConcatText(String concatText) {
		this.concatText = concatText;
	}
	public boolean isMachineTag() {
		return isMachineTag;
	}
	public void setMachineTag(boolean isMachineTag) {
		this.isMachineTag = isMachineTag;
	}

	@Override
	public String toString() {
	
		return this.text;
	}
	
	
	
}
