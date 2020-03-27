package life.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

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
            + "all foods consumed on any given date and saves the report in a .txt file in the same folder as"
            + " jar file.\n "
            + "Parameters: "
            + CliSyntax.PREFIX_DATE + "DATE "
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE + "2020-05-27";

    public static final String MESSAGE_REPORT_SUCCESS = "Successfully generated a report in the /reports folder "
            + "for the following date: %tF" + ".";

    public static final String MESSAGE_REPORT_FAILURE = "Did not manage to generate report.";

    private LocalDate queryDate;

    public ReportCommand(LocalDate queryDate) {
        this.queryDate = queryDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasLogWithSameDate(queryDate)) {
            throw new CommandException(MESSAGE_REPORT_FAILURE);
        }
        DailyFoodLog queryLog = model.getLogByDate(this.queryDate);
        DailyGoal dailyGoal = model.getDailyGoal();
        ReportGenerator reportGenerator = new ReportGenerator(queryLog, dailyGoal);
        boolean isGenerated = reportGenerator.generateReport();
        if (!isGenerated) {
            throw new CommandException(MESSAGE_REPORT_FAILURE);
        }
        return new CommandResult(String.format(MESSAGE_REPORT_SUCCESS, this.queryDate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReportCommand // instanceof handles nulls
                && queryDate.equals(((ReportCommand) other).queryDate));
    }
}
