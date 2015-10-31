
public class Method {
	private String function;
	private String howToUse;
	
	public Method(){
		this.function = "";
		this.howToUse = "";
	}
	
	public Method(String function, String howToUse){
		this.function = function;
		this.howToUse = howToUse;
	}

	public String getFunction(){
		return function;
	}

	public String getHowToUse(){
		return howToUse;
	}
	
	public void setFunction(String function){
		this.function = function;
	}
	
	public void setHowToUse(String howToUse){
		this.howToUse = howToUse;
	}
}
