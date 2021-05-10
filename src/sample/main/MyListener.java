package sample.main;

import sample.models.Items;
import sample.models.OnCartItems;

import java.io.IOException;

public interface MyListener {
    void onClickListener(Items item) throws IOException;
}
