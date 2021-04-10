package command;

import canteens.Canteen;
import exceptions.DukeExceptions;
import nusfoodreviews.NusFoodReviews;
import storage.Storage;
import stores.Store;
import ui.Ui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddStoreCommand extends Command {

    private NusFoodReviews nusFoodReviews;

    public AddStoreCommand(NusFoodReviews nusFoodReviews) {
        this.nusFoodReviews = nusFoodReviews;
    }

    @Override
    public void execute(ArrayList<Canteen> canteens, Ui ui) throws IOException, DukeExceptions {
        nusFoodReviews.setCanteenIndex();
        int currentCanteenIndex = nusFoodReviews.getCanteenIndex();
        if (currentCanteenIndex == -1) {
            ui.showStoreNotAdded();
            return;
        }
        Canteen currentCanteen = canteens.get(currentCanteenIndex);
        boolean isNameValid;
        String storeName;

        //display what canteens are in the store
        ui.showDisplayStores(canteens.get(currentCanteenIndex));
        ui.showAddStore();

        //get store name from user
        do {
            isNameValid = true;
            storeName = ui.readCommand();
            if (storeName.equals("cancel")) {
                ui.showStoreNotAdded();
                return;
            } else {
                for (Store store : currentCanteen.getStores()) {
                    if (store.getStoreName().equals(storeName)) {
                        isNameValid = false;
                        ui.showInvalidStorePrompt(store.getStoreName());
                        break;
                    }
                }
            }
        } while (!isNameValid);

        //add store to canteen
        currentCanteen.addStore(storeName);
        ui.printStoreAdded(storeName, currentCanteen.getCanteenName());
        Storage.saveStore(new FileWriter(Storage.DEFAULT_STORAGE_FILEPATH,true),
                canteens.get(currentCanteenIndex).getCanteenName(),storeName);
    }

}
