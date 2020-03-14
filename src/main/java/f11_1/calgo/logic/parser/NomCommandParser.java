package f11_1.calgo.logic.parser;

import static f11_1.calgo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_CALORIES;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_CARBOHYDRATE;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_DATE;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_FAT;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_PORTION;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_POSITION;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import f11_1.calgo.logic.commands.NomCommand;
import f11_1.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.Food;

public class NomCommandParser implements Parser<NomCommand> {

    public static final String MESSAGE_EMPTY_NAME = "Name should not be empty";

    private final Model model;

    public NomCommandParser(Model model) {
        this.model = model;
    }

    public NomCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_PORTION,
                        PREFIX_CALORIES, PREFIX_PROTEIN, PREFIX_CARBOHYDRATE, PREFIX_FAT,
                        PREFIX_POSITION, PREFIX_TAG);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NomCommand.MESSAGE_USAGE));
        }

        Day dayConsumed = new Day();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            dayConsumed = dayConsumed.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (model.getDayByDate(dayConsumed.getLocalDate()) != null) {
            dayConsumed = model.getDayByDate(dayConsumed.getLocalDate());
        }
        double portion = 1;
        if (argMultimap.getValue(PREFIX_PORTION).isPresent()) {
            portion = ParserUtil.parsePortion(argMultimap.getValue(PREFIX_PORTION).get());
        }
        Optional<Food> optionalFood = model.getFoodByName(
                ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        if (!optionalFood.isPresent()) {
            throw new ParseException(MESSAGE_EMPTY_NAME);
        }
        dayConsumed.consume(optionalFood.get(), portion);
        return new NomCommand(dayConsumed, optionalFood.get());
    }

}