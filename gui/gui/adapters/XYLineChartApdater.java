package adapters;

import cern.colt.list.DoubleArrayList;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeriesCollection;
import umontreal.iro.lecuyer.charts.XYLineChart;
import umontreal.iro.lecuyer.charts.XYListSeriesCollection;
import umontreal.iro.lecuyer.charts.YListChart;

import javax.swing.*;
import java.awt.*;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 04/Apr/2015
 * Time: 10:01
 * System Time: 10:01 AM
 */

/**
 * Adapter for XYLineChart class
 */
public class XYLineChartApdater extends XYLineChart{

    /**
     * Get chart (New method added in this adapter class)
     * @return
     */
    public JFreeChart getChart() {
        return super.chart;
    }

    protected void init (String title, String XLabel, String YLabel) {
        super.init(title, XLabel, YLabel);
    }

    protected void initAxis(){
        super.initAxis();
    }

    /**
     * (Modified method from XYLineChart class)
     * I comment out the myFrame.setVisible(true) to avoid its display on the screen
     *
     * Displays chart on the screen using Swing.
     *    This method creates an application containing a chart panel displaying
     *    the chart. The created frame is positioned on-screen, and displayed before
     *    it is returned. The <TT>width</TT> and the <TT>height</TT>
     *    of the chart are measured in pixels.
     *
     * @param width frame width in pixels.
     *
     *    @param height frame height in pixels.
     *
     *    @return frame containing the chart.;
     *
     */
    public JFrame view (int width, int height)  {

        JFrame myFrame;
        if(chart.getTitle() != null)
            myFrame = new JFrame("XYLineChart from SSJ: " + chart.getTitle().getText());
        else
            myFrame = new JFrame("XYLineChart from SSJ");
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(width, height));
        myFrame.setContentPane(chartPanel);
        myFrame.pack();
        myFrame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        myFrame.setLocationRelativeTo (null);
        // myFrame.setVisible(true); comment out to delete its transient display on screen
        return myFrame;
    }

    /**
     * Initializes a new <TT>XYLineChart</TT> instance with an empty data set.
     *
     */
    public XYLineChartApdater()  {
        super();
    }


    /**
     * Initializes a new <TT>XYLineChart</TT> instance with sets of points <TT>data</TT>.
     * <TT>title</TT> is a title, <TT>XLabel</TT> is a short description of the
     * <SPAN CLASS="MATH"><I>x</I></SPAN>-axis, and <TT>YLabel</TT> a short description of the <SPAN CLASS="MATH"><I>y</I></SPAN>-axis.
     * The input parameter <TT>data</TT>  represents a set of plotting data.
     *
     * <P>
     * For example, if one <SPAN CLASS="MATH"><I>n</I></SPAN>-row matrix <TT>data1</TT> is given as argument
     *  <TT>data</TT>, then the first row <TT>data1</TT><SPAN CLASS="MATH">[0]</SPAN> represents the
     *  <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinate vector, and every other row <TT>data1</TT>
     * <SPAN CLASS="MATH">[<I>i</I>], <I>i</I> = 1,&#8230;, <I>n</I> - 1</SPAN>, represents a <SPAN CLASS="MATH"><I>y</I></SPAN>-coordinate set for a curve.
     *   Therefore matrix <TT>data1</TT><SPAN CLASS="MATH">[<I>i</I>][<I>j</I>]</SPAN>,
     * <SPAN CLASS="MATH"><I>i</I> = 0,&#8230;, <I>n</I> - 1</SPAN>,  corresponds
     *    to <SPAN CLASS="MATH"><I>n</I> - 1</SPAN> curves, all with the same <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates.
     *
     * <P>
     * However, one may want to plot several curves with different <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates.
     *   In that case, one should give the curves as matrices with two rows.
     * For examples, if the argument <TT>data</TT> is made of three 2-row matrices
     * <TT>data1</TT>, <TT>data2</TT> and <TT>data3</TT>, then they represents
     *  three different curves, <TT>data*</TT><SPAN CLASS="MATH">[0]</SPAN> being the <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates,
     *  and  <TT>data*</TT><SPAN CLASS="MATH">[1]</SPAN> the <SPAN CLASS="MATH"><I>y</I></SPAN>-coordinates of the curves.
     *
     * @param title chart title.
     *
     *    @param XLabel Label on <SPAN CLASS="MATH"><I>x</I></SPAN>-axis.
     *
     *    @param YLabel Label on <SPAN CLASS="MATH"><I>y</I></SPAN>-axis.
     *
     *    @param data series of point sets.
     *
     *
     */
    public XYLineChartApdater (String title, String XLabel, String YLabel,
                        double[][]... data)  {
        super(title, XLabel, YLabel, data);
    }


    /**
     * Initializes a new <TT>XYLineChart</TT> instance with sets of points <TT>data</TT>.
     * <TT>title</TT> is a title, <TT>XLabel</TT> is a short description of the
     * <SPAN CLASS="MATH"><I>x</I></SPAN>-axis, and <TT>YLabel</TT> a short description of the <SPAN CLASS="MATH"><I>y</I></SPAN>-axis.
     *  If <TT>data</TT> is a <SPAN CLASS="MATH"><I>n</I></SPAN>-row matrix,
     *  then the first row <TT>data</TT><SPAN CLASS="MATH">[0]</SPAN> represents the
     *  <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinate vector, and every other row <TT>data</TT>
     * <SPAN CLASS="MATH">[<I>i</I>], <I>i</I> = 1,&#8230;, <I>n</I> - 1</SPAN>, represents a <SPAN CLASS="MATH"><I>y</I></SPAN>-coordinate set of points.
     *   Therefore matrix <TT>data</TT><SPAN CLASS="MATH">[<I>i</I>][&nbsp;]</SPAN>,
     * <SPAN CLASS="MATH"><I>i</I> = 0,&#8230;, <I>n</I> - 1</SPAN>,  corresponds
     *    to <SPAN CLASS="MATH"><I>n</I> - 1</SPAN> curves, all with the same <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates.
     *   However, only <SPAN  CLASS="textit">the first</SPAN> <TT>numPoints</TT> of <TT>data</TT> will
     *   be considered to plot each curve.
     *
     * @param title chart title.
     *
     *    @param XLabel Label on <SPAN CLASS="MATH"><I>x</I></SPAN>-axis.
     *
     *    @param YLabel Label on <SPAN CLASS="MATH"><I>y</I></SPAN>-axis.
     *
     *    @param data series of point sets.
     *
     *    @param numPoints Number of points to plot
     *
     *
     */
    public XYLineChartApdater (String title, String XLabel, String YLabel,
                        double[][] data, int numPoints)  {
        super(title, XLabel, YLabel, data, numPoints);
    }


    /**
     * Initializes a new <TT>XYLineChart</TT> instance using subsets of <TT>data</TT>.
     * <TT>data[x][.]</TT> will form the <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates and
     * <TT>data[y][.]</TT> will form the <SPAN CLASS="MATH"><I>y</I></SPAN>-coordinates of the chart.
     * <TT>title</TT> sets a title, <TT>XLabel</TT> is a short description of the
     * <SPAN CLASS="MATH"><I>x</I></SPAN>-axis, and <TT>YLabel</TT> is a short description of the <SPAN CLASS="MATH"><I>y</I></SPAN>-axis.
     * Warning: if the new <SPAN CLASS="MATH"><I>x</I></SPAN>-axis coordinates are not monotone increasing, then
     * they will automatically be sorted in increasing order so the points will
     * be reordered, but the original <TT>data</TT> is not changed.
     *
     * @param title chart title.
     *
     *    @param XLabel Label on <SPAN CLASS="MATH"><I>x</I></SPAN>-axis.
     *
     *    @param YLabel Label on <SPAN CLASS="MATH"><I>y</I></SPAN>-axis.
     *
     *    @param data series of point sets.
     *
     *    @param x Index of data forming the <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates
     *
     *    @param y Index of data forming the <SPAN CLASS="MATH"><I>y</I></SPAN>-coordinates
     *
     *
     */
    public XYLineChartApdater (String title, String XLabel, String YLabel,
                        double[][] data, int x, int y)  {
        super(title, XLabel, YLabel, data, x, y);

    }


    /**
     * Initializes a new <TT>XYLineChart</TT> instance with data <TT>data</TT>.
     *    The input parameter <TT>data</TT> represents a set of plotting data. A
     *    {@link cern.colt.list.DoubleArrayList DoubleArrayList} from the Colt library is
     *    used to store the data. The description is similar to the
     *    constructor  {@link YListChart} with <TT>double[]... data</TT>.
     *
     * @param title chart title.
     *
     *    @param XLabel Label on <SPAN CLASS="MATH"><I>x</I></SPAN>-axis.
     *
     *    @param YLabel Label on <SPAN CLASS="MATH"><I>y</I></SPAN>-axis.
     *
     *    @param data series of point sets.
     *
     *
     */
    public XYLineChartApdater (String title, String XLabel, String YLabel,
                        DoubleArrayList... data)  {
        super(title, XLabel, YLabel, data);
    }


    /**
     * Initializes a new <TT>XYLineChart</TT> instance with data <TT>data</TT>.
     *    The input parameter <TT>data</TT> represents a set of plotting data.
     *    {@link org.jfree.data.xy.XYSeriesCollection XYSeriesCollection} is a
     *    <TT>JFreeChart</TT> container class to store <SPAN CLASS="MATH"><I>XY</I></SPAN> plots.
     *
     * @param title chart title.
     *
     *    @param XLabel Label on <SPAN CLASS="MATH"><I>x</I></SPAN>-axis.
     *
     *    @param YLabel Label on <SPAN CLASS="MATH"><I>y</I></SPAN>-axis.
     *
     *    @param data series collection.
     *
     */
    public XYLineChartApdater (String title, String XLabel, String YLabel,
                        XYSeriesCollection data)  {
        super(title, XLabel, YLabel, data);

    }


    /**
     * Adds a data series into the series collection. Vector <TT>x</TT> represents
     *    the <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates and vector <TT>y</TT> represents the <SPAN CLASS="MATH"><I>y</I></SPAN>-coordinates of
     *    the series. <TT>name</TT> and <TT>plotStyle</TT> are the name and the plot
     *    style associated to the series.
     *
     * @param x <SPAN CLASS="MATH"><I>x</I><SUB>i</SUB></SPAN> coordinates.
     *
     *    @param y <SPAN CLASS="MATH"><I>y</I><SUB>i</SUB></SPAN> coordinates.
     *
     *    @param name Name of the series.
     *
     *    @param plotStyle Plot style of the series.
     *
     *    @return Integer that represent the new point set's position in the JFreeChart <TT>XYSeriesCollection</TT> object.
     *
     */
    public int add (double[] x, double[] y, String name, String plotStyle)  {
        return super.add(x, y, name, plotStyle);
    }


    /**
     * Adds a data series into the series collection. Vector <TT>x</TT> represents
     *    the <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates and vector <TT>y</TT> represents the <SPAN CLASS="MATH"><I>y</I></SPAN>-coordinates of
     *    the series.
     *
     * @param x <SPAN CLASS="MATH"><I>x</I><SUB>i</SUB></SPAN> coordinates.
     *
     *    @param y <SPAN CLASS="MATH"><I>y</I><SUB>i</SUB></SPAN> coordinates.
     *
     *    @return Integer that represent the new point set's position in the JFreeChart <TT>XYSeriesCollection</TT> object.
     *
     */
    public int add (double[] x, double[] y)  {
        return super.add(x, y);
    }


    /**
     * Adds a data series into the series collection. Vector <TT>x</TT> represents
     *    the <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates and vector <TT>y</TT> represents the <SPAN CLASS="MATH"><I>y</I></SPAN>-coordinates of
     *    the series. Only <SPAN  CLASS="textit">the first</SPAN> <TT>numPoints</TT> of <TT>x</TT> and
     *    <TT>y</TT> will be taken into account for the new series.
     *
     * @param x <SPAN CLASS="MATH"><I>x</I><SUB>i</SUB></SPAN> coordinates.
     *
     *    @param y <SPAN CLASS="MATH"><I>y</I><SUB>i</SUB></SPAN> coordinates.
     *
     *    @param numPoints Number of points to add
     *
     *    @return Integer that represent the new point set's position in the JFreeChart <TT>XYSeriesCollection</TT> object.
     *
     */
    public int add (double[] x, double[] y, int numPoints)  {
        return super.add(x, y, numPoints);
    }


    /**
     * Adds the new collection of data series <TT>data</TT> into the series collection.
     *    If <TT>data</TT> is a <SPAN CLASS="MATH"><I>n</I></SPAN>-row matrix,
     *  then the first row <TT>data</TT><SPAN CLASS="MATH">[0]</SPAN> represents the
     *  <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinate vector, and every other row <TT>data</TT>
     * <SPAN CLASS="MATH">[<I>i</I>], <I>i</I> = 1,&#8230;, <I>n</I> - 1</SPAN>, represents a <SPAN CLASS="MATH"><I>y</I></SPAN>-coordinate set of points.
     *   Therefore matrix <TT>data</TT><SPAN CLASS="MATH">[<I>i</I>][&nbsp;]</SPAN>,
     * <SPAN CLASS="MATH"><I>i</I> = 0,&#8230;, <I>n</I> - 1</SPAN>,  corresponds
     *    to <SPAN CLASS="MATH"><I>n</I> - 1</SPAN> curves, all with the same <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates.
     *
     * @param data series of point sets.
     *
     *
     */
    public int add (double[][] data)  {
        return super.add(data);
    }


    /**
     * Adds the new collection of data series <TT>data</TT> into the series collection.
     *    If <TT>data</TT> is a <SPAN CLASS="MATH"><I>n</I></SPAN>-row matrix,
     *  then the first row <TT>data</TT><SPAN CLASS="MATH">[0]</SPAN> represents the
     *  <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinate vector, and every other row <TT>data</TT>
     * <SPAN CLASS="MATH">[<I>i</I>], <I>i</I> = 1,&#8230;, <I>n</I> - 1</SPAN>, represents a <SPAN CLASS="MATH"><I>y</I></SPAN>-coordinate set of points.
     *   Therefore matrix <TT>data</TT><SPAN CLASS="MATH">[<I>i</I>][&nbsp;]</SPAN>,
     * <SPAN CLASS="MATH"><I>i</I> = 0,&#8230;, <I>n</I> - 1</SPAN>,  corresponds
     *    to <SPAN CLASS="MATH"><I>n</I> - 1</SPAN> curves, all with the same <SPAN CLASS="MATH"><I>x</I></SPAN>-coordinates.
     *   However, only <SPAN  CLASS="textit">the first</SPAN> <TT>numPoints</TT> of <TT>data</TT> will
     *   be taken into account for the new series.
     *
     * @param data series of point sets.
     *
     *    @param numPoints Number of points to plot
     *
     *
     */
    public int add (double[][] data, int numPoints)  {
        return super.add(data, numPoints);
    }


    /**
     * Returns the chart's dataset.
     *
     * @return the chart's dataset.
     *
     */
    public XYListSeriesCollection getSeriesCollection()  {
        return super.getSeriesCollection();
    }


    /**
     * Links a new dataset to the current chart.
     *
     * @param dataset new dataset.
     *
     *
     */
    public void setSeriesCollection (XYListSeriesCollection dataset)  {
        super.setSeriesCollection(dataset);
    }


    /**
     * Synchronizes <SPAN CLASS="MATH"><I>X</I></SPAN>-axis ticks to the <SPAN CLASS="MATH"><I>s</I></SPAN>-th series <SPAN CLASS="MATH"><I>x</I></SPAN>-values.
     *
     * @param s series used to define ticks.
     *
     *
     */
    public void setTicksSynchro (int s)  {
        super.setTicksSynchro(s);
    }

    /**
     * Displays bar chart on the screen using Swing.
     *    This method creates an application containing a bar chart panel displaying
     *    the chart.  The created frame is positioned on-screen, and displayed before
     *    it is returned. The <TT>width</TT> and the <TT>height</TT>
     *    of the chart are measured in pixels.
     *
     * @param width frame width in pixels.
     *
     *    @param height frame height in pixels.
     *
     *    @return frame containing the bar chart.;
     *
     */
    public JFrame viewBar (int width, int height)  {
        return super.viewBar(width, height);
    }


    public String toLatex (double width, double height)  {
        return super.toLatex(width, height);
    }
}
