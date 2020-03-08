package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.day.DailyFoodLog;
import seedu.address.model.day.Day;
import seedu.address.model.food.Food;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class NomCommand extends Command {

    public static final String MESSAGE_SUCCESS = "New person added: %1$s on %";

    private final Day dayConsumed;
    // private final EditDayDescriptor editDayDescriptor;

    public NomCommand(Day dayConsumed) {
        requireNonNull(dayConsumed);
        this.dayConsumed = dayConsumed;
        // this.editDayDescriptor = editDayDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasDay(dayConsumed)) {
            model.addDay(dayConsumed);
        } // execute only passes in the corrected item to be edited in model. so parser needs to create dayAfterConsuming
        // parser uses descriptor, which is fed to command to createEditedDay.
        // createEditedDay, then feed it to model.
        // here we update the model with updated day, with updated log
        model.addConsumptionToDay(dayConsumed);
        // return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
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

}
