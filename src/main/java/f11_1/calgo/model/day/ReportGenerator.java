package f11_1.calgo.model.day;

import f11_1.calgo.model.food.Food;
import f11_1.calgo.model.food.Calorie;
import f11_1.calgo.model.food.Protein;
import f11_1.calgo.model.food.Carbohydrate;
import f11_1.calgo.model.food.Fat;

import java.time.LocalDate;

public class ReportGenerator {
    private DailyFoodLog relevantData;

    // for generation of report for a given date
    public ReportGenerator(UniqueDayMap days, LocalDate date) {
        Day inputDay = days.getDayByDate(date);
        this.relevantData = inputDay.getDailyFoodLog();
    }

    public String generateInsights() {
        StringBuilder report = new StringBuilder();
        int totalCalories = 0;
        int totalProteins = 0;
        int totalCarbs = 0;
        int totalFats = 0;

        report.append(String.format("%-20s %-20s %-20s%n", "Food", "Quantity", "Calories"));
        report.append(String.format("%-20s %-20s %-20s%n", "--------------", "--------------", "--------------"));
        for (Food food : relevantData.getFoods()) {
            double quantity = relevantData.getQuantity(food);
            double currCalories = quantity * (double) Integer.parseInt(food.getCalorie().value);
            totalCalories += Integer.parseInt(food.getCalorie().value);
            totalProteins += Integer.parseInt(food.getProtein().value);
            totalCarbs += Integer.parseInt(food.getCarbohydrate().value);
            totalFats += Integer.parseInt(food.getFat().value);
            report.append(String.format("%-20s %-20s %-20s%n", food.toString(), quantity, currCalories));
        }
        report.append(String.format("%-20s %-20s %-20s%n", "--------------", "--------------", "--------------"));
        report.append(String.format("%s %-20s %-20s %-20s%n", "Total Calories in kcal", "Total Protein in grams"
                , "Total Carbohydrates in grams", "Total Fats in grams"));
        report.append(String.format("%d %-20d %-20d %-20d%n", totalCalories, totalProteins, totalCarbs, totalFats));
        return report.toString();
    }
}
