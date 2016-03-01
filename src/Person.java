/**
 * The Class Person.
 */
public class Person extends Thread {

	/** The bathroom. */
	private Bathroom bathroom;

	/** The person type. */
	private String personType;

	/** The arrive time. */
	private int arriveTime;

	/** The service time. */
	private int serviceTime;

	/**
	 * Instantiates a new person.
	 *
	 * @param bathroom
	 *            the bathroom
	 * @param personType
	 *            the person type
	 * @param arriveTime
	 *            the arrive time
	 * @param serviceTime
	 *            the service time
	 */
	public Person(Bathroom bathroom, String personType, int arriveTime, int serviceTime) {
		this.bathroom = bathroom;
		this.setPersonType(personType);
		this.setArriveTime(arriveTime);
		this.setServiceTime(serviceTime);
	}

	/**
	 * Run the person thread
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {

		// Wait the arrival time before queuing to use the bathroom
		try {
			Thread.sleep(getArriveTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Queue up for the Bathroom.
		try {

			// Queue to use bathroom
			bathroom.enter(this);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		// Once in bathroom, wait on for service time
		try {

			// Waiting on service time in bathroom
			Thread.sleep(serviceTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Finished using the bathroom, time to leave
		try {
			bathroom.leave(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the person type.
	 *
	 * @return the person type
	 */
	public String getPersonType() {
		return personType;
	}

	/**
	 * Sets the person type.
	 *
	 * @param personType
	 *            the new person type
	 */
	public void setPersonType(String personType) {
		this.personType = personType;
	}

	/**
	 * Gets the arrive time.
	 *
	 * @return the arrive time
	 */
	public int getArriveTime() {
		return arriveTime;
	}

	/**
	 * Sets the arrive time.
	 *
	 * @param arriveTime
	 *            the new arrive time
	 */
	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * Gets the service time.
	 *
	 * @return the service time
	 */
	public int getServiceTime() {
		return serviceTime;
	}

	/**
	 * Sets the service time.
	 *
	 * @param serviceTime
	 *            the new service time
	 */
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}
}
