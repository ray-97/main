package f11_1.calgo.logic.parser;

import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_DATE;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_NAME;
import static f11_1.calgo.logic.parser.CliSyntax.PREFIX_PORTION;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import f11_1.calgo.logic.commands.NomCommand;
import f11_1.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.Model;
import f11_1.calgo.model.day.Day;
import f11_1.calgo.model.food.Food;

public class NomCommandParser implements Parser<NomCommand> {

    public static final String COMMAND_WORD = "nom";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Model model;

    public NomCommandParser(Model model) {
        this.model = model;
    }

    public NomCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_PORTION);
        // need to include remaining prefixes in case user accidentally include unwanted prefixes.
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            // throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NomCommand.MESSAGE_USAGE));
        }

        // if what we parsed are not correct, could be because of multimap not separating prefixes
        // that were not tokenized.

        // parse for food. then make new day based on parsed stuff
        Day dayConsumed = new Day();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            dayConsumed = dayConsumed.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
            // figure out how exceptions are handled for AB3.
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
            // throw exception saying no food.
        }
        dayConsumed.consume(optionalFood.get(), portion);
        return new NomCommand(dayConsumed, optionalFood.get());

//        // we need to have something to parse for date
//        // throw exception if there is no name
//        // parseFood(Food, parsePortion(...))
//        editDayDescriptor.addToDailyFoodLog(ParserUtil.parseFood(argMultimap.getValue(PREFIX_NAME).get()));
//        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
//            editDayDescriptor.setLocalDate();
//        }

        // date item + constructed item


    }

}

/*
concerns:
1. argmultimap
2. exception flow
 */