package model;

import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.matrix.DoubleVector;
import weka.estimators.AbstractHMMEstimator;
import weka.estimators.DiscreteEstimator;
import weka.estimators.Estimator;
import weka.estimators.HMMEstimator;

import java.util.Random;

/**
 * Project: DCDMC
 * Package: model
 * Date: 06/Apr/2015
 * Time: 10:58
 * System Time: 10:58 AM
 */

public class DiscreteHMMEstimatorAdapter extends AbstractHMMEstimator implements HMMEstimatorAdapter, java.io.Serializable {


    private static final long serialVersionUID = 4585903204046324781L;

    protected Estimator m_outputEstimators[];
    protected int m_NumOutputs;

    /**
     * Get output estimators
     * @return output estimators
     */
    public Estimator[] getOutputEstimators() {
        return this.m_outputEstimators;
    }

    /**
     * Get state estimators
     * @return state estimators
     */
    public Estimator[] getStateEstimators() {
        return super.m_stateEstimators;
    }

    /**
     * Get state 0 estimator
     * @return state 0 estimator
     */
    public Estimator getState0Estimators() {
        return super.m_state0Estimator;
    }

    protected void setupOutputs()
    {
        m_outputEstimators = new Estimator[getNumStates()];
        for (int s = 0; s < getNumStates(); s++)
        {
            m_outputEstimators[s] = new DiscreteEstimator(getNumOutputs(), m_Laplace);
        }
    }

    public int getNumOutputs() {
        return m_NumOutputs;
    }
    public void setNumOutputs(int NumOutputs) {
        this.m_NumOutputs = NumOutputs;
        setupOutputs();
    }

    @Override
    public void setNumStates(int NumStates) {
        super.setNumStates(NumStates);
        setupOutputs();
    }

    public DiscreteHMMEstimatorAdapter() {
        super();
        setNumOutputs(6);
    }

    public DiscreteHMMEstimatorAdapter(int numStates, int numOutputs, boolean laplace) {
        super(numStates, laplace);

        setNumOutputs(numOutputs);
		/*
		m_outputEstimators = new Estimator[numStates];
		for (int s = 0; s < numStates; s++)
		{
			m_outputEstimators[s] = new DiscreteEstimator(numOutputs, laplace);
		}
		*/
    }



    public DiscreteHMMEstimatorAdapter(DiscreteHMMEstimatorAdapter e) throws Exception {
        super(e);

        setNumOutputs(e.getNumOutputs());

        //m_outputEstimators = new Estimator[getNumStates()];
        for (int s = 0; s < getNumStates(); s++)
        {
            m_outputEstimators[s] = Estimator.makeCopy(e.m_outputEstimators[s]);
        }
    }



    @Override
    public void addValue(double prevState, double state, DoubleVector output,
                         double weight) {
        addValue(prevState, state, output.get(0), weight);
        //m_stateEstimators[(int)prevState].addValue(state, weight);
        //m_outputEstimators[(int)state].addValue(output.get(0), weight);
    }

    @Override
    public void addValue0(double state, DoubleVector output, double weight) {
        addValue0(state, output.get(0), weight);
        //m_state0Estimator.addValue(state, weight);
        //m_outputEstimators[(int)state].addValue(output.get(0), weight);
    }

    @Override
    public double getProbability(double prevState, double state, DoubleVector output) {
        return getProbability(prevState, state, output.get(0));
        //return m_stateEstimators[(int)prevState].getProbability(state)
        //	* m_outputEstimators[(int)state].getProbability(output.get(0));
    }

    @Override
    public double getProbability0(double state, DoubleVector output) {
        return getProbability0(state, output.get(0));
        //return m_state0Estimator.getProbability(state)
        //* m_outputEstimators[(int)state].getProbability(output.get(0));
    }



    @Override
    public void addValue(double prevState, double state, double output,
                         double weight) {
        m_stateEstimators[(int)prevState].addValue(state, weight);
        m_outputEstimators[(int)state].addValue(output, weight);
    }

    @Override
    public void addValue0(double state, double output, double weight) {
        m_state0Estimator.addValue(state, weight);
        m_outputEstimators[(int)state].addValue(output, weight);
    }

    @Override
    public double getProbability(double prevState, double state, double output) {
        return m_stateEstimators[(int)prevState].getProbability(state)
                * m_outputEstimators[(int)state].getProbability(output);
    }

    @Override
    public double getProbability0(double state, double output) {
        return m_state0Estimator.getProbability(state)
                * m_outputEstimators[(int)state].getProbability(output);
    }



    @Override
    public String getRevision() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public int Sample0(Instances sequence, Random generator) {
        int state;
        int output;

        do {
            state = generator.nextInt(getNumStates());
            output = generator.nextInt(getNumOutputs());
        } while (generator.nextDouble() > getProbability0((double)state, (double)output));

        sequence.add(new DenseInstance(1));
        Instance frame = sequence.lastInstance();

        frame.setValue(0, output);

        return state;
    }

    @Override
    public int Sample(Instances sequence, int prevState,  Random generator) {
        int state;
        int output;

        do {
            state = generator.nextInt(getNumStates());
            output = generator.nextInt(getNumOutputs());
        } while (generator.nextDouble() > getProbability((double)prevState, (double)state, (double)output));

        sequence.add(new DenseInstance(1));
        Instance frame = sequence.lastInstance();

        frame.setValue(0, output);

        return state;
    }


    public String  toString() {
        String s = "DiscreteHMMEstimator\n" + super.toString();

        for (int i = 0; i < m_outputEstimators.length; i++)
            s = s + "Output Estimator, state " + i + " " + m_outputEstimators[i].toString() + "\n";

        return s;
    }

    @Override
    public void calculateParameters() {
        // do nothing, as discrete estimators use the sufficient
        // statistics directly without calculating parameters

    }

    /**
     * Get the state transition matrix given a estimator
     * @return state transition matrix
     */
    public double[][] getStateTransitionMatrix() {
        double[][] stateTransitionMatrix = new double[super.m_NumStates][super.m_NumStates];

        Estimator[] estimators = super.m_stateEstimators;

        for (int i = 0; i < super.m_NumStates; i++) {
            for (int j = 0; j < super.m_NumStates; j++) {
                stateTransitionMatrix[i][j] = estimators[i].getProbability(j);
            }
        }

        return stateTransitionMatrix;
    }

    /**
     * Get the output transition matrix given a estimator
     * @return state transition matrix
     */
    public double[][] getOutputTransitionMatrix() {
        double[][] outputTransitionMatrix = new double[super.m_NumStates][super.m_NumStates];

        Estimator[] estimators = this.m_outputEstimators;

        for (int i = 0; i < super.m_NumStates; i++) {
            for (int j = 0; j < super.m_NumStates; j++) {
                outputTransitionMatrix[i][j] = estimators[i].getProbability(j);
            }
        }

        return outputTransitionMatrix;
    }

    /**
     * Get the initial state transition matrix given a estimator
     * @return initial state transition matrix
     */
    public double[] getInitialStateTransitionMatrix() {
        double[] initialStateTransitionMatrix = new double[super.m_NumStates];

        Estimator initialEstimator = super.m_state0Estimator;

        for (int i = 0; i < super.m_NumStates; i++) {
            initialStateTransitionMatrix[i] = initialEstimator.getProbability(i);
        }

        return initialStateTransitionMatrix;
    }

}