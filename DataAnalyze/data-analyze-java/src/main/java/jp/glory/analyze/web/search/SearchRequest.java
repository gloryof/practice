package jp.glory.analyze.web.search;

public class SearchRequest {

	private String name;
	private int age;
	private HobbyRequest hobby;
	
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
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the hobby
	 */
	public HobbyRequest getHobby() {
		return hobby;
	}
	/**
	 * @param hobby the hobby to set
	 */
	public void setHobby(HobbyRequest hobby) {
		this.hobby = hobby;
	}
}
