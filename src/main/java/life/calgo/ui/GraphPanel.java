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
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.day.DailyFoodLog;

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
        System.out.println("a");
    }

    // static method to create instance of Singleton class
    public static GraphPanel getGraphPanelInstance() {
        // To ensure only one instance is created
        if (graphPanelInstance == null) {
            graphPanelInstance = new GraphPanel();
        }

        return graphPanelInstance;
    }


    //Wrapper
    private void makeGraph(Logic logic) throws ParseException {
        initialiseTreeMap(logic);
        initialiseGraph();
        updateSeries();
    }

    public LineChart<String, Number> getGraph(Logic logic) throws ParseException {
        makeGraph(logic);
        return graph;
    }

    private void setPastWeekLogs(Logic logic) {
        pastWeekLogs = logic.getPastWeekLogs();
    }

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

    private void initialiseGraph() {
        //graph.getData().removeAll(series);
        series = new XYChart.Series<>();

        xAxis.setLabel("Day");
        yAxis.setLabel("Calories");

        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(1);
        yAxis.setMinorTickVisible(true);

        graph.getData().add(series);
    }

    private void updateSeries() {
        series.getData().clear();

        caloriesAgainstDate.forEach((date, calories) -> {
            String dateString = date.toString();
            series.getData().add(new XYChart.Data<String, Number>(dateString, calories));
        });
    }


    // private void updateMapAdd()

}
