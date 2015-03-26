## Dataset Introduction
1. **Hypnogram**
    * The human sleep dataset consists of a total of 244 fully anonymized human polysomnographic recordings. They were extracted from polysomnographic overnight sleep studies performed in the Sleep Clinic at Day Kimball Hospital in Putnam, Connecticut, USA. This population consists of 122 males and 122 females, all suffering from sleep problems. The subjects’ ages range from 20 to 85 and the mean value is 47.9. The polysomnographic recording is extracted from C3-A2 EEG time signals at a sampling rate of 200Hz and then staged by lab technicians at the Sleep Clinic. Staging of each 30-second epoch into one of the sleep stages (wake, stage 1, stage 2, stage 3, and REM) is done by analyzing EEG, EOG and EMG recordings during the epoch. In this section stages 1, 2, and 3 may be grouped into a non-REM stage (NREM) and stages 1, 2, and REM be combined into light sleep stage. We directly use human hypnogram recordings to capture dynamical features of sleep. The durations of continuous uninterrupted bouts in each stage are natural candidates for the representation of the hypnogram recordings.
![HYPNOGRAMS](/images/IntroductionToHypnograms.bmp)

2. **User Navigation Behavior**
    * The MSNBC dataset (user behavior dataset) on a website comes from Internet Information Server (IIS) logs for msnbc.com on September, 28, 1999 (Pacific Standard Time). It has been applied to visualize navigation patterns in the light of model-based clustering. Each sequence in the dataset corresponds to a webpag browsing history of a given user during the day. Each event in the sequence corresponds to the user’s request for a certain page. Requests are represented at the level of page category rather than at the level of URL. The categories consist of “frontpage”, “news”, “tech”, and so on. There are totally 17 categories in this dataset. Each category is associated with an integer starting with “1” as listed in the following table.
    
    
                Category | Code | Category | Code
                -------- | ---- | -------- | ----
                Frontpage|   1  |  Living  |  10
                News     |   2  |  Business|  11
                Tech     |   3  |  Sports  |  12
                Local    |   4  |  Summary |  13
                Opinion  |   5  |  BBS     |  14
                On-Air   |   6  |  Travel  |  15
                Misc     |   7  |  Msn-News|  16
                Weather  |   8  |  Msn-sports| 17
                Health   |   9

See Link: https://archive.ics.uci.edu/ml/datasets/MSNBC.com+Anonymous+Web+Data