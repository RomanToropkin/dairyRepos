package com.franq.dairy.Utility;

import android.widget.ImageView;

import com.franq.dairy.Model.local.PreferencesData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

public class ImagesHepler {

    public static void setImages(String stringURI, ImageView target, boolean resize) {
        RequestCreator creator = null;
        if ( stringURI.contains( PreferencesData.baseURL ) ) {
            creator = Picasso.get( ).load( stringURI );
        } else {
            creator = Picasso.get( )
                    .load( new File( stringURI ) );
        }
        if ( resize ) {
            creator.resize( 300, 300 )
                    .centerCrop( );
        }
        creator.into( target );
    }
}
