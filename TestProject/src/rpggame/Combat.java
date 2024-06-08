package rpggame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import entity.Entity;

public class Combat {
	private ArrayList<Entity> monsters;
	private ArrayList<Entity> party;
	private static int state = 0;
	private ArrayList<Entity> combatActors;
	
	public Combat(ArrayList<Entity> monsters, ArrayList<Entity> party)
	{
		// Calculate initiative
		monsters = new ArrayList<Entity>(monsters);
		party = new ArrayList<Entity>(party);
		System.out.println("monsters: " + monsters.size() + " party: " + party.size());
		combatActors = new ArrayList<Entity>(monsters.size() + party.size());
		combatActors.addAll(monsters);
		combatActors.addAll(party);
		
		combatActors.forEach((n) -> n.initiative = n.calculateInitiative(20,1) );
		combatActors.forEach((n) -> System.out.println("init: " + n.initiative));
		
		// Sort entities by initiative
		Collections.sort(combatActors, new Comparator<Entity>() {
            @Override
            public int compare(Entity s1, Entity s2) {
                return Integer.compare(s2.initiative, s1.initiative);}
			}
		);

		combatActors.forEach((n) -> System.out.println("init sorted: " + n.initiative));

	}
}
