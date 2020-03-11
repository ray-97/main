package f11_1.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import f11_1.calgo.logic.parser.CliSyntax;
import f11_1.calgo.model.Model;
import f11_1.calgo.commons.core.Messages;
import f11_1.calgo.commons.core.index.Index;
import f11_1.calgo.commons.util.CollectionUtil;
import f11_1.calgo.logic.commands.exceptions.CommandException;
import f11_1.calgo.model.food.Calorie;
import f11_1.calgo.model.food.Carbohydrate;
import f11_1.calgo.model.food.Fat;
import f11_1.calgo.model.food.Protein;
import f11_1.calgo.model.food.Food;
import f11_1.calgo.model.food.Name;
import f11_1.calgo.model.tag.Tag;

/**
 * Edits the details of an existing food in the food record.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the food identified "
            + "by the index number used in the displayed food record. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_NAME + "NAME] "
            + "[" + CliSyntax.PREFIX_CALORIES + "CALORIES] "
            + "[" + CliSyntax.PREFIX_PROTEIN + "PROTEIN] "
            + "[" + CliSyntax.PREFIX_CARBOHYDRATE + "CARBOHYDRATE] "
            + "[" + CliSyntax.PREFIX_FAT + "FAT] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_CALORIES + "91234567 "
            + CliSyntax.PREFIX_FAT + "100";

    public static final String MESSAGE_EDIT_FOOD_SUCCESS = "Edited food: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food already exists in the food record.";

    private final Index index;
    private final EditFoodDescriptor editFoodDescriptor;

    /**
     * @param index of the food in the filtered food record to edit
     * @param editFoodDescriptor details to edit the food with
     */
    public EditCommand(Index index, EditFoodDescriptor editFoodDescriptor) {
        requireNonNull(index);
        requireNonNull(editFoodDescriptor);

        this.index = index;
        this.editFoodDescriptor = new EditFoodDescriptor(editFoodDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Food> lastShownList = model.getFilteredFoodRecord();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
        }

        Food foodToEdit = lastShownList.get(index.getZeroBased());
        Food editedFood = createEditedPerson(foodToEdit, editFoodDescriptor);

        if (!foodToEdit.isSameFood(editedFood) && model.hasFood(editedFood)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }

        model.setFood(foodToEdit, editedFood);
        model.updateFilteredFoodRecord(Model.PREDICATE_SHOW_ALL_FOODS);
        return new CommandResult(String.format(MESSAGE_EDIT_FOOD_SUCCESS, editedFood));
    }

    /**
     * Creates and returns a {@code Food} with the details of {@code foodToEdit}
     * edited with {@code editFoodDescriptor}.
     */
    private static Food createEditedPerson(Food foodToEdit, EditFoodDescriptor editFoodDescriptor) {
        assert foodToEdit != null;

        Name updatedName = editFoodDescriptor.getName().orElse(foodToEdit.getName());
        Calorie updatedCalorie = editFoodDescriptor.getCalorie().orElse(foodToEdit.getCalorie());
        Protein updatedProtein = editFoodDescriptor.getProtein().orElse(foodToEdit.getProtein());
        Carbohydrate updatedCarbohydrate = editFoodDescriptor.getCarbohydrate().orElse(foodToEdit.getCarbohydrate());
        Fat updatedFat = editFoodDescriptor.getFat().orElse(foodToEdit.getFat());
        Set<Tag> updatedTags = editFoodDescriptor.getTags().orElse(foodToEdit.getTags());

        return new Food(updatedName, updatedCalorie, updatedProtein, updatedCarbohydrate, updatedFat, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editFoodDescriptor.equals(e.editFoodDescriptor);
    }

    /**
     * Stores the details to edit the food with. Each non-empty field value will replace the
     * corresponding field value of the food.
     */
    public static class EditFoodDescriptor {
        private Name name;
        private Calorie calorie;
        private Protein protein;
        private Carbohydrate carbohydrate;
        private Fat fat;
        private Set<Tag> tags;

        public EditFoodDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditFoodDescriptor(EditFoodDescriptor toCopy) {
            setName(toCopy.name);
            setCalorie(toCopy.calorie);
            setProtein(toCopy.protein);
            setCarbohydrate(toCopy.carbohydrate);
            setFat(toCopy.fat);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, calorie, fat, protein, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCalorie(Calorie calorie) {
            this.calorie = calorie;
        }

        public Optional<Calorie> getCalorie() {
            return Optional.ofNullable(calorie);
        }

        public void setFat(Fat fat) {
            this.fat = fat;
        }

        public Optional<Fat> getFat() {
            return Optional.ofNullable(fat);
        }

        public void setProtein(Protein protein) {
            this.protein = protein;
        }

        public Optional<Protein> getProtein() {
            return Optional.ofNullable(protein);
        }

        public void setCarbohydrate(Carbohydrate carbohydrate) {
            this.carbohydrate = carbohydrate;
        }

        public Optional<Carbohydrate> getCarbohydrate() {
            return Optional.ofNullable(carbohydrate);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFoodDescriptor)) {
                return false;
            }

            // state check
            EditFoodDescriptor e = (EditFoodDescriptor) other;

            return getName().equals(e.getName())
                    && getCalorie().equals(e.getCalorie())
                    && getProtein().equals(e.getProtein())
                    && getCarbohydrate().equals(e.getCarbohydrate())
                    && getFat().equals(e.getFat())
                    && getTags().equals(e.getTags());
        }
    }
}
