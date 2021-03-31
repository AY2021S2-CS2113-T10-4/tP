package command;

import canteens.Canteen;
import reviews.Review;
import stores.Store;
import ui.Ui;


import java.util.ArrayList;

import static stores.Store.averageRating;


public class ReadReviewsCommand extends Command {
    public Store store;


    public ReadReviewsCommand(Store store) {
        this.store = store;
    }

    @Override
    public void execute(ArrayList<Canteen> canteens, Ui ui) {
        ArrayList<Review> reviews = store.getReviews();
        averageRating = store.getAverageRating();
        String storeName = store.getStoreName();
        ui.showReviews(storeName, reviews, averageRating);
    }
}
