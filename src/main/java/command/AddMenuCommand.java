package command;

import canteens.Canteen;
import exceptions.DukeExceptions;
import menus.Menu;
import nusfoodreviews.NusFoodReviews;
import storage.Storage;
import storage.WriteToFile;
import ui.Ui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddMenuCommand extends Command {

    private NusFoodReviews nusFoodReviews;

    public AddMenuCommand(NusFoodReviews nusFoodReviews) {

        this.nusFoodReviews = nusFoodReviews;
    }

    @Override
    public void execute(ArrayList<Canteen> canteens, Ui ui) throws DukeExceptions {
        try {
            if (canteens.size() == 0) {
                throw new DukeExceptions("There is no canteen. Please add a new canteen and store");
            }
            getMenu(canteens, ui);
        } catch (NumberFormatException | IOException | DukeExceptions e) {
            System.out.println(Ui.LINESPACING);
            System.out.println(e.getMessage());
            System.out.println(Ui.LINESPACING);
        }
    }

    public void getMenu(ArrayList<Canteen> canteens, Ui ui) throws NumberFormatException, IOException, DukeExceptions {
        String menuName;
        double menuPrice = 0;
        Integer currentStoreIndex = 0;

        nusFoodReviews.setCanteenIndex();
        int currentCanteenIndex = nusFoodReviews.getCanteenIndex();
        if (currentCanteenIndex == -1) {
            ui.menuNotAdded();
            return;
        }
        if (canteens.get(currentCanteenIndex).getStores().size() == 0) {
            throw new DukeExceptions("There is no stores in canteen. Please add a new store.");
        }

        ui.showDisplayStores(canteens.get(currentCanteenIndex));
        ui.chooseStore();
        String line = ui.readCommand();

        if (line.equals("cancel")) {
            ui.menuNotAdded();
            return;
        } else {
            try {
                currentStoreIndex = Integer.parseInt(line) - 1;
            } catch (NumberFormatException e) {
                System.out.println(Ui.LINESPACING);
                System.out.println("Wrong input, please enter a correct integer.");
                System.out.println(Ui.LINESPACING);
                return;
            }
        }

        //check selected store input range
        if (currentStoreIndex < 0 | currentStoreIndex >= canteens.get(currentCanteenIndex).getNumStores()) {
            throw new DukeExceptions("Store index not in range! Please try again.");
        }

        ui.enterMenuName();
        line = ui.readCommand();
        if (line.equals("cancel")) {
            ui.menuNotAdded();
            return;
        } else {
            menuName = line;
        }

        ui.enterMenuPrice();
        line = ui.readCommand();

        if (line.equals("cancel")) {
            ui.menuNotAdded();
            return;
        } else {
            try {
                menuPrice = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println(Ui.LINESPACING);
                System.out.println("Wrong input, please enter a correct integer.");
                System.out.println(Ui.LINESPACING);
                return;
            }
        }

        Canteen canteen = canteens.get(currentCanteenIndex);
        Menu menu = new Menu(menuName,menuPrice);
        canteen.getStore(currentStoreIndex).addMenu(menu);
        ui.menuAdded(menuName,line);
        WriteToFile.saveMenu(new FileWriter(Storage.DEFAULT_STORAGE_FILEPATH,true),
                canteen.getCanteenName(),canteen.getStore(currentStoreIndex).getStoreName(),menuName,line);
    }


}
