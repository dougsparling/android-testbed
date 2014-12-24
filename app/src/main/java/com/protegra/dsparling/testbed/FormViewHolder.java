package com.protegra.dsparling.testbed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

public final class FormViewHolder extends RecyclerView.ViewHolder {
    private final EditText number;

    public FormViewHolder(View itemView) {
        super(itemView);
        number = (EditText) itemView.findViewById(0); // TODO
    }
}
