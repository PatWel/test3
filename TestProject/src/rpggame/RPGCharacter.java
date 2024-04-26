package rpggame;

enum Race
{
	HUMAN,
	ELF,
	DWARF,
}

public class RPGCharacter {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
