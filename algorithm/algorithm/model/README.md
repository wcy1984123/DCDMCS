##Dynamic Models
1. Markov Chain Model
    * Markov chain provides a dynamical model of the transitions between states. It is a particular kind of Markov model, in which all states are observable (that is, there are no hidden states). A Markov chain consists of a collection of states together with a state transition matrix that provides the probability of transition between each pair of states. The Markov assumption establishes that state transition probability depends only on the current state, independent of previous states in sequential data. On the other hand, Markov chains force exponential state duration distributions, which is not consistent with observations in practice.
    
2. Semi-Markov Chain Model
    * Semi-Markov chains, as a variant of Markov chains, are proposed to be more suitable for describing sequences with infrequent dynamic events, since they do not assume exponential distributions of state durations. The use of semi-Markov dynamical models in CDMC better describe the dynamical characteristics of human sleep as compared with Markov models.
    
3. Hidden Markov Model
    * Hidden Markov models introduce unobservable states to further describe internal complications of time signal processing. In contrast to the use of geometric state duration in HMMs, semi-Markov chain models explicitly parameterize the state duration to better capture the dynamic features in time series dataset.

