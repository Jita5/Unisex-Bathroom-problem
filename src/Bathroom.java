import java.util.Iterator;
import java.util.LinkedList;

/**
 * The Class Bathroom.
 */
public class Bathroom {

	/** The bathroom line. */
	// List for people in line to use bathroom
	private LinkedList<Person> bathroomLine = new LinkedList<Person>();

	/** The using bathroom. */
	// List for people in the bathroom
	private LinkedList<Person> usingBathroom = new LinkedList<Person>();

	/**
	 * The Enum BathroomState.
	 */
	// States for bathroom control
	public enum BathroomState {

		MALES, /** The males. */
		FEMALES, /** The females. */
		ZOMBIE /** The zombie. */
	}

	/** The bath state. */
	// Initializing bathState so that a male or female can enter if their first
	private BathroomState bathState = BathroomState.ZOMBIE;

	/** The m. */
	private String m = "M";

	/** The f. */
	private String f = "F";

	/**
	 * Enter.
	 *
	 * @param person
	 *            the person
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void enter(Person person) throws InterruptedException {

		String nextGender = person.getPersonType();

		// Add person to the bathroom line
		synchronized (this) {
			bathroomLine.add(person);
			String que = "";
			String using = "";
			// Iterate bathroom line and using bathroom for output
			Iterator<Person> lineIter = bathroomLine.iterator();
			while (lineIter.hasNext()) {
				que += lineIter.next().getPersonType();
			}
			if (que == "") {
				que = null;
			}
			Iterator<Person> usingIter = usingBathroom.iterator();
			while (usingIter.hasNext()) {
				using += usingIter.next().getPersonType();
			}
			if (using == "") {
				using = null;
			}
			Control.output += (String.format("%-5s %5d %5s %-2s %5s %6s %5s %6s \n", "Time", person.getArriveTime(),
					"Arrive", nextGender, "Bathroom =", using, "Queue =", que));
		}
		// Get next person in bathroom line
		Person nextPerson = bathroomLine.peek();

		// If next person in line is not this person, wait
		while (nextPerson != person) {
			synchronized (person) {
				person.wait();
			}
			// refresh next person
			nextPerson = bathroomLine.peek();
		}
		// Get gender of next person
		nextGender = nextPerson.getPersonType();

		if (nextGender.equals(m)) {
			// If its a male and bath state is females
			while ((bathState != BathroomState.MALES) && (bathState == BathroomState.FEMALES)) {
				synchronized (person) {
					person.wait();
				}
			}
		} else if (nextGender.equals(f)) {
			// If its female and bath state is male
			while ((bathState != BathroomState.FEMALES) && (bathState == BathroomState.MALES)) {
				synchronized (person) {
					person.wait();
				}
			}
		}

		// Add person to using the bathroom
		synchronized (this) {
			usingBathroom.add(person);
			bathroomLine.remove(person);
			// Set state to male or female
			if (nextGender.equals(m)) {
				bathState = BathroomState.MALES;
			} else if (nextGender.equals(f)) {
				bathState = BathroomState.FEMALES;
			}
			String que = "";
			String using = "";
			// Iterate bathroom line and using bathroom for output
			Iterator<Person> lineIter = bathroomLine.iterator();
			while (lineIter.hasNext()) {
				que += lineIter.next().getPersonType();
			}
			if (que == "") {
				que = null;
			}
			Iterator<Person> usingIter = usingBathroom.iterator();
			while (usingIter.hasNext()) {
				using += usingIter.next().getPersonType();
			}
			if (using == "") {
				using = null;
			}
			Control.output += (String.format("%-5s %5d %6s %-2s %5s %6s %5s %6s \n", "Time", person.getArriveTime(),
					"Enter", nextGender, "Bathroom =", using, "Queue =", que));
			// If bathroom line is not empty, get next person and notify them
			// they can check if they can enter
			if (bathroomLine.size() != 0) {
				Person checkNext = bathroomLine.peek();
				synchronized (checkNext) {
					checkNext.notifyAll();
				}
			}
		}
	}

	/**
	 * Leave.
	 *
	 * @param person
	 *            the person
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	// Removes person from the bathroom
	public void leave(Person person) throws InterruptedException {
		String nextGender = person.getPersonType();
		int serviceTime = person.getServiceTime();
		String que = "";
		String using = "";
		// Variables used to check who is in bathroom
		boolean male = false;
		boolean female = false;
		String check = "";

		synchronized (this) {
			usingBathroom.remove(person);
			// Iterate bathroom line and using bathroom for output
			Iterator<Person> lineIter = bathroomLine.iterator();
			while (lineIter.hasNext()) {
				que += lineIter.next().getPersonType();
			}
			if (que == "") {
				que = null;
			}
			Iterator<Person> usingIter = usingBathroom.iterator();
			while (usingIter.hasNext()) {
				check = usingIter.next().getPersonType();
				using += check;
				if (check.equals(m)) {
					male = true;
				} else if (check.equals(f)) {
					female = true;
				}
			}
			if (using == "") {
				using = null;
			}
			Control.output += (String.format("%-5s %5d %6s %-2s %5s %6s %5s %6s \n", "Time", serviceTime, "Leave",
					nextGender, "Bathroom =", using, "Queue =", que));
			// If there are males in the bathroom, set/keep bath state as males
			if (male == true) {
				bathState = BathroomState.MALES;
				// If there are females in the bathroom, set/keep bath state as
				// females
			} else if (female == true) {
				bathState = BathroomState.FEMALES;
				// Otherwise set state to zombie, so male or female can get in
			} else {
				bathState = BathroomState.ZOMBIE;
			}
		}
		// Get the next person in line and notify them, they can check to see if
		// they can enter
		if (bathroomLine.size() != 0) {
			Person nextPerson = bathroomLine.peek();
			synchronized (nextPerson) {
				nextPerson.notifyAll();
			}
		}
	}
}
