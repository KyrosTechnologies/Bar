package com.kyros.technologies.bar.otto;

import com.squareup.otto.Bus;

/**
 * Created by Rohin on 27-04-2017.
 */

public class OttoBus {
    private static Bus sBus;
    public static Bus getBus() {
        if (sBus == null)
            sBus = new Bus();
        return sBus;
    }

}
