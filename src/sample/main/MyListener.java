package sample.main;

import sample.models.Items;

import java.io.IOException;

public interface MyListener {
    void onClickListener(Items item) throws IOException;
}
