package contamulation.app;

import java.nio.file.Path;
import java.nio.file.Paths;

import contamulation.api.SimSerializable;
import contamulation.app.simulation.SimulationConfig;
import contamulation.tools.structs.Curve;
import contamulation.tools.structs.Timetable;

public class Disease implements SimSerializable<Disease>
{

	public static final String INCUBATION_SUBPATH = "incubation.curve";
	public static final String RECOVERY_SUBPATH = "recovery.curve";
	public static final String TRANSMISSION_SUBPATH = "transmission.curve";
	public static final String DIAGNOSIS_SUBPATH = "diagnosis.timetable";
	
	private double symptoms      = 0.95;
	private double complications = 0.05;
	
	private Curve<Integer> incubationTime; 
	private Curve<Integer> recoveryTime; 
	private Curve<Double> transmissionChance; 
	private Timetable<Double> diagnosisChance;

	public Curve<Integer> getIncubationTimeCurve()
	{
		return incubationTime;
	}

	public Curve<Integer> getRecoveryTimeCurve()
	{
		return recoveryTime;
	}

	public Curve<Double> getTransmissionChanceCurve()
	{
		return transmissionChance;
	}

	public Timetable<Double> getDiagnosisChanceTimetable()
	{
		return diagnosisChance;
	}

	public double getSymptomsChance()
	{
		return symptoms;
	}

	public double getComplications()
	{
		return complications;
	}

	@Override
	public void write(SimulationConfig cfg)
	{
		Path dir = cfg.getPath(SimulationConfig.DISEASE_DIR);
		String ext = cfg.get(SimulationConfig.DISEASE_EXT);
		Path inc = dir.resolve(INCUBATION_SUBPATH   + ext);
		Path rec = dir.resolve(RECOVERY_SUBPATH     + ext);
		Path tra = dir.resolve(TRANSMISSION_SUBPATH + ext);
		Path dia = dir.resolve(DIAGNOSIS_SUBPATH    + ext);
		
		incubationTime.toFile(Integer.class, inc);
		recoveryTime.toFile(Integer.class, rec);
		transmissionChance.toFile(Double.class, tra);
		diagnosisChance.toFile(Double.class, dia);
	}

	@Override
	public void read(SimulationConfig cfg)
	{
		Path dir = cfg.getPath(SimulationConfig.DISEASE_DIR);
		String ext = cfg.get(SimulationConfig.DISEASE_EXT);
		Path inc = dir.resolve(INCUBATION_SUBPATH   + ext);
		Path rec = dir.resolve(RECOVERY_SUBPATH     + ext);
		Path tra = dir.resolve(TRANSMISSION_SUBPATH + ext);
		Path dia = dir.resolve(DIAGNOSIS_SUBPATH    + ext);
		
		incubationTime = Curve.fromFile(Integer.class, inc);
		recoveryTime = Curve.fromFile(Integer.class, rec);
		transmissionChance = Curve.fromFile(Double.class, tra);
		diagnosisChance = Timetable.fromFile(Double.class, dia);
	}
}
