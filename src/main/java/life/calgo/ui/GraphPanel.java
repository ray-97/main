package life.calgo.ui;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;

import life.calgo.logic.Logic;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.DisplayFood;

public class GraphPanel extends UiPart<Region> {

    private static final String FXML = "GraphPanel.fxml";

    private ArrayList<DailyFoodLog> pastWeekLogs;
    private Map<LocalDate, Double> caloriesAgainstDate;
    private LineChart<LocalDate, Double> graph;
    private XYChart.Series<LocalDate, Double> series;
    private ObservableList<DisplayFood> foodList;

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();

    public GraphPanel(ObservableList<DisplayFood> foodList) throws ParseException {
        super(FXML);
        requireNonNull(foodList);
        this.foodList = foodList;
    }


    //Wrapper
    private void makeGraph(Logic logic) throws ParseException {
        initialiseTreeMap(logic);
        initialiseGraph();
        updateSeries();
    }

    public LineChart<LocalDate, Double> getGraph(Logic logic)  throws ParseException {
        makeGraph(logic);
        return graph;
    }

    private void setPastWeekLogs(Logic logic) {
        pastWeekLogs = logic.getPastWeekLogs();
    }

    private void initialiseTreeMap(Logic logic) {
        caloriesAgainstDate.clear();
        setPastWeekLogs(logic);

        for (DailyFoodLog log : pastWeekLogs) {
            LocalDate date = log.getLocalDate();
            Double totalCalories = log.getTotalCalories();
            caloriesAgainstDate.put(date, totalCalories);
        }
    }

    private void initialiseGraph() {
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
            series.getData().add(new XYChart.Data<LocalDate, Double>(date, calories));
        });
    }


    // private void updateMapAdd()

}
