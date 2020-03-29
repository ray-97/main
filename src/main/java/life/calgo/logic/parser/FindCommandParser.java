package life.calgo.logic.parser;

import static life.calgo.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.ArrayList;
import java.util.function.Predicate;

import life.calgo.commons.core.Messages;
import life.calgo.logic.commands.FindCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;
import life.calgo.model.food.predicates.CalorieContainsKeywordsPredicate;
import life.calgo.model.food.predicates.CarbohydrateContainsKeywordsPredicate;
import life.calgo.model.food.predicates.FatContainsKeywordsPredicate;
import life.calgo.model.food.predicates.NameContainsKeywordsPredicate;
import life.calgo.model.food.predicates.ProteinContainsKeywordsPredicate;
import life.calgo.model.food.predicates.TagContainsKeywordsPredicate;
import life.calgo.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_CALORIES,
                        CliSyntax.PREFIX_PROTEIN, CliSyntax.PREFIX_CARBOHYDRATE,
                        CliSyntax.PREFIX_FAT, CliSyntax.PREFIX_TAG);

        // argMultimap should contain only 1 prefix at this point
        Predicate pred = generateFindCommandPredicate(argMultimap);

        return new FindCommand(pred);

    }

    /**
     * Returns the corresponding type of Predicate based on the single prefix entered by the user.
     *
     * @param am the Argument Multimap we search through to produce the Predicate.
     * @return the corresponding type of Predicate based on the single prefix entered by the user.
     * @throws ParseException is thrown when there is more than 1 Prefix used.
     */
    private Predicate<Food> generateFindCommandPredicate(ArgumentMultimap am) throws ParseException {
        if (!am.containsSingleUserInputField()) {
            throw new ParseException(String.format(Messages.MESSAGE_EXCESS_FIND_FILTERS,
                    FindCommand.MESSAGE_USAGE));
        }

        // below are the specific checks for the single Prefix of the Argument Multimap am
        // Left in this manner for future development of Prefix-specific features for find,
        // such as showing for different values rather than exact values.
        if (arePrefixesPresent(am, CliSyntax.PREFIX_NAME)) {
            Name name = ParserUtil.parseName(am.getValue(CliSyntax.PREFIX_NAME).get());
            return new NameContainsKeywordsPredicate(name);
        }

        if (arePrefixesPresent(am, CliSyntax.PREFIX_CALORIES)) {
            Calorie calorie = ParserUtil.parseCalorie(am.getValue(CliSyntax.PREFIX_CALORIES).get());
            return new CalorieContainsKeywordsPredicate(calorie);
        }

        if (arePrefixesPresent(am, CliSyntax.PREFIX_PROTEIN)) {
            Protein protein = ParserUtil.parseProtein(am.getValue(CliSyntax.PREFIX_PROTEIN).get());
            return new ProteinContainsKeywordsPredicate(protein);
        }

        if (arePrefixesPresent(am, CliSyntax.PREFIX_CARBOHYDRATE)) {
            Carbohydrate carbohydrate = ParserUtil.parseCarbohydrate(am.getValue(CliSyntax.PREFIX_CARBOHYDRATE).get());
            return new CarbohydrateContainsKeywordsPredicate(carbohydrate);
        }

        if (arePrefixesPresent(am, CliSyntax.PREFIX_FAT)) {
            Fat fat = ParserUtil.parseFat(am.getValue(CliSyntax.PREFIX_FAT).get());
            return new FatContainsKeywordsPredicate(fat);
        }

        if (arePrefixesPresent(am, CliSyntax.PREFIX_TAG)) {
            ArrayList<Tag> tagList = new ArrayList<>(ParserUtil.parseTags(am.getAllValues(CliSyntax.PREFIX_TAG)));
            return new TagContainsKeywordsPredicate(tagList);
        }

        return null; // should never arrive here; gives an error in Console (Indicating "does not exist")

    }

}
