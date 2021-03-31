package command;

import canteens.Canteen;
import nusfoodreviews.NusFoodReviews;
import ui.Ui;

import java.util.ArrayList;

public class ResetStoreCommand extends Command {
    NusFoodReviews nusFoodReviews;

    public ResetStoreCommand(NusFoodReviews nusFoodReviews) {
        this.nusFoodReviews = nusFoodReviews;
    }

    @Override
    public void execute(ArrayList<Canteen> canteens, Ui ui) {
        nusFoodReviews.resetStoreIndex();
    }
}
