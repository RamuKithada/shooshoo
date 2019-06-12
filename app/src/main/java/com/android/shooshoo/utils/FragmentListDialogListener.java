package com.android.shooshoo.utils;

import android.widget.EditText;

/***
 * {@link FragmentListDialogListener} is listener to get selected position of the {@link CustomListFragmentDialog}
 */
public interface FragmentListDialogListener {
    /**this called when the user is selected on option of the list show in dialog
     * @param view  is the id of the view
     * @param position position of the list item selected
     */
    void onEditView(int view,int position);
}
