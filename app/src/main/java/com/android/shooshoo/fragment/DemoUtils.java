package com.android.shooshoo.fragment;
import com.android.shooshoo.models.DemoItem;

import java.util.ArrayList;
import java.util.List;

final class DemoUtils {
  int currentOffset;

  DemoUtils() {
  }

  public List<DemoItem> moarItems(int qty) {
    List<DemoItem> items = new ArrayList<>();

    for (int i = 0; i < qty; i++) {
      int colSpan = 0;

      switch (i%3){
        case 0: colSpan=1;break;
        case 1: colSpan=2;break;
        case 2: colSpan=1;break;
      }
      int rowSpan=colSpan;
      // Swap the next 2 lines to have items with variable
      // column/row span.
      // int rowSpan = Math.random() < 0.2f ? 2 : 1;

      DemoItem item = new DemoItem(colSpan, rowSpan, currentOffset + i);
      items.add(item);
    }

    currentOffset += qty;

    return items;
  }
}
