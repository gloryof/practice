package jp.glory.analyze.web.search;

public class HobbyRequest {

	private String name;
	private int ranking;
	private Type type;


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the ranking
	 */
	public int getRanking() {
		return ranking;
	}


	/**
	 * @param ranking the ranking to set
	 */
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}


	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}


	public enum Type {
		Sports,
		Outdoor,
		Amusement
	}
}
