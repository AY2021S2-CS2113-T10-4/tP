package command;

import canteens.Canteen;
import nusfoodreviews.NusFoodReviews;
import ui.Ui;

import java.util.ArrayList;

public class HomeCommand extends Command {

    NusFoodReviews nusFoodReviews;

    public HomeCommand(NusFoodReviews nusFoodReviews) {
        this.nusFoodReviews = nusFoodReviews;
    }

    @Override
    public void execute(ArrayList<Canteen> canteens, Ui ui) {
        nusFoodReviews.resetAllIndexes();
    }
}
