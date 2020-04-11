package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;

import life.calgo.logic.commands.exceptions.CommandException;
import life.calgo.logic.parser.CliSyntax;
import life.calgo.model.Model;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.day.DailyGoal;
import life.calgo.storage.ReportGenerator;

/**
 * Generates a report of all the food consumed by User on any given date.
 * Date is in YYYY-MM-DD format.
 */
public class ReportCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a report containing statistics of "
            + "all foods consumed on a given date and saves the report in a text file in the data/reports folder.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE + "2020-05-27";

    public static final String MESSAGE_REPORT_SUCCESS = "Successfully generated a report in the data/reports folder "
            + "for the following date: %tF" + ".";

    public static final String MESSAGE_REPORT_FAILURE = "Did not manage to generate report.";

    public static final String NO_SUCH_DATE = "There was no food consumed on %tF.";

    public static final String INPUT_OUTPUT_EXCEPTION = "There was an error in creating and/or "
            + "writing to your report file. Kindly revise your system settings to enable the app to create a new file.";

    // the date the user enters
    private LocalDate queryDate;

    public ReportCommand(LocalDate queryDate) {
        this.queryDate = queryDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // if there is no food consumed on the given date, do not execute command
        if (!model.hasLogWithSameDate(queryDate) || model.getLogByDate(queryDate).getFoods().size() == 0) {
            throw new CommandException(MESSAGE_REPORT_FAILURE + "\n" + String.format(NO_SUCH_DATE, queryDate));
        }

        DailyGoal dailyGoal = model.getDailyGoal();
        DailyFoodLog foodLog = model.getLogByDate(queryDate);
        ArrayList<DailyFoodLog> pastWeekLogs = model.getPastWeekLogs(); // for suggestions feature

        assert foodLog.getFoods().size() > 0 : "ReportCommand is wrongly processing an empty food log.";

        ReportGenerator reportGenerator = new ReportGenerator(queryDate, dailyGoal, foodLog, pastWeekLogs);
        boolean isGenerated = reportGenerator.generateReport();

        // if report does not successfully generate, inform user of failure in command execution
        if (!isGenerated) {
            throw new CommandException(MESSAGE_REPORT_FAILURE + "\n" + INPUT_OUTPUT_EXCEPTION);
        }

        return new CommandResult(String.format(MESSAGE_REPORT_SUCCESS, queryDate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReportCommand // instanceof handles nulls
                && queryDate.equals(((ReportCommand) other).queryDate)); // attribute check
    }
}
