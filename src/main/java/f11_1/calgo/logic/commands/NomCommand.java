package f11_1.calgo.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import f11_1.calgo.model.day.Day;
import seedu.address.model.food.Food;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class NomCommand extends Command {

    public static final String MESSAGE_SUCCESS = "%d portion of %s was consumed on %s";
    public static final String COMMAND_WORD = "nom";

    private final Day dayConsumed;
    private final Food foodConsumed;

    public NomCommand(Day dayConsumed, Food foodConsumed) {
        requireNonNull(dayConsumed);
        this.dayConsumed = dayConsumed;
        this.foodConsumed = foodConsumed;
    }
    // Flow from GUI: mainwindow -> executeCommand handling help and exit <- logic.execute(commandResult),
    // and also interacts with AB storage(which gets AB from model) <- ABparser results commandResult
    // <- respective parser with feedback in commandResult -> passes Day into model -> model gives Day to save in AB model

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasDay(dayConsumed)) {
            model.addDay(dayConsumed);
        }
        model.addConsumption(dayConsumed);
        return new CommandResult(String.format(MESSAGE_SUCCESS, foodConsumed.getPortion(),
                foodConsumed, dayConsumed.getLocalDate()));
        }

}

//    // do we want to change these 2 to be under EditDayDescriptor class if we have edEat command?
//
//    private static Person createEditedDay(Day DayToEdit, EditDayDescriptor editDayDescriptor) {
//        assert personToEdit != null;
//
//        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
//        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
//        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
//        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
//        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
//
//        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
//    }
//
//    public static class EditDayDescriptor {
//        private LocalDate localDate;
//        private DailyFoodLog dailyFoodLog;
//
//        public EditDayDescriptor() {}
//
//        public EditDayDescriptor(EditDayDescriptor toCopy) {
//            setLocalDate(toCopy.localDate);
//            setDailyFoodLog(toCopy.dailyFoodLog);
//        }
//
//        public void setLocalDate(LocalDate localDate) {
//            this.localDate = localDate;
//            // this.foodlist = foodlist?
//        }
//
//        // maybe useful if we decide to have edit.
//        public void setDailyFoodLog(DailyFoodLog dailyFoodLog) {
//            this.dailyFoodLog = dailyFoodLog;
//        }
//
//        public void addToDailyFoodLog(Food food) {
//            dailyFoodLog = dailyFoodLog.add(food);
//        }

