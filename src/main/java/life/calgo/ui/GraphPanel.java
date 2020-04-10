package life.calgo.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import life.calgo.logic.Logic;
import life.calgo.model.day.DailyFoodLog;

/**
 * The Graph Panel, containing both the ui part for displaying a line chart of
 * total calories against date for the past seven days,
 * and the logic to create that chart.
 */
public class GraphPanel extends UiPart<Region> {

    private static GraphPanel graphPanelInstance = null;
    private static final String FXML = "GraphPanel.fxml";

    private ArrayList<DailyFoodLog> pastWeekLogs;
    private Map<LocalDate, Double> caloriesAgainstDate = new TreeMap<>();
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart<String, Number> graph = new LineChart<>(xAxis, yAxis);
    private XYChart.Series<String, Number> series;
    private LocalDate date;

    @FXML
    private TextArea graphPanel;

    public GraphPanel() {
        super(FXML);
    }

    // static method creates a singleton graphPanel
    public static GraphPanel getGraphPanelInstance() {
        if (graphPanelInstance == null) {
            graphPanelInstance = new GraphPanel();
        }

        return graphPanelInstance;
    }


    //Wrapper
    private void makeGraph(Logic logic) {
        initialiseTreeMap(logic);
        initialiseGraph();
        updateSeries();
    }

    public LineChart<String, Number> getGraph(Logic logic) {
        makeGraph(logic);
        return graph;
    }

    private void setPastWeekLogs(Logic logic) {
        pastWeekLogs = logic.getPastWeekLogs();
    }

    /**
     * Sets up the treemap containing mapping of date to total calories consumed on that day.
     * Days where there are no records are counted as 0 calories consumed.
     * @param logic module that contains method for obtaining daily food logs.
     */
    private void initialiseTreeMap(Logic logic) {
        caloriesAgainstDate.clear();
        setPastWeekLogs(logic);
        date = logic.getDate();

        for (int counter = pastWeekLogs.size() - 1; counter >= 0; counter--) {
            int daysToSubtract = pastWeekLogs.size() - 1 - counter;
            LocalDate logDate = date.minusDays(daysToSubtract);

            DailyFoodLog log = pastWeekLogs.get(counter);
            Double totalCalories = log.getTotalCalories();
            caloriesAgainstDate.put(logDate, totalCalories);
        }
    }

    /**
     * Sets up line chart axes, and adds a series to provide data.
     */
    private void initialiseGraph() {
        graph.getData().removeAll(series);
        series = new XYChart.Series<>();

        xAxis.setLabel("Day");
        yAxis.setLabel("Calories");

        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(1);
        yAxis.setMinorTickVisible(true);

        graph.getData().add(series);
    }


    /**
     * Updates the series that provides data to the graph.
     */
    private void updateSeries() {
        series.getData().clear();

        caloriesAgainstDate.forEach((date, calories) -> {
            String dateString = date.toString();
            series.getData().add(new XYChart.Data<>(dateString, calories));
        });
    }

}
