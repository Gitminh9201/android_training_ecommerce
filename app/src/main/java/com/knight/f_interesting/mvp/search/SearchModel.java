package com.knight.f_interesting.mvp.search;

import java.util.ArrayList;
import java.util.List;

public class SearchModel implements SearchContract.Model {
    @Override
    public void getData(OnFinishedListener onFinishedListener) {
        List<String> strings = new ArrayList<>();
        onFinishedListener.onFinished(strings);
    }
}
