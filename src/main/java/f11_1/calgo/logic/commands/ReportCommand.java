package f11_1.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import f11_1.calgo.logic.commands.exceptions.CommandException;
import f11_1.calgo.logic.parser.CliSyntax;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.day.Day;
import f11_1.calgo.storage.ReportGenerator;

/**
 * Generates a report of all the food consumed by User on any given day.
 * Day is in YYYY-MM-DD format.
 */
public class ReportCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a report containing statistics of "
            + "all foods consumed on any given day and saves the report in a .txt file in the same folder as"
            + " jar file.\n "
            + "Parameters: "
            + CliSyntax.PREFIX_DATE + "DATE"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE + "2020-05-27";

    public static final String MESSAGE_REPORT_SUCCESS = "Successfully generated a report. Check in the same folder "
            + "as jar file.";

    public static final String MESSAGE_REPORT_FAILURE = "Did not manage to generate report.";

    private LocalDate queryDate;

    public ReportCommand(LocalDate queryDate) {
        this.queryDate = queryDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasDay(new Day(queryDate))) {
            throw new CommandException(MESSAGE_REPORT_FAILURE);
        }
        Day queryDay = model.getDayByDate(this.queryDate);
        ReportGenerator reportGenerator = new ReportGenerator(queryDay);
        boolean isGenerated = reportGenerator.generateReport();
        // todo: use String.format and regex to make MESSAGE_SUCCESS able to show date as well
        if (!isGenerated) {
            throw new CommandException(MESSAGE_REPORT_FAILURE);
        }
        return new CommandResult(MESSAGE_REPORT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReportCommand // instanceof handles nulls
                && queryDate.equals(((ReportCommand) other).queryDate));
    }
}
