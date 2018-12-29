package com.franq.dairy.View.Dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.franq.dairy.R;
import com.franq.dairy.Utility.ImagesHepler;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageDialog extends DialogFragment {

    private String uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.dialog_image, container, false );
        ImageView imageView = view.findViewById( R.id.ivDialog );
        ImagesHepler.setImages( uri, imageView, false );
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) { this.uri = uri; }
}