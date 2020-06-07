package contamulation.app.behavior;

public enum InfectionState
{
	SUSCEPTIBLE, // can catch the virus.
	INFECTIOUS,  // can transfer the virus.
	SYMPTOMLESS, // infectious without symptoms.
	QUARANTINED, // can neither, but is not working. NYI.
	RECOVERED;   // can neither.
	
	public boolean infectious()
	{
		return this == INFECTIOUS || this == SYMPTOMLESS;
	}
	
	public boolean hasSymptoms()
	{
		return this == INFECTIOUS || this == QUARANTINED;
	}
	
	public boolean susceptible()
	{
		return this == SUSCEPTIBLE;
	}
}
